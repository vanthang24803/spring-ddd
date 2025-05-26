package com.amak.app.api.controller;

import com.amak.app.api.annotation.profile.Profile;
import com.amak.app.api.annotation.version.VersionController;
import com.amak.app.application.dto.auth.*;
import com.amak.app.application.services.auth.AuthenticationService;
import com.amak.app.shared.common.NormalResponse;
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
    public ResponseEntity<NormalResponse> register(@Valid @RequestBody RegisterRequest request) {
        var result = authenticationService.register(request);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginToken> login(@Valid @RequestBody LoginRequest request) {
        var result = authenticationService.login(request);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/refreshToken")
    public ResponseEntity<?> refreshToken(@Valid @RequestBody RefreshTokenRequest request) {
        var result = authenticationService.refreshToken(request.getToken());
        return ResponseEntity.ok(result);
    }

    @PostMapping("/logout")
    public ResponseEntity<NormalResponse> logout(@Profile ProfileResponse currentUser) {
        var result = authenticationService.logout(currentUser.getUsername());
        return ResponseEntity.ok(result);
    }
}
