/*
 * Copyright (c) 2026 Farich Murobic
 *
 * This project is licensed under the MIT License.
 * See the LICENSE file in the project root for more information.
 */

package com.bedroom.infrastructure.persistence.repository;

import com.bedroom.infrastructure.persistence.entity.GenreEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * JPA repository untuk entity {@code GenreEntity}.
 */
public interface JpaGenreRepository extends JpaRepository<GenreEntity, UUID> {

    /**
     * Mengecek apakah genre dengan nama tertentu sudah ada.
     *
     * @param nama nama genre yang dicek
     * @return true jika sudah ada, false jika belum
     */
    boolean existsByNama(String nama);
}