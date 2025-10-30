package com.gym.GoldenGym.dtos;

import java.util.List;

import lombok.Data;

@Data
public class OrderCart {
    private List<Long> cartItemIds;
    private Coordinates coordinates;
}
