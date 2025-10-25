package com.gym.GoldenGym.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminInventoryAdjustDto extends InventoryAdjustDto {
    private Long storeId;
}
