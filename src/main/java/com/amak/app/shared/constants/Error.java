package com.amak.app.shared.constants;

import lombok.Getter;

import java.util.Map;

@Getter
public enum Error {
    WRONG_PIN(1000, "Wrong PIN!"),
    ACCOUNT_LOCKED(1001, "Account locked!"),
    INVALID_TOKEN(1002, "Invalid authentication token!"),
    EMAIL_EXISTED(1003, "Email existed!"),
    USERNAME_EXISTED(1004, "Username existed!"),
    USER_ROLE_NOTFOUND(1005, "User role not found!"),
    USERNAME_OR_PASSWORD_IS_VALID(1006, "Username or password invalid!"),
    TOKEN_INVALID(1007, "Token invalid!"),
    TOKEN_IN_ACTIVE(1008, "Token disable!")
    ;

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

}
