package com.gym.GoldenGym.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gym.GoldenGym.entities.StoreLocation;

public interface StoreLocationRepo extends JpaRepository<StoreLocation, Long> {

    public List<StoreLocation> findByDeletedFalse();
    
}
