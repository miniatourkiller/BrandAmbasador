package com.gym.GoldenGym.dtos.reqdtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VariantRequestDto extends RequestDto {
    private String name;
    private Long categoryId;
    private Integer minPrice;
    private Integer maxPrice;
}
