/*
 * Copyright (c) 2026 Farich Murobic
 *
 * This project is licensed under the MIT License.
 * See the LICENSE file in the project root for more information.
 */

package com.bedroom.infrastructure.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

import java.time.Instant;
import java.util.UUID;

/**
 * Representasi persistence dari entity domain {@code Suka}.
 */
@Entity
@Table(
        name = "suka",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_suka_pengguna_karya", columnNames = {"pengguna_id", "karya_id"})
        }
)
public class SukaEntity {

    @Id
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @Column(name = "pengguna_id", nullable = false, updatable = false)
    private UUID penggunaId;

    @Column(name = "karya_id", nullable = false, updatable = false)
    private UUID karyaId;

    @Column(name = "disukai_pada", nullable = false, updatable = false)
    private Instant disukaiPada;

    protected SukaEntity() {
        // Diperlukan oleh JPA/Hibernate
    }

    public SukaEntity(UUID id, UUID penggunaId, UUID karyaId, Instant disukaiPada) {
        this.id = id;
        this.penggunaId = penggunaId;
        this.karyaId = karyaId;
        this.disukaiPada = disukaiPada;
    }

    public UUID getId() {
        return id;
    }

    public UUID getPenggunaId() {
        return penggunaId;
    }

    public UUID getKaryaId() {
        return karyaId;
    }

    public Instant getDisukaiPada() {
        return disukaiPada;
    }
}