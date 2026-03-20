package com.taidt9.productservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Value("${monitoring.prometheus.api-key}")
    private String prometheusApiKey;


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.csrf(CsrfConfigurer::disable).authorizeHttpRequests(auth -> auth

                // Prometheus endpoint
                .requestMatchers("/actuator/prometheus").access((authen, context) -> {
                    String authHeader = context.getRequest().getHeader("Authorization");
                    return new AuthorizationDecision(authHeader != null && authHeader.equals(String.format("Bearer %s", prometheusApiKey)));
                })
                // Health & info (optional: allow internal)
                .requestMatchers("/actuator/health", "/actuator/info").permitAll()
                // Block other actuator endpoints
                .requestMatchers("/actuator/**").denyAll()
                // Business APIs vẫn dùng JWT (nếu có)
                .anyRequest().authenticated());

        return http.build();
    }
}