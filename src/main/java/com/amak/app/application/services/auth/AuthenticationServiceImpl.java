package com.amak.app.application.services.auth;

import com.amak.app.application.dto.auth.LoginRequest;
import com.amak.app.application.dto.auth.LoginToken;
import com.amak.app.application.dto.auth.RegisterRequest;
import com.amak.app.domain.enums.RoleName;
import com.amak.app.domain.enums.TokenName;
import com.amak.app.domain.models.RoleEntity;
import com.amak.app.domain.models.TokenEntity;
import com.amak.app.domain.models.UserEntity;
import com.amak.app.domain.models.UserRoleEntity;
import com.amak.app.infrastructure.jpa.RoleRepository;
import com.amak.app.infrastructure.jpa.TokenRepository;
import com.amak.app.infrastructure.jpa.UserRepository;
import com.amak.app.infrastructure.jpa.UserRoleRepository;
import com.amak.app.infrastructure.security.JwtService;
import com.amak.app.shared.common.JwtPayload;
import com.amak.app.shared.common.NormalResponse;
import com.amak.app.shared.constants.Error;
import com.amak.app.shared.constants.Message;
import com.amak.app.shared.exceptions.CustomException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final TokenRepository tokenRepository;
    private final UserRoleRepository userRoleRepository;

    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public NormalResponse register(RegisterRequest request) {
        userRepository.findUserByEmail(request.getEmail())
                .ifPresent(userEntity -> {
                    throw new CustomException(Error.EMAIL_EXISTED);
                });

        userRepository.findUserByUsername(request.getUsername())
                .ifPresent(userEntity -> {
                    throw new CustomException(Error.USERNAME_EXISTED);
                });

        String encoderPassword = passwordEncoder.encode(request.getPassword());

        RoleEntity existingUserRoleEntity = roleRepository.findRoleByName(RoleName.ROLE_USER)
                .orElseThrow(() -> new CustomException(Error.USER_ROLE_NOTFOUND));


        UserEntity newUserEntity = UserEntity.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .hashPassword(encoderPassword)

                .build();

        UserEntity saveUserEntity = userRepository.save(newUserEntity);

        UserRoleEntity userRoleEntity = new UserRoleEntity(saveUserEntity, existingUserRoleEntity);

        userRoleRepository.save(userRoleEntity);

        return NormalResponse.builder().message(Message.REGISTER_SUCCESSFULLY).build();
    }

    @Override
    public LoginToken login(LoginRequest request) {
        UserEntity existingUser = userRepository.findUserByUsername(request.getUsername()).orElseThrow(
                () -> new CustomException(Error.USERNAME_OR_PASSWORD_IS_VALID)
        );

        List<String> roles = roleRepository.findRoleForAccount(existingUser.getId());

        Optional<TokenEntity> optAccessToken = tokenRepository
                .findUserTokenByName(existingUser.getId(), TokenName.TOKEN_BEARER);
        Optional<TokenEntity> optRefreshToken = tokenRepository
                .findUserTokenByName(existingUser.getId(), TokenName.TOKEN_REFRESH);

        Instant now = Instant.now();

        boolean accessTokenValid = optAccessToken.isPresent() && optAccessToken.get().getExpiredAt() != null
                && optAccessToken.get().getExpiredAt().isAfter(now);

        boolean refreshTokenValid = optRefreshToken.isPresent() && optRefreshToken.get().getExpiredAt() != null
                && optRefreshToken.get().getExpiredAt().isAfter(now);

        if (optAccessToken.isPresent() && !accessTokenValid) {
            TokenEntity accessToken = optAccessToken.get();
            accessToken.setIsActive(false);
            tokenRepository.save(accessToken);
        }

        if (optRefreshToken.isPresent() && !refreshTokenValid) {
            TokenEntity refreshToken = optRefreshToken.get();
            refreshToken.setIsActive(false);
            tokenRepository.save(refreshToken);
        }

        if (accessTokenValid && refreshTokenValid) {
            return LoginToken.builder()
                    .accessToken(optAccessToken.get().getValue())
                    .refreshToken(optRefreshToken.get().getValue())
                    .build();
        }

        if (!accessTokenValid && refreshTokenValid) {
            JwtPayload payload = JwtPayload.builder()
                    .username(existingUser.getUsername())
                    .roles(roles)
                    .build();

            String newAccessToken = jwtService.generateAccessToken(payload);

            TokenEntity accessToken = optAccessToken.orElseGet(() -> TokenEntity.builder().userEntity(existingUser).name(TokenName.TOKEN_BEARER).build());
            accessToken.setValue(newAccessToken);
            accessToken.setExpiredAt(jwtService.SECRET_EXP);
            accessToken.setCheckSum(jwtService.generateJwtCheckSum(existingUser, TokenName.TOKEN_BEARER));
            accessToken.setIsActive(true);
            tokenRepository.save(accessToken);

            return LoginToken.builder()
                    .accessToken(newAccessToken)
                    .refreshToken(optRefreshToken.get().getValue())
                    .build();
        }

        JwtPayload payload = JwtPayload.builder()
                .username(existingUser.getUsername())
                .roles(roles)
                .build();

        LoginToken loginToken = LoginToken.builder()
                .accessToken(jwtService.generateAccessToken(payload))
                .refreshToken(jwtService.generateRefreshToken(payload))
                .build();

        TokenEntity newToken = TokenEntity.builder()
                .name(TokenName.TOKEN_BEARER)
                .value(loginToken.getAccessToken())
                .checkSum(jwtService.generateJwtCheckSum(existingUser, TokenName.TOKEN_BEARER))
                .expiredAt(jwtService.SECRET_EXP)
                .userEntity(existingUser)
                .isActive(true)
                .build();

        TokenEntity newRefreshToken = TokenEntity.builder()
                .name(TokenName.TOKEN_REFRESH)
                .value(loginToken.getRefreshToken())
                .checkSum(jwtService.generateJwtCheckSum(existingUser, TokenName.TOKEN_REFRESH))
                .expiredAt(jwtService.REFRESH_EXP)
                .userEntity(existingUser)
                .isActive(true)
                .build();

        tokenRepository.save(newToken);
        tokenRepository.save(newRefreshToken);

        return loginToken;
    }

    @Override
    public NormalResponse logout(String username) {
        UserEntity user = userRepository.findUserByUsername(username)
                .orElseThrow(() -> new CustomException(Error.TOKEN_INVALID));

        Optional<TokenEntity> optAccessToken = tokenRepository.findUserTokenByName(user.getId(), TokenName.TOKEN_BEARER);

        if (optAccessToken.isPresent() && Boolean.TRUE.equals(optAccessToken.get().getIsActive())) {
            TokenEntity accessToken = optAccessToken.get();
            accessToken.setIsActive(false);
            tokenRepository.save(accessToken);
        }

        return NormalResponse.builder().message(Message.LOGOUT_SUCCESSFULLY).build();
    }

    @Override
    @Transactional
    public LoginToken refreshToken(String refreshToken) {
        TokenEntity existingToken = tokenRepository.findTokenEntitiesByValue(refreshToken)
                .orElseThrow(() -> new CustomException(Error.TOKEN_INVALID));

        if (!Boolean.TRUE.equals(existingToken.getIsActive()) || Instant.now().isAfter(existingToken.getExpiredAt())) {
            throw new CustomException(Error.TOKEN_INVALID);
        }

        Optional<TokenEntity> existingAccessTokenOpt = tokenRepository
                .findUserTokenByName(existingToken.getUserEntity().getId(), TokenName.TOKEN_BEARER);


        if (existingAccessTokenOpt.isPresent()) {
            TokenEntity accessToken = existingAccessTokenOpt.get();

            if (accessToken.getIsActive() && accessToken.getExpiredAt().isAfter(Instant.now())) {
                return LoginToken.builder()
                        .refreshToken(existingToken.getValue())
                        .accessToken(accessToken.getValue())
                        .build();
            }

            accessToken.setIsActive(true);
            accessToken.setExpiredAt(jwtService.SECRET_EXP);

            tokenRepository.save(accessToken);

            return LoginToken.builder()
                    .refreshToken(existingToken.getValue())
                    .accessToken(accessToken.getValue())
                    .build();
        }

        List<String> roles = roleRepository.findRoleForAccount(existingToken.getUserEntity().getId());

        String newAccessTokenValue = jwtService.generateAccessToken(
                JwtPayload.builder()
                        .username(existingToken.getUserEntity().getUsername())
                        .roles(roles)
                        .build()
        );

        TokenEntity newAccessToken = TokenEntity.builder()
                .name(TokenName.TOKEN_BEARER)
                .value(newAccessTokenValue)
                .checkSum(jwtService.generateJwtCheckSum(existingToken.getUserEntity(), TokenName.TOKEN_BEARER))
                .expiredAt(jwtService.SECRET_EXP)
                .userEntity(existingToken.getUserEntity())
                .build();

        tokenRepository.save(newAccessToken);

        return LoginToken.builder()
                .refreshToken(existingToken.getValue())
                .accessToken(newAccessToken.getValue())
                .build();
    }



}
