package com.gym.GoldenGym.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gym.GoldenGym.entities.Order;

public interface OrderRepo extends JpaRepository<Order, Long> {
    
}
