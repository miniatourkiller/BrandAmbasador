package com.gym.GoldenGym.entities;

import com.gym.GoldenGym.utils.DateUtils;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class PaymentsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String paymentMethod;
    private String paymentRef;
    private double amount;
    @Column(nullable = false, updatable = false)
    private String paymentDate = DateUtils.currentDate();
    private String reversalReason;
    private String reversalDate;
    private boolean reversed = false;
}
