package com.gym.GoldenGym.repos;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gym.GoldenGym.entities.ServiceEntity;

public interface ServicesRepo extends JpaRepository<ServiceEntity, Long> {

    public Optional<ServiceEntity> findByIdAndDeletedFalse(Long serviceId);

}
