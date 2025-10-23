package com.gym.GoldenGym.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gym.GoldenGym.entities.StoreLocation;

public interface StoreLocationRepo extends JpaRepository<StoreLocation, Long> {
    
}
