package com.amak.app.application.dto.auth;

import com.amak.app.domain.enums.AccountStatus;
import com.amak.app.domain.enums.Locale;
import com.amak.app.domain.enums.Timezone;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProfileResponse {
    private Long id;
    private String username;
    private String email;
    private String firstName;
    private String middleName;
    private String lastName;
    private String phoneNumber;
    private Instant lastLogin;
    private Timezone timezone;
    private Locale locale;
    private String avatarUrl;
    private Boolean emailVerified;
    private AccountStatus status;
    private List<String> roles;
}
