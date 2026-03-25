package com.taidt9.analyticsservice.consumer;

import com.taidt9.analyticsservice.dto.PaymentEvent;
import com.taidt9.analyticsservice.entity.PaymentAnalytics;
import com.taidt9.analyticsservice.repository.PaymentAnalyticsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;

import java.time.LocalDateTime;

@Configuration
@RequiredArgsConstructor
public class PaymentConsumer {
    private final PaymentAnalyticsRepository analyticsRepository;

    @KafkaListener(topics = "payment-events", containerFactory = "paymentKafkaListenerFactory")
    public void consume(PaymentEvent paymentEvent) {
        PaymentAnalytics analytics = PaymentAnalytics.builder().orderId(paymentEvent.getOrderId()).amount(paymentEvent.getAmount()).paymentStatus(paymentEvent.getStatus()).createdAt(LocalDateTime.now()).build();
        analyticsRepository.save(analytics);
    }
}
