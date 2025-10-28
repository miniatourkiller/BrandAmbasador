package com.gym.GoldenGym.repos;


import org.springframework.data.jpa.repository.JpaRepository;

import com.gym.GoldenGym.entities.Category;

public interface CategoryRepo extends JpaRepository<Category, Long>{
    
}
