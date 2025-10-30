package com.gym.GoldenGym.repos;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gym.GoldenGym.entities.Order;

public interface OrderRepo extends JpaRepository<Order, Long> {

    Optional<Order> findByIdAndDeletedFalse(Long orderId);
    
}
