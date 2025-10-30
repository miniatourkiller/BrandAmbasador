package com.gym.GoldenGym.dtos;

import lombok.Data;

@Data
public class RemovePaymentDto {
    private Long orderId;
    private String reason;
}
