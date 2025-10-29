package com.gym.GoldenGym.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gym.GoldenGym.entities.ItemVariant;
import com.gym.GoldenGym.entities.StoreInventory;
import com.gym.GoldenGym.entities.StoreLocation;

public interface StoreInventoryRepo extends JpaRepository<StoreInventory, Long> {

    StoreInventory findByItemVariantAndStoreLocation(ItemVariant itemVariant, StoreLocation storeLocation);
    
}
