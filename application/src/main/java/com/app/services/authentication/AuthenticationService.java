package com.app.services.authentication;

import com.app.common.NormalResponse;
import com.app.exceptions.BadRequestException;
import com.app.exceptions.NotFoundException;
import com.app.services.authentication.dto.RegisterRequest;
import com.domain.enums.ERole;
import com.domain.models.RoleEntity;
import com.domain.models.UserEntity;
import com.domain.repositories.RoleRepository;
import com.domain.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j

public class AuthenticationService implements IAuthenticationService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
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

        log.debug(newUser.toString());

        userRepository.save(newUser);

        return NormalResponse.<String>builder()
                .httpStatus(200)
                .message("Created account successful!")
                .build();
    }
}

