package com.gym.GoldenGym.dtos;

import java.util.Set;

import com.gym.GoldenGym.entities.FileEntity;
import com.gym.GoldenGym.entities.ItemVariant;

import lombok.Data;

@Data
public class ItemVariantDto {
    private String metricMeasure;
    private double variantPrice;
    private double offerPrice;
    private String offerStartDateTime;//format yyyy-MM-dd HH:mm:ss
    private String offerEndDateTime;//format yyyy-MM-dd HH:mm:ss

    public ItemVariant toItemVariant(Set<FileEntity> images) {
        ItemVariant itemVariant = new ItemVariant();
        itemVariant.setMetricMeasure(metricMeasure);
        itemVariant.setVariantPrice(variantPrice);
        itemVariant.setOfferPrice(offerPrice);
        itemVariant.setImages(images);
        itemVariant.setOfferStartDateTime(offerStartDateTime);
        itemVariant.setOfferEndDateTime(offerEndDateTime);
        return itemVariant;
    }
}
