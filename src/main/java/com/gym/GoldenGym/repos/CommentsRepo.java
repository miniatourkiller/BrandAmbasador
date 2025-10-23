package com.gym.GoldenGym.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gym.GoldenGym.entities.Comments;

public interface CommentsRepo extends JpaRepository<Comments, Long> {
    
}
