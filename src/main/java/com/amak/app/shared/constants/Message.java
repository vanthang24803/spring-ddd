package com.amak.app.shared.constants;

public enum Message {

    REGISTER_SUCCESSFULLY("Register successfully!");

    private final String message;

    Message(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

}
