/*
 * Copyright (c) 2026 Farich Murobic
 *
 * This project is licensed under the MIT License.
 * See the LICENSE file in the project root for more information.
 */

package com.bedroom.infrastructure.persistence.repository;

import com.bedroom.infrastructure.persistence.entity.PenggunaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

/**
 * Antarmuka Spring Data JPA untuk operasi dasar terhadap {@code PenggunaEntity}.
 */
public interface JpaPenggunaRepository extends JpaRepository<PenggunaEntity, UUID> {

    /**
     * Mencari pengguna berdasarkan nama pengguna.
     *
     * @param namaPengguna nama pengguna yang dicari
     * @return optional pengguna
     */
    Optional<PenggunaEntity> findByNamaPengguna(String namaPengguna);

    /**
     * Mengecek apakah pengguna dengan nama pengguna tertentu sudah ada.
     *
     * @param namaPengguna nama pengguna yang dicek
     * @return true jika sudah ada, false jika belum
     */
    boolean existsByNamaPengguna(String namaPengguna);
}