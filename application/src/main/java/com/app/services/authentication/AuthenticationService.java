package com.app.services.authentication;

import com.app.common.NormalResponse;
import com.app.exceptions.BadRequestException;
import com.app.exceptions.NotFoundException;
import com.app.exceptions.UnauthorizedException;
import com.app.services.authentication.dto.RegisterRequest;
import com.domain.enums.ERole;
import com.domain.models.RoleEntity;
import com.domain.models.UserEntity;
import com.domain.repositories.RoleRepository;
import com.domain.repositories.TokenRepository;
import com.domain.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j

public class AuthenticationService implements IAuthenticationService, UserDetailsService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public NormalResponse<String> register(RegisterRequest request) {
        Optional<UserEntity> isExistEmail = userRepository.findByEmail(request.getEmail());

        if (isExistEmail.isPresent()) {
            throw new BadRequestException("Email already exists!");
        }

        Optional<RoleEntity> customerRole = roleRepository.findByRole(ERole.ROLE_CUSTOMER);

        if (customerRole.isEmpty()) {
            throw new NotFoundException("Customer role not found!");
        }

        String hashedPassword = passwordEncoder.encode(request.getPassword());

        UserEntity newUser = UserEntity.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .password(hashedPassword)
                .email(request.getEmail())
                .verify(false)
                .roles(Set.of(customerRole.get()))
                .build();

        userRepository.save(newUser);

        return NormalResponse.<String>builder()
                .httpStatus(200)
                .message("Created account successful!")
                .build();
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByEmail(email).orElseThrow(UnauthorizedException::new);

        Collection<GrantedAuthority> roles =
                user.getRoles()
                        .stream().map(role ->
                                new SimpleGrantedAuthority(role.getRole().toString()))
                        .collect(Collectors.toList());

        return new User(user.getEmail(), user.getPassword(), roles);
    }
}

