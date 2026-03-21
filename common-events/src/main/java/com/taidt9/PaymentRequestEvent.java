package com.taidt9;

import java.math.BigDecimal;

public record PaymentRequestEvent(
        String orderId,
        String userId,
        BigDecimal amount,
        String currency
) {}

