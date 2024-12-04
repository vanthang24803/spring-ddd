package com.app.services.authentication;

import com.app.common.NormalResponse;
import com.domain.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;

@Service
@RequiredArgsConstructor
public class AuthenticationService implements IAuthenticationService {

    private final UserRepository userRepository;

    private final Logger logger = LoggerFactory.getLogger(AuthenticationService.class);

    @Override
    public NormalResponse<String> getHelloWorld() {
        logger.info("Running at {}", "Hello World");
        return NormalResponse.<String>builder()
                .httpStatus(200)
                .message("Oke")
                .build();
    }

}
