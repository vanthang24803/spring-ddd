package com.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {"com.api", "com.app", "com.domain", "com.infrastructure"})
@EntityScan(basePackages = {
        "com.domain.*"
})
@EnableJpaRepositories(basePackages = {
        "com.domain.*"
})
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}