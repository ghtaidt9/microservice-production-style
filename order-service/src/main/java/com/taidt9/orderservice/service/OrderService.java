package com.taidt9.orderservice.service;

import com.taidt9.PaymentRequestEvent;
import com.taidt9.orderservice.repositories.OrderRepository;
import com.taidt9.orderservice.entity.Order;
import com.taidt9.orderservice.metrics.OrderMetrics;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository repo;
    private final OrderMetrics orderMetrics;
    private final OrderPublisher paymentPublisher;

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

        paymentPublisher.sendInventoryRequest(saved);

        orderMetrics.incrementOrders();

        return saved;
    }
}