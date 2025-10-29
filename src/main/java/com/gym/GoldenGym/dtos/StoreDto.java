package com.gym.GoldenGym.dtos;

import com.gym.GoldenGym.entities.StoreLocation;

import lombok.Data;

@Data
public class StoreDto {
    private String storeName;
    private Coordinates coordinates;

    public StoreLocation toStoreLocation() {
        StoreLocation storeLocation = new StoreLocation();
        storeLocation.setStoreName(storeName);
        storeLocation.setLongitude(coordinates.getLongitude());
        storeLocation.setLatitude(coordinates.getLatitude());
        return storeLocation;
    }
}
