package com.taidt9.analyticsservice.repository;
import com.taidt9.analyticsservice.entity.PaymentAnalytics;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentAnalyticsRepository extends JpaRepository<PaymentAnalytics, Long> {
}