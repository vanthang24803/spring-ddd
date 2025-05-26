package com.amak.app.shared.constants;

import lombok.Getter;

@Getter
public enum Message {

    REGISTER_SUCCESSFULLY("Register successfully!"),
    LOGOUT_SUCCESSFULLY("Logout successfully!")
    ;

    private final String message;

    Message(String message) {
        this.message = message;
    }

}
