package com.taidt9.orderservice.service;

import com.taidt9.OrderEvent;
import com.taidt9.PaymentRequestEvent;
import com.taidt9.orderservice.repositories.OrderRepository;
import com.taidt9.orderservice.entity.Order;
import com.taidt9.orderservice.metrics.OrderMetrics;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository repo;
    private final KafkaTemplate<String, OrderEvent> kafkaTemplate;
    private final OrderMetrics orderMetrics;
    private final PaymentPublisher paymentPublisher;

    public Order create(Order order) {

        order.setStatus("CREATED");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated()) {
            order.setUserId(auth.getName());
        } else {
            order.setUserId("anonymous");
        }

        Order saved = repo.save(order);

        paymentPublisher.sendPaymentRequest(
                new PaymentRequestEvent(String.valueOf(saved.getId()), saved.getUserId(), BigDecimal.valueOf(saved.getQuantity()), "USD")
        );

        kafkaTemplate.send("order-topic",
                new OrderEvent(saved.getProductId(), saved.getQuantity()));
        orderMetrics.incrementOrders();

        return saved;
    }
}