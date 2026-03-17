package com.taidt9.inventoryservice.config;

import io.micrometer.core.instrument.MeterRegistry;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class MetricsConfig {

    private final MeterRegistry meterRegistry;

    @PostConstruct
    public void init() {
        meterRegistry.config().commonTags("system", "ecommerce",
                "environment", "local");
    }

}
