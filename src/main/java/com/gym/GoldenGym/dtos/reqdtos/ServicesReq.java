package com.gym.GoldenGym.dtos.reqdtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ServicesReq extends RequestDto {
    private String orderNumber;
    private String clientEmail;
    private String serviceName;
    private String storeName;
    private Boolean paid;
    private Boolean completed;
}
