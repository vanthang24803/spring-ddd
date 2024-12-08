package com.infrastructure.exceptions;


import com.infrastructure.common.ApiError;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class BadRequestException extends RuntimeException {
    private final ApiError apiError;

    public BadRequestException(String message) {
        super(message);
        this.apiError = new ApiError(400, message, LocalDateTime.now());
    }
}
