package com.taidt9.notificationservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaNotificationConsumer {

    private final SnsPublisherService snsPublisherService;

    @KafkaListener(topics = "order-events", groupId = "notification-group")
    public void consumeOrderEvent(String message) {
        snsPublisherService.publishNotification("Order Event Received: " + message);
    }

    @KafkaListener(topics = "payment-events", groupId = "notification-group")
    public void consumePaymentEvent(String message) {
        snsPublisherService.publishNotification("Payment Event Received: " + message);
    }
}
