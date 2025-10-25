package com.gym.GoldenGym.services;

import com.gym.GoldenGym.dtos.Coordinates;
import com.gym.GoldenGym.dtos.ResponseDto;

public interface LocationDistanceServices {
    public ResponseDto getPreferedStoresForDelivery(Long orderId);

    public ResponseDto canMakeOrders(Coordinates coordinates);
}
