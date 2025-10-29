package com.gym.GoldenGym.dtos;

import com.gym.GoldenGym.utils.DateUtils;

import lombok.Data;

@Data
public class ServiceOrderScheduleDto {
    private Long orderId;
    private String scheduledDateTime;

    public void setScheduledDateTime(String scheduledDateTime) {
        if (scheduledDateTime != null) {
            try {
                this.scheduledDateTime = DateUtils.getDate(scheduledDateTime);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
