package com.taidt9.inventoryservice.listener;

import com.taidt9.OrderEvent;
import com.taidt9.inventoryservice.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InventoryListener {

    private final InventoryService inventoryService;

    @KafkaListener(
            topics = "order-topic",
            groupId = "inventory-group",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void consume(OrderEvent event) {

        inventoryService.deductStock(
                event.getProductId(),
                event.getQuantity()
        );
    }
}