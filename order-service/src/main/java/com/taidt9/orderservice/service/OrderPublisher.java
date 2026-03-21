package com.taidt9.orderservice.service;

import com.taidt9.OrderEvent;
import com.taidt9.PaymentRequestEvent;
import com.taidt9.orderservice.entity.Order;
import io.awspring.cloud.sqs.operations.SqsTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderPublisher {

    private final SqsTemplate sqsTemplate;
    private final KafkaTemplate<String, OrderEvent> kafkaTemplate;

    @Value("${spring.cloud.aws.queue.paymentRequests}")
    private String queueEndpoint;

    public void sendPaymentRequest(PaymentRequestEvent event) {
        sqsTemplate.send(queueEndpoint, event);
    }

    public void sendInventoryRequest(Order order) {
        kafkaTemplate.send("order-topic",
                new OrderEvent(order.getProductId(), order.getQuantity()));
    }


}
