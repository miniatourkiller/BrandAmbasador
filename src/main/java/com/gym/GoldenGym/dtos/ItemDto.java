package com.gym.GoldenGym.dtos;

import lombok.Data;

@Data
public class ItemDto {
    private Long categoryId;
    private String itemName;
    private String itemDescription;
    private String manufacturer;
}
