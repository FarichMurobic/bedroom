/*
 * Copyright (c) 2026 Farich Murobic
 *
 * This project is licensed under the MIT License.
 * See the LICENSE file in the project root for more information.
 */

package com.bedroom.infrastructure.persistence.repository;

import com.bedroom.infrastructure.persistence.entity.SukaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

/**
 * JPA repository untuk entity {@code SukaEntity}.
 */
public interface JpaSukaRepository extends JpaRepository<SukaEntity, UUID> {

    /**
     * Mencari data like berdasarkan ID pengguna dan ID karya.
     *
     * @param penggunaId ID pengguna
     * @param karyaId ID karya
     * @return optional data like
     */
    Optional<SukaEntity> findByPenggunaIdAndKaryaId(UUID penggunaId, UUID karyaId);

    /**
     * Menghitung jumlah like pada sebuah karya.
     *
     * @param karyaId ID karya
     * @return jumlah like
     */
    long countByKaryaId(UUID karyaId);
}