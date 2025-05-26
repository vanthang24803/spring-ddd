package com.amak.app.application.services.auth;

import com.amak.app.application.dto.auth.LoginRequest;
import com.amak.app.application.dto.auth.LoginToken;
import com.amak.app.application.dto.auth.RegisterRequest;
import com.amak.app.shared.common.NormalResponse;

public interface AuthenticationService {
    NormalResponse register(RegisterRequest request);
    LoginToken login(LoginRequest request);
    NormalResponse logout(String username);
    LoginToken refreshToken(String refreshToken);
}
