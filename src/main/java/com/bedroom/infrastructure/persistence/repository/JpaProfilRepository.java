/*
 * Copyright (c) 2026 Farich Murobic
 *
 * This project is licensed under the MIT License.
 * See the LICENSE file in the project root for more information.
 */

package com.bedroom.infrastructure.persistence.repository;

import com.bedroom.infrastructure.persistence.entity.ProfilEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * JPA repository untuk entity {@code ProfilEntity}.
 */
public interface JpaProfilRepository extends JpaRepository<ProfilEntity, UUID> {
}