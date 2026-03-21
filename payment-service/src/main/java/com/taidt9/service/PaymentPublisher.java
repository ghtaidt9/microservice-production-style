package com.taidt9.service;

import com.taidt9.PaymentResultEvent;
import io.awspring.cloud.sqs.operations.SqsTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentPublisher {

    private final SqsTemplate sqsTemplate;

    public void sendPaymentResult(PaymentResultEvent event) {
        sqsTemplate.send("payment-results", event);
    }
}
