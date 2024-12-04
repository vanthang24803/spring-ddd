package com.app.services.authentication;

import com.domain.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthenticationService implements IAuthenticationService {

    private final UserRepository userRepository;

    @Override
    public Map<String, String> getHelloWorld() {
        return Map.of("response", "Hello World");
    }

}
