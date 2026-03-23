package com.taidt9.notificationservice.service;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.PublishRequest;

@Service
public class SnsPublisherService {

    private final SnsClient snsClient;
    private final Counter notificationsSentCounter;
    @Value("${spring.cloud.aws.sns.topic-arn}")
    private String topicArn;

    public SnsPublisherService(MeterRegistry meterRegistry, SnsClient snsClient) {
        this.snsClient = snsClient;
        this.notificationsSentCounter = Counter.builder("notifications_sent_total")
                .description("Total number of notifications sent")
                .tags("service", "notification-service")
                .register(meterRegistry);
    }

    public void publishNotification(String message) {
        PublishRequest request = PublishRequest.builder().topicArn(topicArn).message(message).build();
        snsClient.publish(request);
        notificationsSentCounter.increment();
    }
}

