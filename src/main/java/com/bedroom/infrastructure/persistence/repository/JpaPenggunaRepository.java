package com.bedroom.infrastructure.persistence.repository;

import com.bedroom.infrastructure.persistence.entity.PenggunaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

/**
 * Antarmuka Spring Data JPA untuk operasi dasar terhadap {@code PenggunaEntity}.
 */
public interface JpaPenggunaRepository extends JpaRepository<PenggunaEntity, UUID> {

    Optional<PenggunaEntity> findByNamaPengguna(String namaPengguna);

    boolean existsByNamaPengguna(String namaPengguna);
}