/*
 * Copyright (c) 2026 Farich Murobic
 *
 * This project is licensed under the MIT License.
 * See the LICENSE file in the project root for more information.
 */

package com.bedroom.domain.identitas.repository;

import com.bedroom.domain.identitas.model.Profil;
import com.bedroom.domain.identitas.valueobject.IdPengguna;

import java.util.Optional;

/**
 * Port untuk operasi penyimpanan dan pengambilan data {@code Profil}.
 *
 * <p>Interface ini mendefinisikan kontrak akses data yang dibutuhkan
 * domain tanpa bergantung pada teknologi atau mekanisme penyimpanan
 * tertentu. Implementasi konkret disediakan oleh lapisan infrastruktur.</p>
 */
public interface RepositoriProfil {

    /**
     * Menyimpan profil pengguna.
     *
     * @param profil profil yang akan disimpan
     * @return profil yang telah disimpan
     */
    Profil simpan(Profil profil);

    /**
     * Mencari profil berdasarkan ID pengguna yang memilikinya.
     *
     * @param idPengguna ID pengguna yang profilnya dicari
     * @return {@code Optional} berisi profil jika ditemukan,
     *         atau kosong jika tidak ditemukan
     */
    Optional<Profil> cariBerdasarkanIdPengguna(IdPengguna idPengguna);
}