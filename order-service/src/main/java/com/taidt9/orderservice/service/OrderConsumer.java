package com.taidt9.orderservice.service;

import com.taidt9.PaymentResultEvent;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class OrderConsumer {

    private final OrderService orderService;

    @KafkaListener(topics = "payment-results", containerFactory = "paymentResultKafkaListenerContainerFactory")
    public void consume(PaymentResultEvent event) {
        log.info("Received PaymentResultEvent: {}", "PaymentResultEvent");
    }
}