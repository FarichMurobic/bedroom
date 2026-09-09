/*
 * Copyright (c) 2026 Farich Murobic
 *
 * This project is licensed under the MIT License.
 * See the LICENSE file in the project root for more information.
 */

package com.bedroom.infrastructure.persistence.entity;

import com.bedroom.domain.identitas.enums.Peran;
import com.bedroom.domain.identitas.enums.StatusPengguna;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

import java.time.Instant;
import java.util.EnumSet;
import java.util.Set;
import java.util.UUID;

/**
 * Representasi persistence dari entity domain {@code Pengguna}.
 */
@Entity
@Table(
        name = "pengguna",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_pengguna_nama_pengguna", columnNames = "nama_pengguna")
        }
)
public class PenggunaEntity {

    @Id
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @Column(name = "nama_pengguna", nullable = false, length = 30)
    private String namaPengguna;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 30)
    private StatusPengguna status;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "pengguna_peran",
            joinColumns = @JoinColumn(name = "pengguna_id", foreignKey = @ForeignKey(name = "fk_pengguna_peran_pengguna"))
    )
    @Enumerated(EnumType.STRING)
    @Column(name = "peran", nullable = false, length = 20)
    private Set<Peran> perans = EnumSet.noneOf(Peran.class);

    @Column(name = "dibuat_pada", nullable = false, updatable = false)
    private Instant dibuatPada;

    @Column(name = "diperbarui_pada", nullable = false)
    private Instant diperbaruiPada;

    @Column(name = "login_terakhir")
    private Instant loginTerakhir;

    protected PenggunaEntity() {
        // Diperlukan oleh JPA/Hibernate
    }

    public PenggunaEntity(
            UUID id,
            String namaPengguna,
            StatusPengguna status,
            Set<Peran> perans,
            Instant dibuatPada,
            Instant diperbaruiPada,
            Instant loginTerakhir
    ) {
        this.id = id;
        this.namaPengguna = namaPengguna;
        this.status = status;
        this.perans = perans;
        this.dibuatPada = dibuatPada;
        this.diperbaruiPada = diperbaruiPada;
        this.loginTerakhir = loginTerakhir;
    }

    public UUID getId() {
        return id;
    }

    public String getNamaPengguna() {
        return namaPengguna;
    }

    public StatusPengguna getStatus() {
        return status;
    }

    public Set<Peran> getPerans() {
        return perans;
    }

    public Instant getDibuatPada() {
        return dibuatPada;
    }

    public Instant getDiperbaruiPada() {
        return diperbaruiPada;
    }

    public Instant getLoginTerakhir() {
        return loginTerakhir;
    }
}