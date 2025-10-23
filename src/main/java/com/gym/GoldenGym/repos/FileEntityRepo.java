package com.gym.GoldenGym.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gym.GoldenGym.entities.FileEntity;

public interface FileEntityRepo extends JpaRepository<FileEntity, Long> {
    
}
