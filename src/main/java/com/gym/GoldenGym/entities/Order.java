package com.gym.GoldenGym.entities;

import com.gym.GoldenGym.utils.RandomGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "tb_orders")
public class Order extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String orderNumber = "ORD-" + RandomGenerator.generateRandomString(8);
    @ManyToOne
    private ItemVariant itemVariant;
    @ManyToOne
    private User customer;
    private int units;
    private double totalAmount;
    private double transportCharge = 0.0;
    @ManyToOne
    private StoreLocation assignedStore;
    private boolean paid = false;
    private boolean delivered = false;
    @Column(columnDefinition = "TEXT")
    private String deliveryNotes;
    private String longitude;
    private String latitude;
}
