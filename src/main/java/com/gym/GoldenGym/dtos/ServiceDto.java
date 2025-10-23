package com.gym.GoldenGym.dtos;

import lombok.Data;

@Data
public class ServiceDto {
    private String serviceName;
    private String serviceDescription;
    private double servicePrice;
    private double offerPrice;
    private String offerStartDateTime;
    private String offerEndDateTime;
}
