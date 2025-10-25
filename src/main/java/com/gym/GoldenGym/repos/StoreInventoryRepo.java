package com.gym.GoldenGym.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gym.GoldenGym.entities.StoreInventory;

public interface StoreInventoryRepo extends JpaRepository<StoreInventory, Long> {
    
}
