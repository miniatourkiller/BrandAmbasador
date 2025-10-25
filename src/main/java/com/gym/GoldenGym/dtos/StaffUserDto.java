package com.gym.GoldenGym.dtos;

import lombok.Data;

@Data
public class StaffUserDto {
    private String email;
    private String fullName;
    private String contact;
    private String idNumber;
    private Long storeId;
}
