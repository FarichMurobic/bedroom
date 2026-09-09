/*
 * Copyright (c) 2026 Farich Murobic
 *
 * This project is licensed under the MIT License.
 * See the LICENSE file in the project root for more information.
 */

package com.bedroom.infrastructure.persistence.entity;

import com.bedroom.domain.identitas.enums.PenyediaAutentikasi;
import jakarta.persistence.*;

import java.time.Instant;
import java.util.UUID;

/**
 * Representasi persistence dari entity domain {@code IdentitasAutentikasi}.
 * Field {@code email}, {@code nomorTelepon}, {@code pengenalEksternal}, dan
 * {@code hashKataSandi} bersifat nullable karena hanya sebagian yang terisi
 * tergantung {@code penyediaAutentikasi}.
 */
@Entity
@Table(
        name = "identitas_autentikasi",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_identitas_autentikasi_email",
                        columnNames = "email"
                ),
                @UniqueConstraint(
                        name = "uk_identitas_autentikasi_nomor_telepon",
                        columnNames = "nomor_telepon"
                ),
                @UniqueConstraint(
                        name = "uk_identitas_autentikasi_penyedia_pengenal",
                        columnNames = {
                                "penyedia_autentikasi",
                                "pengenal_eksternal"
                        }
                )
        }
)
public class IdentitasAutentikasiEntity {

    @Id
    @Column(
            name = "id",
            nullable = false,
            updatable = false
    )
    private UUID id;

    @Column(
            name = "pengguna_id",
            nullable = false,
            updatable = false
    )
    private UUID penggunaId;

    @Enumerated(EnumType.STRING)
    @Column(
            name = "penyedia_autentikasi",
            nullable = false,
            length = 20,
            updatable = false
    )
    private PenyediaAutentikasi penyediaAutentikasi;

    @Column(
            name = "email",
            length = 100
    )
    private String email;

    @Column(
            name = "nomor_telepon",
            length = 20
    )
    private String nomorTelepon;

    @Column(
            name = "pengenal_eksternal",
            length = 255
    )
    private String pengenalEksternal;

    @Column(name = "hash_kata_sandi")
    private String hashKataSandi;

    @Column(
            name = "dibuat_pada",
            nullable = false,
            updatable = false
    )
    private Instant dibuatPada;

    @Column(
            name = "diperbarui_pada",
            nullable = false
    )
    private Instant diperbaruiPada;

    protected IdentitasAutentikasiEntity() {
        // Untuk JPA/Hibernate
    }

    public IdentitasAutentikasiEntity(
            UUID id,
            UUID penggunaId,
            PenyediaAutentikasi penyediaAutentikasi,
            String email,
            String nomorTelepon,
            String pengenalEksternal,
            String hashKataSandi,
            Instant dibuatPada,
            Instant diperbaruiPada
    ) {
        this.id = id;
        this.penggunaId = penggunaId;
        this.penyediaAutentikasi = penyediaAutentikasi;
        this.email = email;
        this.nomorTelepon = nomorTelepon;
        this.pengenalEksternal = pengenalEksternal;
        this.hashKataSandi = hashKataSandi;
        this.dibuatPada = dibuatPada;
        this.diperbaruiPada = diperbaruiPada;
    }

    public UUID getId() {
        return id;
    }

    public UUID getPenggunaId() {
        return penggunaId;
    }

    public PenyediaAutentikasi getPenyediaAutentikasi() {
        return penyediaAutentikasi;
    }

    public String getEmail() {
        return email;
    }

    public String getNomorTelepon() {
        return nomorTelepon;
    }

    public String getPengenalEksternal() {
        return pengenalEksternal;
    }

    public String getHashKataSandi() {
        return hashKataSandi;
    }

    public Instant getDibuatPada() {
        return dibuatPada;
    }

    public Instant getDiperbaruiPada() {
        return diperbaruiPada;
    }
}