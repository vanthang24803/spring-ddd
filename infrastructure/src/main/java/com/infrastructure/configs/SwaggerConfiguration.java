package com.infrastructure.configs;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition
public class SwaggerConfiguration {

    @Bean
    public OpenAPI swagger() {
        Info info = new Info().version("1.0.0").title("API");
        return new OpenAPI().info(info);
    }
}
