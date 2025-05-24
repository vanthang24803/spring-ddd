package com.amak.app.api.controller;

import com.amak.app.api.annotation.version.VersionController;
import com.amak.app.application.dto.RegisterRequest;
import com.amak.app.application.services.auth.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@VersionController(module = "auth")
@RequiredArgsConstructor
@Slf4j
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest request) {
        var result = authenticationService.register(request);
        return ResponseEntity.ok(result);
    }

}
