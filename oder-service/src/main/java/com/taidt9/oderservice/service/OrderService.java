package com.taidt9.oderservice.service;

import com.taidt9.OrderEvent;
import com.taidt9.oderservice.OrderRepository;
import com.taidt9.oderservice.entity.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository repo;
    private final KafkaTemplate<String, OrderEvent> kafkaTemplate;

    public Order create(Order order) {

        order.setStatus("CREATED");
        Order saved = repo.save(order);

        kafkaTemplate.send("order-topic",
                new OrderEvent(saved.getProductId(), saved.getQuantity()));

        return saved;
    }
}