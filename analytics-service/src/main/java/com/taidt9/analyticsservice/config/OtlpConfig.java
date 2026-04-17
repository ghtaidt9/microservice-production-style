package com.taidt9.analyticsservice.config;

import io.opentelemetry.exporter.otlp.http.trace.OtlpHttpSpanExporter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OtlpConfig {
    @Value("${OTEL_EXPORTER_OTLP_ENDPOINT:http://jaeger:4318/v1/traces}")
    private String otlpEndpoint;

    @Bean
    OtlpHttpSpanExporter otlpHttpSpanExporter() {
        return OtlpHttpSpanExporter.builder().setEndpoint(otlpEndpoint).build();
    }
}
