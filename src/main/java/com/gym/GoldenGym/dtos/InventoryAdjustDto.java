package com.gym.GoldenGym.dtos;

import lombok.Data;

@Data
public class InventoryAdjustDto {
    private Long variantId;
    private int quantity;
    private Action action;

    public static enum Action {
        INCREASE, DECREASE
    }
}
