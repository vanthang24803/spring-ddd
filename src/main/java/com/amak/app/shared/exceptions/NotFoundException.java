package com.amak.app.shared.exceptions;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String messgae) {
        super(messgae);
    }
}
