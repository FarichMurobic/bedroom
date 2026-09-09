/*
 * Copyright (c) 2026 Farich Murobic
 *
 * This project is licensed under the MIT License.
 * See the LICENSE file in the project root for more information.
 */

package com.bedroom.application.karya.service;

import com.bedroom.application.karya.command.HapusBabCommand;
import com.bedroom.application.karya.command.TambahBabCommand;
import com.bedroom.application.karya.command.UbahIsiBabCommand;
import com.bedroom.application.karya.command.UbahJudulBabCommand;
import com.bedroom.application.karya.result.KaryaResult;
import com.bedroom.application.security.PenyediaPenggunaTerautentikasi;
import com.bedroom.domain.karya.model.Karya;
import com.bedroom.domain.karya.repository.RepositoriKarya;
import com.bedroom.domain.karya.valueobject.IdBab;
import com.bedroom.domain.karya.valueobject.IsiBab;
import com.bedroom.domain.karya.valueobject.JudulBab;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

/**
 * Application service untuk mengelola operasi CRUD bab pada karya.
 */
public class PenulisanBabApplicationService {

    private final PengambilKaryaTerotorisasi pengambilKaryaTerotorisasi;
    private final RepositoriKarya repositoriKarya;

    public PenulisanBabApplicationService(
            RepositoriKarya repositoriKarya,
            PenyediaPenggunaTerautentikasi penyediaPenggunaTerautentikasi
    ) {
        this.repositoriKarya = Objects.requireNonNull(repositoriKarya, "Repositori karya tidak boleh kosong");
        this.pengambilKaryaTerotorisasi = new PengambilKaryaTerotorisasi(
                repositoriKarya,
                Objects.requireNonNull(penyediaPenggunaTerautentikasi, "Penyedia pengguna terautentikasi tidak boleh kosong")
        );
    }

    /**
     * Menambahkan bab baru ke dalam karya.
     *
     * @param command perintah tambah bab
     * @return data karya yang telah diperbarui
     */
    @Transactional
    public KaryaResult tambahBab(TambahBabCommand command) {
        Objects.requireNonNull(command, "Perintah tambah bab tidak boleh kosong");

        Karya karya = pengambilKaryaTerotorisasi.ambilMilikSendiri(command.idKarya());
        karya.tambahBab(new JudulBab(command.judulBab()), new IsiBab(command.isiBab()));

        return KaryaMapper.keResult(repositoriKarya.simpan(karya));
    }

    /**
     * Mengubah judul bab dalam karya.
     *
     * @param command perintah ubah judul bab
     * @return data karya yang telah diperbarui
     */
    @Transactional
    public KaryaResult ubahJudulBab(UbahJudulBabCommand command) {
        Objects.requireNonNull(command, "Perintah ubah judul bab tidak boleh kosong");

        Karya karya = pengambilKaryaTerotorisasi.ambilMilikSendiri(command.idKarya());
        karya.ubahJudulBab(IdBab.dari(command.idBab()), new JudulBab(command.judulBaru()));

        return KaryaMapper.keResult(repositoriKarya.simpan(karya));
    }

    /**
     * Mengubah isi bab dalam karya.
     *
     * @param command perintah ubah isi bab
     * @return data karya yang telah diperbarui
     */
    @Transactional
    public KaryaResult ubahIsiBab(UbahIsiBabCommand command) {
        Objects.requireNonNull(command, "Perintah ubah isi bab tidak boleh kosong");

        Karya karya = pengambilKaryaTerotorisasi.ambilMilikSendiri(command.idKarya());
        karya.ubahIsiBab(IdBab.dari(command.idBab()), new IsiBab(command.isiBaru()));

        return KaryaMapper.keResult(repositoriKarya.simpan(karya));
    }

    /**
     * Menghapus bab dari karya.
     *
     * @param command perintah hapus bab
     * @return data karya yang telah diperbarui
     */
    @Transactional
    public KaryaResult hapusBab(HapusBabCommand command) {
        Objects.requireNonNull(command, "Perintah hapus bab tidak boleh kosong");

        Karya karya = pengambilKaryaTerotorisasi.ambilMilikSendiri(command.idKarya());
        karya.hapusBab(IdBab.dari(command.idBab()));

        return KaryaMapper.keResult(repositoriKarya.simpan(karya));
    }
}