package com.gym.GoldenGym.repos;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.gym.GoldenGym.entities.User;

public interface UserRepo extends JpaRepository<User, Long> {

    public Optional<User> findByEmail(String email);

    @Query(value = "SELECT u FROM User u WHERE u.email LIKE %?1%", nativeQuery = false)
    public Page<User> autocompleteUser(String emailQuery, Pageable pageable);
}
