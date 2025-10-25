package com.gym.GoldenGym.dtos.reqdtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrdersReq extends RequestDto {
    private String orderNumber;
    private String clientEmail;
    private String storeName;
    private Boolean paid;
    private boolean delivered;
}
