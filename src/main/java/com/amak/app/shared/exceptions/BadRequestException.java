package com.amak.app.shared.exceptions;

public class BadRequestException extends RuntimeException {
    public BadRequestException(String messgae) {
        super(messgae);
    }
}
