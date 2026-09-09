/*
 * Copyright (c) 2026 Farich Murobic
 *
 * This project is licensed under the MIT License.
 * See the LICENSE file in the project root for more information.
 */

package com.bedroom.application.karya.service;

import com.bedroom.application.karya.command.GantiJudulKaryaCommand;
import com.bedroom.application.karya.command.GantiSinopsisKaryaCommand;
import com.bedroom.application.karya.command.HapusGenreDariKaryaCommand;
import com.bedroom.application.karya.command.MulaiTulisKaryaCommand;
import com.bedroom.application.karya.command.TambahGenreKeKaryaCommand;
import com.bedroom.application.karya.result.KaryaResult;
import com.bedroom.application.security.PenggunaTerautentikasi;
import com.bedroom.application.security.PenyediaPenggunaTerautentikasi;
import com.bedroom.domain.identitas.valueobject.IdPengguna;
import com.bedroom.domain.karya.model.Karya;
import com.bedroom.domain.karya.repository.RepositoriKarya;
import com.bedroom.domain.karya.valueobject.IdGenre;
import com.bedroom.domain.karya.valueobject.JudulKarya;
import com.bedroom.domain.karya.valueobject.Sinopsis;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

/**
 * Application service untuk mengelola operasi penulisan dan pengeditan karya.
 */
public class PenulisanKaryaApplicationService {

    private final RepositoriKarya repositoriKarya;
    private final PenyediaPenggunaTerautentikasi penyediaPenggunaTerautentikasi;
    private final PengambilKaryaTerotorisasi pengambilKaryaTerotorisasi;

    public PenulisanKaryaApplicationService(
            RepositoriKarya repositoriKarya,
            PenyediaPenggunaTerautentikasi penyediaPenggunaTerautentikasi
    ) {
        this.repositoriKarya = Objects.requireNonNull(repositoriKarya, "Repositori karya tidak boleh kosong");
        this.penyediaPenggunaTerautentikasi = Objects.requireNonNull(penyediaPenggunaTerautentikasi, "Penyedia pengguna terautentikasi tidak boleh kosong");
        this.pengambilKaryaTerotorisasi = new PengambilKaryaTerotorisasi(repositoriKarya, penyediaPenggunaTerautentikasi);
    }

    /**
     * Memulai penulisan karya baru dengan status draft.
     *
     * @param command perintah mulai tulis karya
     * @return data karya yang telah dibuat
     */
    @Transactional
    public KaryaResult mulaiTulis(MulaiTulisKaryaCommand command) {
        Objects.requireNonNull(command, "Perintah mulai tulis karya tidak boleh kosong");

        JudulKarya judul = new JudulKarya(command.judul());
        Sinopsis sinopsis = new Sinopsis(command.sinopsis());
        IdPengguna idPenulis = ambilIdPenggunaTerautentikasi();

        Karya karya = Karya.mulaiTulis(idPenulis, judul, sinopsis);
        Karya karyaTersimpan = repositoriKarya.simpan(karya);

        return KaryaMapper.keResult(karyaTersimpan);
    }

    /**
     * Mengganti judul karya.
     *
     * @param command perintah ganti judul karya
     * @return data karya yang telah diperbarui
     */
    @Transactional
    public KaryaResult gantiJudul(GantiJudulKaryaCommand command) {
        Objects.requireNonNull(command, "Perintah ganti judul karya tidak boleh kosong");

        Karya karya = pengambilKaryaTerotorisasi.ambilMilikSendiri(command.idKarya());
        karya.gantiJudul(new JudulKarya(command.judulBaru()));

        return KaryaMapper.keResult(repositoriKarya.simpan(karya));
    }

    /**
     * Mengganti sinopsis karya.
     *
     * @param command perintah ganti sinopsis karya
     * @return data karya yang telah diperbarui
     */
    @Transactional
    public KaryaResult gantiSinopsis(GantiSinopsisKaryaCommand command) {
        Objects.requireNonNull(command, "Perintah ganti sinopsis karya tidak boleh kosong");

        Karya karya = pengambilKaryaTerotorisasi.ambilMilikSendiri(command.idKarya());
        karya.gantiSinopsis(new Sinopsis(command.sinopsisBaru()));

        return KaryaMapper.keResult(repositoriKarya.simpan(karya));
    }

    /**
     * Menambahkan genre ke karya.
     *
     * @param command perintah tambah genre ke karya
     * @return data karya yang telah diperbarui
     */
    @Transactional
    public KaryaResult tambahGenre(TambahGenreKeKaryaCommand command) {
        Objects.requireNonNull(command, "Perintah tambah genre tidak boleh kosong");

        Karya karya = pengambilKaryaTerotorisasi.ambilMilikSendiri(command.idKarya());
        karya.tambahGenre(IdGenre.dari(command.idGenre()));

        return KaryaMapper.keResult(repositoriKarya.simpan(karya));
    }

    /**
     * Menghapus genre dari karya.
     *
     * @param command perintah hapus genre dari karya
     * @return data karya yang telah diperbarui
     */
    @Transactional
    public KaryaResult hapusGenre(HapusGenreDariKaryaCommand command) {
        Objects.requireNonNull(command, "Perintah hapus genre tidak boleh kosong");

        Karya karya = pengambilKaryaTerotorisasi.ambilMilikSendiri(command.idKarya());
        karya.hapusGenre(IdGenre.dari(command.idGenre()));

        return KaryaMapper.keResult(repositoriKarya.simpan(karya));
    }

    private IdPengguna ambilIdPenggunaTerautentikasi() {
        PenggunaTerautentikasi penggunaTerautentikasi = penyediaPenggunaTerautentikasi.ambilPenggunaTerautentikasi();
        return penggunaTerautentikasi.idPengguna();
    }
}