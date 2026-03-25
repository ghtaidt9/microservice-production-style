package com.taidt9.analyticsservice.consumer;

import com.taidt9.analyticsservice.dto.OrderEvent;
import com.taidt9.analyticsservice.entity.OrderAnalytics;
import com.taidt9.analyticsservice.repository.OrderAnalyticsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class OrderConsumer {

    private final OrderAnalyticsRepository repository;

    @KafkaListener(topics = "order-events", containerFactory = "orderKafkaListenerFactory")
    public void consume(OrderEvent event) {

        OrderAnalytics analytics = OrderAnalytics.builder().orderId(event.getOrderId()).totalAmount(event.getAmount()).status(event.getStatus()).createdAt(LocalDateTime.now()).build();

        repository.save(analytics);
    }
}