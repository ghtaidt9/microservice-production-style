package com.taidt9.analyticsservice.service;

import com.taidt9.analyticsservice.repository.OrderAnalyticsRepository;
import com.taidt9.analyticsservice.repository.PaymentAnalyticsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AnalyticsService {

    private final OrderAnalyticsRepository orderRepo;
    private final PaymentAnalyticsRepository paymentRepo;

    public long totalOrders() {
        return orderRepo.count();
    }

    public long totalPayments() {
        return paymentRepo.count();
    }
}