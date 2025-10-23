package com.gym.GoldenGym.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gym.GoldenGym.entities.User;

public interface UserRepo extends JpaRepository<User, Long> {
    
}
