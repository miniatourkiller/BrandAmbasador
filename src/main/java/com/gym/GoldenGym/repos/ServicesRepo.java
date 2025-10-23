package com.gym.GoldenGym.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gym.GoldenGym.entities.ServiceEntity;

public interface ServicesRepo extends JpaRepository<ServiceEntity, Long> {
    
}
