package com.taidt9.oderservice.metrics;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Component;

@Component
public class OrderMetrics {
    private final Counter orderCreated;

    public OrderMetrics(MeterRegistry meterRegistry) {
        this.orderCreated = Counter
                .builder("orders_total")
                .description("Total orders created")
                .tag("service", "order-service")
                .tag("region", "vn")
                .register(meterRegistry);
    }

    public void incrementOrders() {
        System.out.println(this.orderCreated.count());
        this.orderCreated.increment();
        System.out.println(this.orderCreated.count());
    }
}
