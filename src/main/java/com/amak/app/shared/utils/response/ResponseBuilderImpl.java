package com.amak.app.shared.utils.response;


import com.amak.app.shared.common.Metadata;
import com.amak.app.shared.common.Response;
import com.amak.app.shared.constants.Header;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.stereotype.Component;

@Component
public class ResponseBuilderImpl implements ResponseBuilder {

    private final ObjectFactory<HttpServletRequest> requestFactory;

    public ResponseBuilderImpl(ObjectFactory<HttpServletRequest> requestFactory) {
        this.requestFactory = requestFactory;
    }

    private Metadata getMetadata() {
        HttpServletRequest request = requestFactory.getObject();
        return new Metadata(
                request.getRequestURI(),
                request.getMethod(),
                request.getHeader(Header.USER_AGENT.getValue()),
                (String) request.getAttribute(Header.REQUEST_ID.getValue())
        );
    }

    @Override
    public Response buildSuccess(Object result) {
        return new Response(result, getMetadata());
    }

    @Override
    public Response buildError(int code, String error) {
        return new Response(code, error, getMetadata());
    }
}
