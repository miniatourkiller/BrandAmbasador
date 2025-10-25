package com.gym.GoldenGym.dtos;

import java.util.List;

import lombok.Data;

@Data
public class MultiVariantsEntry {
    private Long itemId;
    private List<ItemVariantDto> itemVariants;
}
