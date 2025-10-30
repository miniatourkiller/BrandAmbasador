package com.gym.GoldenGym.entities;

import com.gym.GoldenGym.utils.DateUtils;
import com.gym.GoldenGym.utils.RandomGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class ServiceOrderEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String orderNumber = "SRV-" + RandomGenerator.generateRandomString(8);
    @ManyToOne
    private ServiceEntity service;
    @ManyToOne
    private User client;
    @ManyToOne
    private StoreLocation store;
    @ManyToOne
    private User assignedTo;
    private String scheduledDateTime;
    @Column(nullable = false)
    private String longitude;
    @Column(nullable = false)
    private String latitude;
    private boolean completed = false;
    private boolean paid = false;
    @OneToOne
    private PaymentsEntity payment;

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
