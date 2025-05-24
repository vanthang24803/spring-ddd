package com.amak.app.shared.common;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class Response {
    private int code;
    private boolean success;
    private String error;
    private Object result;
    private Metadata metadata;


    public Response(int code, String error, Metadata metadata) {
        this.code = code;
        this.success = false;
        this.result = null;
        this.error = error;
        this.metadata = metadata;
    }

    public Response(Object result, Metadata metadata) {
        this.code = 200;
        this.success = true;
        this.result = result;
        this.error = null;
        this.metadata = metadata;
    }


}
