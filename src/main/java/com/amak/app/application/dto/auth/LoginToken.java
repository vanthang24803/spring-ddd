package com.amak.app.application.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginToken {
    private String accessToken;
    private String refreshToken;
    @Builder.Default
    private String expiredAt = Instant.now().plus(7, ChronoUnit.DAYS).toString();
}
