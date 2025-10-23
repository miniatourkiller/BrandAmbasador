package com.gym.GoldenGym.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gym.GoldenGym.entities.SettingsEntity;

public interface SettingsRepo extends JpaRepository<SettingsEntity, Long> {
    
}
