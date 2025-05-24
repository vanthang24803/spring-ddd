package com.amak.app.application.services.auth;

import com.amak.app.application.dto.RegisterRequest;
import com.amak.app.domain.enums.RoleName;
import com.amak.app.domain.models.Role;
import com.amak.app.domain.models.User;
import com.amak.app.domain.models.UserRole;
import com.amak.app.infrastructure.jpa.RoleRepository;
import com.amak.app.infrastructure.jpa.UserRepository;
import com.amak.app.infrastructure.jpa.UserRoleRepository;
import com.amak.app.shared.common.NormalResponse;
import com.amak.app.shared.constants.Error;
import com.amak.app.shared.constants.Message;
import com.amak.app.shared.exceptions.CustomException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@Slf4j
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserRoleRepository userRoleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public NormalResponse register(RegisterRequest request) {
        userRepository.findUserByEmail(request.getEmail())
                .ifPresent(user -> {
                    throw new CustomException(Error.EMAIL_EXISTED);
                });

        userRepository.findUserByUsername(request.getUsername())
                .ifPresent(user -> {
                    throw new CustomException(Error.USERNAME_EXISTED);
                });

        String encoderPassword = passwordEncoder.encode(request.getPassword());

        Role existingUserRole = roleRepository.findRoleByName(RoleName.ROLE_USER)
                .orElseThrow(() -> new CustomException(Error.USER_ROLE_NOTFOUND));


        User newUser = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .hashPassword(encoderPassword)

                .build();

        User saveUser = userRepository.save(newUser);

        UserRole userRole = new UserRole(saveUser, existingUserRole);

        userRoleRepository.save(userRole);

        return NormalResponse.builder().message(Message.REGISTER_SUCCESSFULLY).build();
    }
}
