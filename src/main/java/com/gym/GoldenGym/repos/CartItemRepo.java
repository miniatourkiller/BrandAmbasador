package com.gym.GoldenGym.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gym.GoldenGym.entities.CartItemEntity;

public interface CartItemRepo extends JpaRepository<CartItemEntity, Long> {
    
}
