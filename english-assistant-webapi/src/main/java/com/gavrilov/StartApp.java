package com.gavrilov;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = "org.gavrilov.domain")
@EnableJpaRepositories(basePackages = "org.gavrilov.repository")
@ComponentScan({"org.gavrilov", "com.gavrilov"})
public class StartApp extends SpringBootServletInitializer {
    public static void main(String[] args) {
        SpringApplication.run(StartApp.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(StartApp.class);
    }
}
