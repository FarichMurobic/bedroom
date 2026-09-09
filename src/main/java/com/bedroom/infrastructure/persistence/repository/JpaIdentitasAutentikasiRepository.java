/*
 * Copyright (c) 2026 Farich Murobic
 *
 * This project is licensed under the MIT License.
 * See the LICENSE file in the project root for more information.
 */

package com.bedroom.infrastructure.persistence.repository;

import com.bedroom.domain.identitas.enums.PenyediaAutentikasi;
import com.bedroom.infrastructure.persistence.entity.IdentitasAutentikasiEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

/**
 * Antarmuka Spring Data JPA untuk operasi dasar terhadap {@code IdentitasAutentikasiEntity}.
 */
public interface JpaIdentitasAutentikasiRepository
        extends JpaRepository<IdentitasAutentikasiEntity, UUID> {

    /**
     * Mencari identitas autentikasi berdasarkan email.
     *
     * @param email email yang dicari
     * @return optional identitas autentikasi
     */
    Optional<IdentitasAutentikasiEntity> findByEmail(String email);

    /**
     * Mencari identitas autentikasi berdasarkan nomor telepon.
     *
     * @param nomorTelepon nomor telepon yang dicari
     * @return optional identitas autentikasi
     */
    Optional<IdentitasAutentikasiEntity> findByNomorTelepon(String nomorTelepon);

    /**
     * Mencari identitas autentikasi berdasarkan penyedia dan pengenal eksternal.
     *
     * @param penyediaAutentikasi penyedia autentikasi (misal: GOOGLE)
     * @param pengenalEksternal pengenal eksternal dari penyedia
     * @return optional identitas autentikasi
     */
    Optional<IdentitasAutentikasiEntity> findByPenyediaAutentikasiAndPengenalEksternal(
            PenyediaAutentikasi penyediaAutentikasi,
            String pengenalEksternal
    );

    /**
     * Mengecek apakah identitas autentikasi dengan email tertentu sudah ada.
     *
     * @param email email yang dicek
     * @return true jika sudah ada, false jika belum
     */
    boolean existsByEmail(String email);

    /**
     * Mengecek apakah identitas autentikasi dengan nomor telepon tertentu sudah ada.
     *
     * @param nomorTelepon nomor telepon yang dicek
     * @return true jika sudah ada, false jika belum
     */
    boolean existsByNomorTelepon(String nomorTelepon);

    /**
     * Mengecek apakah identitas autentikasi dengan penyedia dan pengenal eksternal tertentu sudah ada.
     *
     * @param penyediaAutentikasi penyedia autentikasi
     * @param pengenalEksternal pengenal eksternal
     * @return true jika sudah ada, false jika belum
     */
    boolean existsByPenyediaAutentikasiAndPengenalEksternal(
            PenyediaAutentikasi penyediaAutentikasi,
            String pengenalEksternal
    );
}