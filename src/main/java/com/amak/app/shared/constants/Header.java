package com.amak.app.shared.constants;

public enum Header {
    REQUEST_ID("requestId"),
    USER_AGENT("User-Agent"),
    AUTHORIZATION("Authorization");

    private final String value;

    Header(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
