package com.taidt9.service;

import com.taidt9.PaymentRequestEvent;
import com.taidt9.PaymentResultEvent;
import io.awspring.cloud.sqs.annotation.SqsListener;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class PaymentConsumer {

    private final PaymentProcessor paymentProcessor;
    private final PaymentPublisher paymentPublisher;

    @SqsListener("${spring.cloud.aws.queue.paymentRequests}")
    public void consume(PaymentRequestEvent event) {
        log.info("Received Payment Request: {}", "PaymentRequestEvent");
        PaymentResultEvent result = paymentProcessor.process(event);
        paymentPublisher.sendPaymentResult(result);
    }
}
