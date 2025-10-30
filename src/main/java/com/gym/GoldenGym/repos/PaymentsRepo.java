package com.gym.GoldenGym.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gym.GoldenGym.entities.PaymentsEntity;

public interface PaymentsRepo extends JpaRepository<PaymentsEntity, Long>{
    
}
