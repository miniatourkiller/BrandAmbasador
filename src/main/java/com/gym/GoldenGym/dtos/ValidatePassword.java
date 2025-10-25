package com.gym.GoldenGym.dtos;

import lombok.Data;

@Data
public class ValidatePassword {
    private String email;
    private String otp;
    private String newPassword;
}
