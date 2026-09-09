/*
 * Copyright (c) 2026 Farich Murobic
 *
 * This project is licensed under the MIT License.
 * See the LICENSE file in the project root for more information.
 */

package com.bedroom.domain.karya.repository;

import com.bedroom.domain.identitas.valueobject.IdPengguna;
import com.bedroom.domain.karya.enums.StatusKarya;
import com.bedroom.domain.karya.model.Karya;
import com.bedroom.domain.karya.valueobject.IdKarya;

import java.util.List;
import java.util.Optional;

/**
 * Port untuk operasi penyimpanan dan pengambilan data {@code Karya}
 * beserta seluruh {@code Bab} yang berada di dalamnya.
 *
 * <p>Interface ini mendefinisikan kontrak akses data yang dibutuhkan
 * domain tanpa bergantung pada teknologi atau mekanisme penyimpanan
 * tertentu. Implementasi konkret disediakan oleh lapisan infrastruktur.</p>
 */
public interface RepositoriKarya {

    /**
     * Menyimpan aggregate {@code Karya} beserta state yang dimilikinya.
     *
     * @param karya karya yang akan disimpan
     * @return karya yang telah disimpan
     */
    Karya simpan(Karya karya);

    /**
     * Mencari karya berdasarkan ID.
     *
     * @param id ID karya yang dicari
     * @return {@code Optional} berisi karya jika ditemukan,
     *         atau kosong jika tidak ditemukan
     */
    Optional<Karya> cariBerdasarkanId(IdKarya id);

    /**
     * Mencari seluruh karya yang dimiliki oleh seorang penulis.
     *
     * @param idPenulis ID pengguna yang menjadi penulis karya
     * @return daftar karya milik penulis tersebut
     */
    List<Karya> cariBerdasarkanPenulis(IdPengguna idPenulis);

    /**
     * Mencari seluruh karya berdasarkan status penerbitannya.
     *
     * @param status status karya yang akan dicari
     * @return daftar karya dengan status yang diberikan
     */
    List<Karya> cariBerdasarkanStatus(StatusKarya status);

    /**
     * Menghapus karya berdasarkan ID.
     *
     * @param id ID karya yang akan dihapus
     */
    void hapusBerdasarkanId(IdKarya id);
}