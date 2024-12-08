package com.infrastructure.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NotValidException {
    private int httpStatus;
    private Map<String, String> message;
    private LocalDateTime timestamp;

    public NotValidException(int httpStatus, Map<String, String> message) {
        this.httpStatus = httpStatus;
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }
}
