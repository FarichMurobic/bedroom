package com.bedroom.infrastructure.persistence.repository;

import com.bedroom.domain.identitas.enums.PenyediaAutentikasi;
import com.bedroom.infrastructure.persistence.entity.IdentitasAutentikasiEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

/**
 * Antarmuka Spring Data JPA untuk operasi dasar terhadap {@code IdentitasAutentikasiEntity}.
 */
public interface JpaIdentitasAutentikasiRepository
        extends JpaRepository<IdentitasAutentikasiEntity, UUID> {

    Optional<IdentitasAutentikasiEntity> findByEmail(String email);

    Optional<IdentitasAutentikasiEntity> findByNomorTelepon(String nomorTelepon);

    Optional<IdentitasAutentikasiEntity> findByPenyediaAutentikasiAndPengenalEksternal(
            PenyediaAutentikasi penyediaAutentikasi,
            String pengenalEksternal
    );

    boolean existsByEmail(String email);

    boolean existsByNomorTelepon(String nomorTelepon);

    boolean existsByPenyediaAutentikasiAndPengenalEksternal(
            PenyediaAutentikasi penyediaAutentikasi,
            String pengenalEksternal
    );
}