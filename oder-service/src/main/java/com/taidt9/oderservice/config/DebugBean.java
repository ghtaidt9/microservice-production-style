package com.taidt9.oderservice.config;

import io.micrometer.core.instrument.MeterRegistry;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DebugBean {

    private final MeterRegistry registry;

    @PostConstruct
    public void debug() {
        registry.getMeters().forEach(m ->
                System.out.println("METER: " + m.getId().getName())
        );
    }
}
