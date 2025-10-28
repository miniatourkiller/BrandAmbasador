package com.gym.GoldenGym.dtos;

import java.util.List;

import lombok.Data;

@Data
public class MultiCategoryDto {
    private List<CategoryDto> categories;
}
