package com.gym.GoldenGym.dtos.reqdtos;

import com.gym.GoldenGym.entities.enums.Roles;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserReqDto extends RequestDto{
    private String email;
    private Roles role;
    private String storeName;
    private Long storeId;
}
