package com.amak.app.api.annotation.version;


import org.springframework.core.annotation.AliasFor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@RestController
@RequestMapping
public @interface VersionController {

    @AliasFor(annotation = RequestMapping.class, attribute = "path")
    String[] path() default {};

    /**
     * Version number (e.g., 1, 2, 3)
     */
    int version() default 1;

    /**
     * Module name (e.g., "auth", "user", "product")
     */
    String module() default "";

    /**
     * API prefix (default: "/api")
     */
    String prefix() default "/api";
}
