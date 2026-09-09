/*
 * Copyright (c) 2026 Farich Murobic
 *
 * This project is licensed under the MIT License.
 * See the LICENSE file in the project root for more information.
 */

package com.bedroom.domain.interaksi.repository;

import com.bedroom.domain.identitas.valueobject.IdPengguna;
import com.bedroom.domain.interaksi.model.Suka;
import com.bedroom.domain.karya.valueobject.IdKarya;

import java.util.Optional;

/**
 * Port untuk operasi penyimpanan dan pengambilan data {@code Suka}.
 *
 * <p>Interface ini mendefinisikan kontrak akses data yang dibutuhkan
 * domain tanpa bergantung pada teknologi atau mekanisme penyimpanan
 * tertentu. Implementasi konkret disediakan oleh lapisan infrastruktur.</p>
 */
public interface RepositoriSuka {

    /**
     * Menyimpan relasi {@code Suka}.
     *
     * @param suka relasi suka yang akan disimpan
     * @return relasi suka yang telah disimpan
     */
    Suka simpan(Suka suka);

    /**
     * Mencari relasi suka berdasarkan pengguna dan karya.
     *
     * @param idPengguna ID pengguna yang memberikan suka
     * @param idKarya ID karya yang disukai
     * @return {@code Optional} berisi relasi suka jika ditemukan,
     *         atau kosong jika tidak ditemukan
     */
    Optional<Suka> cariBerdasarkanPenggunaDanKarya(
            IdPengguna idPengguna,
            IdKarya idKarya
    );

    /**
     * Menghitung jumlah suka yang diberikan terhadap sebuah karya.
     *
     * @param idKarya ID karya yang jumlah sukanya akan dihitung
     * @return jumlah suka yang dimiliki karya
     */
    long hitungBerdasarkanKarya(IdKarya idKarya);

    /**
     * Menghapus relasi {@code Suka}.
     *
     * @param suka relasi suka yang akan dihapus
     */
    void hapus(Suka suka);
}