package com.taidt9.inventoryservice.listener;

import com.taidt9.OrderEvent;
import com.taidt9.inventoryservice.metrics.InventoryMetrics;
import com.taidt9.inventoryservice.service.InventoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class InventoryListener {

    private final InventoryService inventoryService;
    private final InventoryMetrics eInventoryMetrics;

    @KafkaListener(
            topics = "order-topic",
            groupId = "inventory-group",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void consume(OrderEvent event) {
        log.info("receive message");

        inventoryService.deductStock(
                event.getProductId(),
                event.getQuantity()
        );
        eInventoryMetrics.increment();
    }
}