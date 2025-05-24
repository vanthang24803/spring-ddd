package com.amak.app.shared.utils.response;

import com.amak.app.shared.common.Response;

public interface ResponseBuilder {
    Response buildSuccess(Object result);
    Response buildError(int code , String error);
}
