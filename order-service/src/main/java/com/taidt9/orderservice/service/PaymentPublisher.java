package com.taidt9.orderservice.service;

import com.taidt9.PaymentRequestEvent;
import io.awspring.cloud.sqs.operations.SqsTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentPublisher {

    private final SqsTemplate sqsTemplate;

    @Value("${spring.cloud.aws.queue.paymentRequests}")
    private String queueEndpoint;

    public void sendPaymentRequest(PaymentRequestEvent event) {
        sqsTemplate.send(queueEndpoint, event);
    }
}
