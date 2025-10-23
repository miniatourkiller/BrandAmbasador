package com.gym.GoldenGym.dtos;

import lombok.Data;

@Data
public class ItemVariantDto {
    private String variantQuantity;
    private int units;
    private double variantPrice;
    private double offerPrice;
    private String offerStartDateTime;//format yyyy-MM-dd HH:mm:ss
    private String offerEndDateTime;//format yyyy-MM-dd HH:mm:ss
}
