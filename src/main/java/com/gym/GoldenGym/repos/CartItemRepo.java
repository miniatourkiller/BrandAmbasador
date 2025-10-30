package com.gym.GoldenGym.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.gym.GoldenGym.entities.CartItemEntity;

public interface CartItemRepo extends JpaRepository<CartItemEntity, Long> {
    @Query("SELECT c FROM CartItemEntity c WHERE c.id IN ?1 AND c.deleted = false")
    public List<CartItemEntity> findByIdsInAndDeletedFalse(List<Long> ids);
}
