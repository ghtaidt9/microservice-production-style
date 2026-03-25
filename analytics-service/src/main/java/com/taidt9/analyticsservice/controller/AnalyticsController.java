package com.taidt9.analyticsservice.controller;

import com.taidt9.analyticsservice.service.AnalyticsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/analytics")
@RequiredArgsConstructor
public class AnalyticsController {
    private final AnalyticsService service;

    @GetMapping("/summary")
    public Map<String, Object> summary() {

        return Map.of(
                "totalOrders", service.totalOrders(),
                "totalPayments", service.totalPayments()
        );
    }
}
