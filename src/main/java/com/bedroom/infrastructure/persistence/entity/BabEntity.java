/*
 * Copyright (c) 2026 Farich Murobic
 *
 * This project is licensed under the MIT License.
 * See the LICENSE file in the project root for more information.
 */

package com.bedroom.infrastructure.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.time.Instant;
import java.util.UUID;

/**
 * Representasi persistence dari entity domain {@code Bab}.
 * Selalu terikat pada sebuah {@code KaryaEntity} sebagai induknya —
 * tidak memiliki repository tersendiri, sejalan dengan aturan aggregate
 * pada domain layer.
 */
@Entity
@Table(name = "bab")
public class BabEntity {

    @Id
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "karya_id", nullable = false, foreignKey = @ForeignKey(name = "fk_bab_karya"))
    private KaryaEntity karya;

    @Column(name = "judul", nullable = false, length = 150)
    private String judul;

    @Column(name = "isi", nullable = false, columnDefinition = "LONGTEXT")
    private String isi;

    @Column(name = "urutan", nullable = false)
    private int urutan;

    @Column(name = "dibuat_pada", nullable = false, updatable = false)
    private Instant dibuatPada;

    @Column(name = "diperbarui_pada", nullable = false)
    private Instant diperbaruiPada;

    protected BabEntity() {
        // Diperlukan oleh JPA/Hibernate
    }

    public BabEntity(
            UUID id, KaryaEntity karya, String judul, String isi, int urutan,
            Instant dibuatPada, Instant diperbaruiPada
    ) {
        this.id = id;
        this.karya = karya;
        this.judul = judul;
        this.isi = isi;
        this.urutan = urutan;
        this.dibuatPada = dibuatPada;
        this.diperbaruiPada = diperbaruiPada;
    }

    public UUID getId() {
        return id;
    }

    public KaryaEntity getKarya() {
        return karya;
    }

    public String getJudul() {
        return judul;
    }

    public String getIsi() {
        return isi;
    }

    public int getUrutan() {
        return urutan;
    }

    public Instant getDibuatPada() {
        return dibuatPada;
    }

    public Instant getDiperbaruiPada() {
        return diperbaruiPada;
    }
}