package com.amak.app.shared.constants;

import lombok.Getter;

@Getter
public enum Swagger {
    TITLE("AMAK API Document"),
    DESC(""),
    LICENSE("Apache 2.0");

    private final String value;

    Swagger(String value) {
        this.value = value;
    }

}
