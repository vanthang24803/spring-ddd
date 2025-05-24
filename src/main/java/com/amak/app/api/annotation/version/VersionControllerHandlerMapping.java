package com.amak.app.api.annotation.version;

import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.reflect.Method;

public class VersionControllerHandlerMapping extends RequestMappingHandlerMapping {

    @Override
    protected RequestMappingInfo getMappingForMethod(Method method, Class<?> handlerType) {
        RequestMappingInfo info = super.getMappingForMethod(method, handlerType);
        if (info == null) return null;

        VersionController versionController = AnnotatedElementUtils.findMergedAnnotation(handlerType, VersionController.class);
        if (versionController == null) return info;

        String basePath = buildPath(versionController);

        RequestMappingInfo prefixInfo = RequestMappingInfo
                .paths(basePath)
                .build();

        return prefixInfo.combine(info);
    }

    private String buildPath(VersionController versionController) {
        StringBuilder sb = new StringBuilder();
        sb.append(versionController.prefix());
        sb.append("/v").append(versionController.version());
        if (!versionController.module().isEmpty()) {
            sb.append("/").append(versionController.module());
        }
        return sb.toString();
    }

}
