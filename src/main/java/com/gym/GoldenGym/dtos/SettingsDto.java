package com.gym.GoldenGym.dtos;

import lombok.Data;

@Data
public class SettingsDto {
    private boolean checkClientDistance = false;
    private double clientDistanceMax = 0.0;
}
