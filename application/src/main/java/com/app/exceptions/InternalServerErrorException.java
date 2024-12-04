package com.app.exceptions;


import com.app.common.ApiError;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class InternalServerErrorException extends RuntimeException {
    private final ApiError apiError;

    public InternalServerErrorException() {
        super();
        this.apiError = new ApiError(500, "Internal Server Error!"
                , LocalDateTime.now());
    }
}
