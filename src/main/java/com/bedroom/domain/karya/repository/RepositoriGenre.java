/*
 * Copyright (c) 2026 Farich Murobic
 *
 * This project is licensed under the MIT License.
 * See the LICENSE file in the project root for more information.
 */

package com.bedroom.domain.karya.repository;

import com.bedroom.domain.karya.model.Genre;
import com.bedroom.domain.karya.valueobject.IdGenre;
import com.bedroom.domain.karya.valueobject.NamaGenre;

import java.util.List;
import java.util.Optional;

/**
 * Port untuk operasi penyimpanan dan pengambilan data {@code Genre}.
 *
 * <p>Interface ini mendefinisikan kontrak akses data yang dibutuhkan
 * domain tanpa bergantung pada teknologi atau mekanisme penyimpanan
 * tertentu. Implementasi konkret disediakan oleh lapisan infrastruktur.</p>
 */
public interface RepositoriGenre {

    /**
     * Menyimpan genre.
     *
     * @param genre genre yang akan disimpan
     * @return genre yang telah disimpan
     */
    Genre simpan(Genre genre);

    /**
     * Mencari genre berdasarkan ID.
     *
     * @param id ID genre yang dicari
     * @return {@code Optional} berisi genre jika ditemukan,
     *         atau kosong jika tidak ditemukan
     */
    Optional<Genre> cariBerdasarkanId(IdGenre id);

    /**
     * Mengambil seluruh genre yang tersedia.
     *
     * @return daftar seluruh genre
     */
    List<Genre> cariSemua();

    /**
     * Memeriksa apakah genre dengan nama tertentu sudah tersedia.
     *
     * @param nama nama genre yang akan diperiksa
     * @return {@code true} jika genre dengan nama tersebut sudah ada,
     *         atau {@code false} jika belum ada
     */
    boolean adaBerdasarkanNama(NamaGenre nama);

    /**
     * Menghapus genre berdasarkan ID.
     *
     * @param id ID genre yang akan dihapus
     */
    void hapusBerdasarkanId(IdGenre id);
}