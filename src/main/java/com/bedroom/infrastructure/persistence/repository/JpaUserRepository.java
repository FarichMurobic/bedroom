package com.bedroom.infrastructure.persistence.repository;

import com.bedroom.infrastructure.persistence.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface JpaUserRepository
        extends JpaRepository<UserEntity, UUID> {

    Optional<UserEntity> findByUsername(String username);

    boolean existsByUsername(String username);
}