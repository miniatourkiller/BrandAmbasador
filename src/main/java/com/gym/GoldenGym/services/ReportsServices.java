package com.gym.GoldenGym.services;

import com.gym.GoldenGym.dtos.ResponseDto;

public interface ReportsServices {
    public ResponseDto getSalesReport(); // return sales for all stores at once

    public ResponseDto getStoreSalesReport(); // for staff mostly

    public ResponseDto getItemVariantsInventory(Long ItemId); // returns from all the stores
}
