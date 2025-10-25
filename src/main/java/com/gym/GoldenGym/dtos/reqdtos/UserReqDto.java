package com.gym.GoldenGym.dtos.reqdtos;

import com.gym.GoldenGym.entities.enums.Roles;

import lombok.Data;

@Data
public class UserReqDto {
    private String email;
    private String idNumber;
    private Roles role;
    private String storeName;
    private Long storeId;
}
