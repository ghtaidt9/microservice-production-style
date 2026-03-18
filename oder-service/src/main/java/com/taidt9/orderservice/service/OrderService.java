package com.taidt9.orderservice.service;

import com.taidt9.OrderEvent;
import com.taidt9.orderservice.OrderRepository;
import com.taidt9.orderservice.entity.Order;
import com.taidt9.orderservice.metrics.OrderMetrics;
import io.micrometer.tracing.Tracer;
import lombok.RequiredArgsConstructor;
import io.micrometer.tracing.Span;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository repo;
    private final KafkaTemplate<String, OrderEvent> kafkaTemplate;
    private final OrderMetrics orderMetrics;
    private final Tracer tracer;

    public Order create(Order order) {

        Span span = tracer.nextSpan().name("create-order");
        Order saved = repo.save(order);

        try (Tracer.SpanInScope ws = tracer.withSpan(span.start())) {
            processPayment(saved);
            sendKafkaEvent(saved);
        } finally {
            span.end();
        }



        return saved;
    }
    private void processPayment(Order order) {

        Span span = tracer.nextSpan().name("process-payment");

        try (Tracer.SpanInScope ws = tracer.withSpan(span.start())) {
            // giả lập payment
        } finally {
            span.end();
        }
    }

    private void sendKafkaEvent(Order order) {

        Span span = tracer.nextSpan().name("send-kafka-event");

        try (Tracer.SpanInScope ws = tracer.withSpan(span.start())) {
            // Kafka publish
            kafkaTemplate.send("order-topic",
                    new OrderEvent(order.getProductId(), order.getQuantity()));
            orderMetrics.incrementOrders();
        } finally {
            span.end();
        }
    }
}