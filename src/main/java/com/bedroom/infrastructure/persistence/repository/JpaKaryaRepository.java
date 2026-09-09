/*
 * Copyright (c) 2026 Farich Murobic
 *
 * This project is licensed under the MIT License.
 * See the LICENSE file in the project root for more information.
 */

package com.bedroom.infrastructure.persistence.repository;

import com.bedroom.domain.karya.enums.StatusKarya;
import com.bedroom.infrastructure.persistence.entity.KaryaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

/**
 * JPA repository untuk entity {@code KaryaEntity}.
 */
public interface JpaKaryaRepository extends JpaRepository<KaryaEntity, UUID> {

    /**
     * Mencari karya berdasarkan ID penulis.
     *
     * @param penulisId ID penulis
     * @return daftar karya milik penulis
     */
    List<KaryaEntity> findByPenulisId(UUID penulisId);

    /**
     * Mencari karya berdasarkan status.
     *
     * @param status status karya
     * @return daftar karya dengan status tertentu
     */
    List<KaryaEntity> findByStatus(StatusKarya status);
}