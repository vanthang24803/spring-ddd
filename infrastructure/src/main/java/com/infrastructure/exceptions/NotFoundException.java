package com.infrastructure.exceptions;


import com.infrastructure.common.ApiError;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class NotFoundException extends RuntimeException {
    private final ApiError apiError;

    public NotFoundException(String message) {
        super(message);
        this.apiError = new ApiError(404, message, LocalDateTime.now());
    }
}
