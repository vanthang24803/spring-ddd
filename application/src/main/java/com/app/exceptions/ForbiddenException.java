package com.app.exceptions;


import com.app.common.ApiError;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ForbiddenException extends RuntimeException {
    private final ApiError apiError;

    public ForbiddenException() {
        super();
        this.apiError = new ApiError(403, "Forbidden!"
                , LocalDateTime.now());
    }
}
