package com.taidt9.analyticsservice.repository;

import com.taidt9.analyticsservice.entity.OrderAnalytics;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderAnalyticsRepository extends JpaRepository<OrderAnalytics, Long> {
}
