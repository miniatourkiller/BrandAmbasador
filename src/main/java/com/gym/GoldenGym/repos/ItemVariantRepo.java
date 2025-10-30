package com.gym.GoldenGym.repos;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gym.GoldenGym.entities.Item;
import com.gym.GoldenGym.entities.ItemVariant;

public interface ItemVariantRepo extends JpaRepository<ItemVariant, Long> {

    List<ItemVariant> findByItemAndDeletedFalse(Item item);

    public Optional<ItemVariant> findByIdAndDeletedFalse(Long variantId);
    
}
