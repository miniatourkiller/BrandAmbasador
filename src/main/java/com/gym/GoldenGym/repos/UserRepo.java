package com.gym.GoldenGym.repos;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gym.GoldenGym.entities.User;

public interface UserRepo extends JpaRepository<User, Long> {

    public Optional<User> findByEmail(String email);
    
}
