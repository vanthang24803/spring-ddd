package com.app.services.authentication;


import com.app.common.NormalResponse;
import com.app.services.authentication.dto.RegisterRequest;


public interface IAuthenticationService {
    NormalResponse<String> register(RegisterRequest request);
}
