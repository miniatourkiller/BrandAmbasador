package com.gym.GoldenGym.entities;

import com.gym.GoldenGym.utils.DateUtils;
import com.gym.GoldenGym.utils.RandomGenerator;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class ServiceOrderEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String orderNumber = "ORD-" + RandomGenerator.generateRandomString(8);
    @ManyToOne
    private ServiceEntity service;
    @ManyToOne
    private User client;
    private String scheduledDateTime;
    private boolean completed = false;
    private boolean paid = false;

    public void setScheduledDateTime(String scheduledDateTime) {
        if(scheduledDateTime != null) {
            try {
                this.scheduledDateTime = DateUtils.getDate(scheduledDateTime);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
