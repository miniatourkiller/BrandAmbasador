package com.gym.GoldenGym.dtos;

import lombok.Data;

@Data
public class OrderDto {
    private Long variantId;
    private int units;
    private Coordinates coordinates;
}