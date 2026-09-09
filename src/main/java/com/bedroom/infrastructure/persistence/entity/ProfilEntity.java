/*
 * Copyright (c) 2026 Farich Murobic
 *
 * This project is licensed under the MIT License.
 * See the LICENSE file in the project root for more information.
 */

package com.bedroom.infrastructure.persistence.entity;

import com.bedroom.domain.identitas.enums.JenisKelamin;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

/**
 * Representasi persistence dari entity domain {@code Profil}.
 * Diidentifikasi langsung oleh {@code pengguna_id} (relasi satu-ke-satu murni).
 */
@Entity
@Table(name = "profil")
public class ProfilEntity {

    @Id
    @Column(name = "pengguna_id", nullable = false, updatable = false)
    private UUID penggunaId;

    @Column(name = "nama_lengkap", length = 100)
    private String namaLengkap;

    @Column(name = "tanggal_lahir")
    private LocalDate tanggalLahir;

    @Enumerated(EnumType.STRING)
    @Column(name = "jenis_kelamin", length = 20)
    private JenisKelamin jenisKelamin;

    @Column(name = "url_avatar", length = 500)
    private String urlAvatar;

    @Column(name = "diperbarui_pada", nullable = false)
    private Instant diperbaruiPada;

    protected ProfilEntity() {
        // Diperlukan oleh JPA/Hibernate
    }

    public ProfilEntity(
            UUID penggunaId, String namaLengkap, LocalDate tanggalLahir,
            JenisKelamin jenisKelamin, String urlAvatar, Instant diperbaruiPada
    ) {
        this.penggunaId = penggunaId;
        this.namaLengkap = namaLengkap;
        this.tanggalLahir = tanggalLahir;
        this.jenisKelamin = jenisKelamin;
        this.urlAvatar = urlAvatar;
        this.diperbaruiPada = diperbaruiPada;
    }

    public UUID getPenggunaId() {
        return penggunaId;
    }

    public String getNamaLengkap() {
        return namaLengkap;
    }

    public LocalDate getTanggalLahir() {
        return tanggalLahir;
    }

    public JenisKelamin getJenisKelamin() {
        return jenisKelamin;
    }

    public String getUrlAvatar() {
        return urlAvatar;
    }

    public Instant getDiperbaruiPada() {
        return diperbaruiPada;
    }
}