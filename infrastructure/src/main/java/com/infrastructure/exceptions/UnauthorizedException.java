package com.infrastructure.exceptions;


import com.infrastructure.common.ApiError;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UnauthorizedException extends RuntimeException {
    private final ApiError apiError;

    public UnauthorizedException() {
        super();
        this.apiError = new ApiError(401, "Unauthorized!"
                , LocalDateTime.now());
    }
}
