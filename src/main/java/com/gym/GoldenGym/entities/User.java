package com.gym.GoldenGym.entities;

import com.gym.GoldenGym.entities.enums.Roles;
import com.gym.GoldenGym.utils.DateUtils;

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
@Table(name = "tb_users")
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String fullName;
    private String contact;
    private String idNumber;
    private String password;
    private Roles role;
    private String otp;
    @Setter(lombok.AccessLevel.NONE)
    private String otpExpiry;
    @ManyToOne
    private StoreLocation storeLocation;

    public void setOtp(String otp) {
        this.otp = otp;
        this.otpExpiry = DateUtils.dateTodayPlusMinutes(5);
    }
}
