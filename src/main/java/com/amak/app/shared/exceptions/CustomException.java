package com.amak.app.shared.exceptions;

import com.amak.app.shared.constants.Error;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomException extends RuntimeException {

    private int code;
    public CustomException(int code, String message) {
        super(message);
        this.code = code;
    }

    public CustomException(Error error) {
        super(error.getMessage());
        this.code =  error.getCode();
    }
}
