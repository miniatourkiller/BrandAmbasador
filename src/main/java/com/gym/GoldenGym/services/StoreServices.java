package com.gym.GoldenGym.services;

import com.gym.GoldenGym.dtos.Coordinates;
import com.gym.GoldenGym.dtos.ResponseDto;
import com.gym.GoldenGym.dtos.StoreDto;

public interface StoreServices {

    public ResponseDto getAllStores();

    public ResponseDto createStore(StoreDto storeDto);

    public ResponseDto updateStore(Long storeId, StoreDto storeDto);

    public ResponseDto getPreferedStoresForDelivery(Long orderId);

    public ResponseDto canMakeOrders(Coordinates coordinates);
}
