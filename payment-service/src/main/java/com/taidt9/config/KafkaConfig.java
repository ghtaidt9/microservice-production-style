package com.taidt9.config;

import com.taidt9.PaymentResultEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

@Configuration
public class KafkaConfig {
    @Bean
    public KafkaTemplate<String, PaymentResultEvent> kafkaTemplate(
            ProducerFactory<String, PaymentResultEvent> pf) {
        return new KafkaTemplate<>(pf);
    }
}
