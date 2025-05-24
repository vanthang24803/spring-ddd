package com.amak.app.shared.constants;

import java.util.Map;

public enum Error {
    WRONG_PIN(1000, "Wrong PIN!"),
    ACCOUNT_LOCKED(1001, "Account locked!"),
    INVALID_TOKEN(1002, "Invalid authentication token!"),
    EMAIL_EXISTED(1003, "Email existed!"),
    USERNAME_EXISTED(1004, "Username existed!"),
    USER_ROLE_NOTFOUND(1005, "User role not found!");

    private final int code;
    private final String message;

    Error(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public Map<String, Object> getError() {
        return Map.of(
                "code", this.getCode(),
                "message", this.getMessage()
        );
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
