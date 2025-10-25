package com.gym.GoldenGym.dtos.reqdtos;

import lombok.Data;

@Data
public abstract class RequestDto {
    private int pageSize;
    private int pageNumber;
}
