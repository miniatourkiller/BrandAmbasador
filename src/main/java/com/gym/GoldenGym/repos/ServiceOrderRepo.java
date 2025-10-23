package com.gym.GoldenGym.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gym.GoldenGym.entities.ServiceOrderEntity;

public interface ServiceOrderRepo extends JpaRepository<ServiceOrderEntity, Long>{
    
}
