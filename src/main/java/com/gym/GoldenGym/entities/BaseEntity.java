package com.gym.GoldenGym.entities;

import com.gym.GoldenGym.utils.DateUtils;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;

@Data
@MappedSuperclass
public abstract class BaseEntity {
    @Column(nullable = false, updatable = false)
    private String createdAt = DateUtils.currentDate();
    @Column(insertable = false)
    private String updatedAt = DateUtils.currentDate();
    private boolean active = true;
    private boolean deleted = false;
}
