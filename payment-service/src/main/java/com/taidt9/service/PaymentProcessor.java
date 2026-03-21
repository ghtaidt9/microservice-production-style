package com.taidt9.service;

import com.taidt9.PaymentRequestEvent;
import com.taidt9.PaymentResultEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class PaymentProcessor {

    private final StringRedisTemplate redisTemplate;

    public PaymentResultEvent process(PaymentRequestEvent event) {
        String key = "payment:" + event.orderId();

        if (redisTemplate.hasKey(key)) {
            return new PaymentResultEvent(event.orderId(), "DUPLICATE", "Already processed");
        }

        try {
            boolean success = Math.random() > 0.2; // 80% success
            redisTemplate.opsForValue().set(key, "done", 1, TimeUnit.DAYS);

            if (success) {
                return new PaymentResultEvent(event.orderId(), "SUCCESS", "Payment processed");
            } else {
                throw new RuntimeException("Payment failed");
            }
        } catch (Exception ex) {
            return new PaymentResultEvent(event.orderId(), "FAILED", ex.getMessage());
        }
    }
}
