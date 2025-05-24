package com.amak.app.application.services.auth;

import com.amak.app.application.dto.RegisterRequest;
import com.amak.app.shared.common.NormalResponse;

public interface AuthenticationService {
    NormalResponse register(RegisterRequest request);
}
