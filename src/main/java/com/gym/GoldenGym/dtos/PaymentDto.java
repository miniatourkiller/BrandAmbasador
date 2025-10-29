package com.gym.GoldenGym.dtos;

import lombok.Data;

@Data
public class PaymentDto {
    private Long orderId;
    private String paymentMethod;
    private String paymentRef;
}
