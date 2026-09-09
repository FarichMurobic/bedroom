/*
 * Copyright (c) 2026 Farich Murobic
 *
 * This project is licensed under the MIT License.
 * See the LICENSE file in the project root for more information.
 */

package com.bedroom.domain.identitas.repository;

import com.bedroom.domain.identitas.model.Pengguna;
import com.bedroom.domain.identitas.valueobject.IdPengguna;
import com.bedroom.domain.identitas.valueobject.NamaPengguna;

import java.util.Optional;

/**
 * Port untuk operasi penyimpanan dan pengambilan data {@code Pengguna}.
 *
 * <p>Interface ini mendefinisikan kontrak akses data yang dibutuhkan
 * domain tanpa bergantung pada teknologi atau mekanisme penyimpanan
 * tertentu. Implementasi konkret disediakan oleh lapisan infrastruktur.</p>
 */
public interface RepositoriPengguna {

    /**
     * Menyimpan pengguna.
     *
     * @param pengguna pengguna yang akan disimpan
     * @return pengguna yang telah disimpan
     */
    Pengguna simpan(Pengguna pengguna);

    /**
     * Mencari pengguna berdasarkan ID.
     *
     * @param id ID pengguna yang dicari
     * @return {@code Optional} berisi pengguna jika ditemukan,
     *         atau kosong jika tidak ditemukan
     */
    Optional<Pengguna> cariBerdasarkanId(IdPengguna id);

    /**
     * Mencari pengguna berdasarkan nama pengguna.
     *
     * @param namaPengguna nama pengguna yang digunakan sebagai kriteria pencarian
     * @return {@code Optional} berisi pengguna jika ditemukan,
     *         atau kosong jika tidak ditemukan
     */
    Optional<Pengguna> cariBerdasarkanNamaPengguna(NamaPengguna namaPengguna);

    /**
     * Memeriksa apakah terdapat pengguna dengan nama pengguna tertentu.
     *
     * @param namaPengguna nama pengguna yang akan diperiksa
     * @return {@code true} jika nama pengguna tersebut telah digunakan,
     *         atau {@code false} jika belum digunakan
     */
    boolean adaBerdasarkanNamaPengguna(NamaPengguna namaPengguna);

    /**
     * Menghapus pengguna berdasarkan ID.
     *
     * @param id ID pengguna yang akan dihapus
     */
    void hapusBerdasarkanId(IdPengguna id);
}