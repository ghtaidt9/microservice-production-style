package com.taidt9.orderservice.service;

import com.taidt9.OrderEvent;
import com.taidt9.orderservice.OrderRepository;
import com.taidt9.orderservice.entity.Order;
import com.taidt9.orderservice.metrics.OrderMetrics;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository repo;
    private final KafkaTemplate<String, OrderEvent> kafkaTemplate;
    private final OrderMetrics orderMetrics;

    public Order create(Order order) {

        Order saved = repo.save(order);

        // Kafka publish
        kafkaTemplate.send("order-topic",
                new OrderEvent(order.getProductId(), order.getQuantity()));
        orderMetrics.incrementOrders();

        return saved;
    }
}