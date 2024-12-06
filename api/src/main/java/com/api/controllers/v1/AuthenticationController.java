package com.api.controllers.v1;

import com.app.common.NormalResponse;
import com.app.services.authentication.IAuthenticationService;
import com.app.services.authentication.dto.RegisterRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
@Slf4j
@RequiredArgsConstructor
public class AuthenticationController {
    private final IAuthenticationService authenticationService;

    @PostMapping("register")
    public NormalResponse<String> register(@Valid @RequestBody RegisterRequest request) {
       return authenticationService.register(request);
    }

}
