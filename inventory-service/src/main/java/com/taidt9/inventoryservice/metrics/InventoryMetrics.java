package com.taidt9.inventoryservice.metrics;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Component;

@Component
public class InventoryMetrics {
    private final Counter inventoryDeducted;

    public InventoryMetrics(MeterRegistry meterRegistry) {
        this.inventoryDeducted = Counter.builder("inventory_deducted_total")
                .description("Total inventory deductions")
                .tag("service", "order-service")
                .tag("region", "vn")
                .register(meterRegistry);
    }

    public void increment() {
        System.out.println(inventoryDeducted.count());
        this.inventoryDeducted.increment();
        System.out.println(inventoryDeducted.count());

    }
}
