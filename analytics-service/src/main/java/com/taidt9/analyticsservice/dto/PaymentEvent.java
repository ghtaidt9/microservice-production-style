package com.taidt9.analyticsservice.dto;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentEvent {
    private String orderId;
    private Double amount;
    private String status;
}