package com.taidt9.service;

import com.taidt9.PaymentResultEvent;
import io.awspring.cloud.sqs.operations.SqsTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentPublisher {

    private final KafkaTemplate<String, PaymentResultEvent> kafkaTemplate;

    public void sendPaymentResult(PaymentResultEvent event) {
        kafkaTemplate.send("payment-results", event);
        //sqsTemplate.send("payment-results", event);
    }
}
