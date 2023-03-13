package com.github.maleksandrowicz93.cqrsdemo.student.rest.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
class RestApiConfig {

    @Value("${allowed-origins.front-end}")
    private String frontEnd;

    @Bean
    WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry
                        .addMapping("/student/**")
                        .allowedOrigins(frontEnd)
                        .allowedMethods(HttpMethod.GET.toString(),
                                HttpMethod.POST.toString(),
                                HttpMethod.PUT.toString(),
                                HttpMethod.DELETE.toString());
            }
        };
    }
}
