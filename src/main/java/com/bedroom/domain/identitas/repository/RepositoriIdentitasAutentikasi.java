/*
 * Copyright (c) 2026 Farich Murobic
 *
 * This project is licensed under the MIT License.
 * See the LICENSE file in the project root for more information.
 */

package com.bedroom.domain.identitas.repository;

import com.bedroom.domain.identitas.enums.PenyediaAutentikasi;
import com.bedroom.domain.identitas.model.IdentitasAutentikasi;
import com.bedroom.domain.identitas.valueobject.Email;
import com.bedroom.domain.identitas.valueobject.IdIdentitasAutentikasi;
import com.bedroom.domain.identitas.valueobject.NomorTelepon;
import com.bedroom.domain.identitas.valueobject.PengenalEksternal;

import java.util.Optional;

/**
 * Port untuk operasi penyimpanan dan pengambilan data
 * {@code IdentitasAutentikasi}.
 *
 * <p>Interface ini mendefinisikan kontrak akses data yang dibutuhkan
 * domain tanpa bergantung pada teknologi atau mekanisme penyimpanan
 * tertentu. Implementasi konkret disediakan oleh lapisan infrastruktur.</p>
 */
public interface RepositoriIdentitasAutentikasi {

    /**
     * Menyimpan identitas autentikasi.
     *
     * @param identitasAutentikasi identitas autentikasi yang akan disimpan
     * @return identitas autentikasi yang telah disimpan
     */
    IdentitasAutentikasi simpan(IdentitasAutentikasi identitasAutentikasi);

    /**
     * Mencari identitas autentikasi berdasarkan ID.
     *
     * @param id ID identitas autentikasi yang dicari
     * @return {@code Optional} berisi identitas autentikasi jika ditemukan,
     *         atau kosong jika tidak ditemukan
     */
    Optional<IdentitasAutentikasi> cariBerdasarkanId(IdIdentitasAutentikasi id);

    /**
     * Mencari identitas autentikasi berdasarkan email.
     *
     * @param email email yang digunakan sebagai kriteria pencarian
     * @return {@code Optional} berisi identitas autentikasi jika ditemukan,
     *         atau kosong jika tidak ditemukan
     */
    Optional<IdentitasAutentikasi> cariBerdasarkanEmail(Email email);

    /**
     * Mencari identitas autentikasi berdasarkan nomor telepon.
     *
     * @param nomorTelepon nomor telepon yang digunakan sebagai kriteria pencarian
     * @return {@code Optional} berisi identitas autentikasi jika ditemukan,
     *         atau kosong jika tidak ditemukan
     */
    Optional<IdentitasAutentikasi> cariBerdasarkanNomorTelepon(NomorTelepon nomorTelepon);

    /**
     * Mencari identitas autentikasi berdasarkan penyedia autentikasi
     * dan pengenal eksternal.
     *
     * @param penyediaAutentikasi penyedia autentikasi yang digunakan
     * @param pengenalEksternal pengenal pengguna pada penyedia eksternal
     * @return {@code Optional} berisi identitas autentikasi jika ditemukan,
     *         atau kosong jika tidak ditemukan
     */
    Optional<IdentitasAutentikasi> cariBerdasarkanPenyediaDanPengenalEksternal(
            PenyediaAutentikasi penyediaAutentikasi,
            PengenalEksternal pengenalEksternal
    );

    /**
     * Memeriksa apakah terdapat identitas autentikasi dengan email tertentu.
     *
     * @param email email yang akan diperiksa
     * @return {@code true} jika identitas dengan email tersebut tersedia,
     *         atau {@code false} jika tidak ada
     */
    boolean adaBerdasarkanEmail(Email email);

    /**
     * Memeriksa apakah terdapat identitas autentikasi dengan nomor telepon tertentu.
     *
     * @param nomorTelepon nomor telepon yang akan diperiksa
     * @return {@code true} jika identitas dengan nomor telepon tersebut tersedia,
     *         atau {@code false} jika tidak ada
     */
    boolean adaBerdasarkanNomorTelepon(NomorTelepon nomorTelepon);

    /**
     * Memeriksa apakah terdapat identitas autentikasi berdasarkan penyedia
     * autentikasi dan pengenal eksternal tertentu.
     *
     * @param penyediaAutentikasi penyedia autentikasi yang digunakan
     * @param pengenalEksternal pengenal pengguna pada penyedia eksternal
     * @return {@code true} jika identitas dengan kombinasi tersebut tersedia,
     *         atau {@code false} jika tidak ada
     */
    boolean adaBerdasarkanPenyediaDanPengenalEksternal(
            PenyediaAutentikasi penyediaAutentikasi,
            PengenalEksternal pengenalEksternal
    );

    /**
     * Menghapus identitas autentikasi berdasarkan ID.
     *
     * @param id ID identitas autentikasi yang akan dihapus
     */
    void hapusBerdasarkanId(IdIdentitasAutentikasi id);
}