/*
 * Copyright (c) 2026 Farich Murobic
 *
 * This project is licensed under the MIT License.
 * See the LICENSE file in the project root for more information.
 */

package com.bedroom.application.karya.service;

import com.bedroom.application.security.PenggunaTerautentikasi;
import com.bedroom.application.security.PenyediaPenggunaTerautentikasi;
import com.bedroom.domain.identitas.valueobject.IdPengguna;
import com.bedroom.domain.karya.model.Karya;
import com.bedroom.domain.karya.repository.RepositoriKarya;
import com.bedroom.domain.karya.valueobject.IdKarya;
import com.bedroom.shared.exception.SumberDayaTidakDitemukanException;

import java.util.Objects;

/**
 * Helper yang digunakan bersama oleh application service domain karya
 * untuk mengambil sebuah {@code Karya} sekaligus memastikan bahwa
 * pengguna yang sedang terautentikasi adalah pemiliknya.
 */
class PengambilKaryaTerotorisasi {

    private final RepositoriKarya repositoriKarya;
    private final PenyediaPenggunaTerautentikasi penyediaPenggunaTerautentikasi;

    PengambilKaryaTerotorisasi(
            RepositoriKarya repositoriKarya,
            PenyediaPenggunaTerautentikasi penyediaPenggunaTerautentikasi
    ) {
        this.repositoriKarya = Objects.requireNonNull(repositoriKarya, "Repositori karya tidak boleh kosong");
        this.penyediaPenggunaTerautentikasi = Objects.requireNonNull(penyediaPenggunaTerautentikasi, "Penyedia pengguna terautentikasi tidak boleh kosong");
    }

    Karya ambilMilikSendiri(String idKaryaString) {
        IdKarya idKarya = IdKarya.dari(idKaryaString);
        IdPengguna idPengguna = ambilIdPenggunaTerautentikasi();

        Karya karya = repositoriKarya.cariBerdasarkanId(idKarya)
                .orElseThrow(() -> new SumberDayaTidakDitemukanException("Karya tidak ditemukan"));

        karya.pastikanPemilik(idPengguna);
        return karya;
    }

    private IdPengguna ambilIdPenggunaTerautentikasi() {
        PenggunaTerautentikasi penggunaTerautentikasi = penyediaPenggunaTerautentikasi.ambilPenggunaTerautentikasi();
        return penggunaTerautentikasi.idPengguna();
    }
}