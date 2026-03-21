package com.taidt9;

public record PaymentResultEvent(
        String orderId,
        String status,
        String message
) {}
