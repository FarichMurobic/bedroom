/*
 * Copyright (c) 2026 Farich Murobic
 *
 * This project is licensed under the MIT License.
 * See the LICENSE file in the project root for more information.
 */

package com.bedroom.application.karya.service;

import com.bedroom.application.karya.command.BuatGenreCommand;
import com.bedroom.application.karya.result.GenreResult;
import com.bedroom.application.security.PenggunaTerautentikasi;
import com.bedroom.application.security.PenyediaPenggunaTerautentikasi;
import com.bedroom.domain.identitas.enums.Peran;
import com.bedroom.domain.identitas.exception.BukanAdminException;
import com.bedroom.domain.identitas.model.Pengguna;
import com.bedroom.domain.identitas.repository.RepositoriPengguna;
import com.bedroom.domain.identitas.valueobject.IdPengguna;
import com.bedroom.domain.karya.model.Genre;
import com.bedroom.domain.karya.repository.RepositoriGenre;
import com.bedroom.domain.karya.valueobject.NamaGenre;
import com.bedroom.shared.exception.KonflikDataException;
import com.bedroom.shared.exception.SumberDayaTidakDitemukanException;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

/**
 * Mengorkestrasi use case pengelolaan genre, khusus untuk pengguna
 * berperan ADMIN.
 */
public class GenreApplicationService {

    private final RepositoriGenre repositoriGenre;
    private final RepositoriPengguna repositoriPengguna;
    private final PenyediaPenggunaTerautentikasi penyediaPenggunaTerautentikasi;

    public GenreApplicationService(
            RepositoriGenre repositoriGenre,
            RepositoriPengguna repositoriPengguna,
            PenyediaPenggunaTerautentikasi penyediaPenggunaTerautentikasi
    ) {
        this.repositoriGenre = Objects.requireNonNull(repositoriGenre, "Repositori genre tidak boleh kosong");
        this.repositoriPengguna = Objects.requireNonNull(repositoriPengguna, "Repositori pengguna tidak boleh kosong");
        this.penyediaPenggunaTerautentikasi = Objects.requireNonNull(penyediaPenggunaTerautentikasi, "Penyedia pengguna terautentikasi tidak boleh kosong");
    }

    /**
     * Membuat genre baru. Hanya dapat dilakukan oleh admin.
     *
     * @param command perintah buat genre
     * @return data genre yang telah dibuat
     * @throws BukanAdminException jika pengguna bukan admin
     * @throws KonflikDataException jika nama genre sudah ada
     * @throws SumberDayaTidakDitemukanException jika pengguna tidak ditemukan
     */
    @Transactional
    public GenreResult buatGenre(BuatGenreCommand command) {
        Objects.requireNonNull(command, "Perintah buat genre tidak boleh kosong");

        pastikanAdmin();

        NamaGenre nama = new NamaGenre(command.nama());
        if (repositoriGenre.adaBerdasarkanNama(nama)) {
            throw new KonflikDataException("Genre dengan nama tersebut sudah ada");
        }

        Genre genre = Genre.buat(nama);
        Genre genreTersimpan = repositoriGenre.simpan(genre);

        return new GenreResult(genreTersimpan.id().nilai().toString(), genreTersimpan.nama().nilai());
    }

    private void pastikanAdmin() {
        PenggunaTerautentikasi penggunaTerautentikasi = penyediaPenggunaTerautentikasi.ambilPenggunaTerautentikasi();
        IdPengguna idPengguna = penggunaTerautentikasi.idPengguna();

        Pengguna pengguna = repositoriPengguna.cariBerdasarkanId(idPengguna)
                .orElseThrow(() -> new SumberDayaTidakDitemukanException("Pengguna tidak ditemukan"));

        if (!pengguna.perans().contains(Peran.ADMIN)) {
            throw new BukanAdminException("Tindakan ini hanya dapat dilakukan oleh admin");
        }
    }
}