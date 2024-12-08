package com.infrastructure.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NormalResponse<T> {
    private int httpStatus;

    private String message;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private T result;

    @Builder.Default
    private LocalDateTime timestamp = LocalDateTime.now();
}

