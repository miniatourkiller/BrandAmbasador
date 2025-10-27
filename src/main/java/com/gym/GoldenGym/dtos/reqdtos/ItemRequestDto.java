package com.gym.GoldenGym.dtos.reqdtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemRequestDto extends RequestDto {
    private String name;
    private Long categoryId;
    private int minPrice;
    private int maxPrice;
}
