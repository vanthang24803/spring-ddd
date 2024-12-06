package com.api.interceptors;

import jakarta.annotation.Nullable;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class HttpResponseInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request,
                             @Nullable() HttpServletResponse response, @Nullable() Object handler) {
        if (HttpMethod.POST.matches(request.getMethod())) {
            assert response != null;
            response.setStatus(HttpServletResponse.SC_CREATED);
        } else {
            assert response != null;
            response.setStatus(HttpServletResponse.SC_OK);
        }
        return true;
    }
}
