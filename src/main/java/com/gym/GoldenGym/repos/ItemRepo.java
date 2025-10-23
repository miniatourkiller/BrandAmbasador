package com.gym.GoldenGym.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gym.GoldenGym.entities.Item;

public interface ItemRepo extends JpaRepository<Item, Long> {
    
}
