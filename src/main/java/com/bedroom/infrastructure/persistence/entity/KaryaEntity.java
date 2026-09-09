/*
 * Copyright (c) 2026 Farich Murobic
 *
 * This project is licensed under the MIT License.
 * See the LICENSE file in the project root for more information.
 */

package com.bedroom.infrastructure.persistence.entity;

import com.bedroom.domain.karya.enums.StatusKarya;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;

import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * Representasi persistence dari entity domain {@code Karya}.
 * Memuat seluruh {@code BabEntity} dan {@code GenreEntity} yang terkait,
 * sesuai dengan batas aggregate pada domain layer.
 */
@Entity
@Table(name = "karya")
public class KaryaEntity {

    @Id
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @Column(name = "penulis_id", nullable = false, updatable = false)
    private UUID penulisId;

    @Column(name = "judul", nullable = false, length = 150)
    private String judul;

    @Column(name = "sinopsis", nullable = false, length = 500)
    private String sinopsis;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private StatusKarya status;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "karya_genre",
            joinColumns = @JoinColumn(name = "karya_id", foreignKey = @ForeignKey(name = "fk_karya_genre_karya")),
            inverseJoinColumns = @JoinColumn(name = "genre_id", foreignKey = @ForeignKey(name = "fk_karya_genre_genre"))
    )
    private Set<GenreEntity> genreList = new HashSet<>();

    @OneToMany(mappedBy = "karya", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @OrderBy("urutan ASC")
    private List<BabEntity> babList = new java.util.ArrayList<>();

    @Column(name = "dibuat_pada", nullable = false, updatable = false)
    private Instant dibuatPada;

    @Column(name = "diperbarui_pada", nullable = false)
    private Instant diperbaruiPada;

    protected KaryaEntity() {
        // Diperlukan oleh JPA/Hibernate
    }

    public KaryaEntity(
            UUID id, UUID penulisId, String judul, String sinopsis, StatusKarya status,
            Set<GenreEntity> genreList, List<BabEntity> babList,
            Instant dibuatPada, Instant diperbaruiPada
    ) {
        this.id = id;
        this.penulisId = penulisId;
        this.judul = judul;
        this.sinopsis = sinopsis;
        this.status = status;
        this.genreList = genreList;
        this.babList = babList;
        this.dibuatPada = dibuatPada;
        this.diperbaruiPada = diperbaruiPada;
    }

    public UUID getId() {
        return id;
    }

    public UUID getPenulisId() {
        return penulisId;
    }

    public String getJudul() {
        return judul;
    }

    public String getSinopsis() {
        return sinopsis;
    }

    public StatusKarya getStatus() {
        return status;
    }

    public Set<GenreEntity> getGenreList() {
        return genreList;
    }

    public List<BabEntity> getBabList() {
        return babList;
    }

    public Instant getDibuatPada() {
        return dibuatPada;
    }

    public Instant getDiperbaruiPada() {
        return diperbaruiPada;
    }
}