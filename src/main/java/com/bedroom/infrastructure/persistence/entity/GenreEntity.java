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
 * Representasi persistence dari entity domain {@code Genre}.
 */
@Entity
@Table(
        name = "genre",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_genre_nama", columnNames = "nama")
        }
)
public class GenreEntity {

    @Id
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @Column(name = "nama", nullable = false, length = 30)
    private String nama;

    @Column(name = "dibuat_pada", nullable = false, updatable = false)
    private Instant dibuatPada;

    protected GenreEntity() {
        // Diperlukan oleh JPA/Hibernate
    }

    public GenreEntity(UUID id, String nama, Instant dibuatPada) {
        this.id = id;
        this.nama = nama;
        this.dibuatPada = dibuatPada;
    }

    public UUID getId() {
        return id;
    }

    public String getNama() {
        return nama;
    }

    public Instant getDibuatPada() {
        return dibuatPada;
    }
}