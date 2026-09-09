/*
 * Copyright (c) 2026 Farich Murobic
 *
 * This project is licensed under the MIT License.
 * See the LICENSE file in the project root for more information.
 */

package com.bedroom.application.karya.service;

import com.bedroom.application.karya.command.ArsipkanKaryaCommand;
import com.bedroom.application.karya.command.PulihkanDariArsipCommand;
import com.bedroom.application.karya.command.TarikKeDraftCommand;
import com.bedroom.application.karya.command.TerbitkanKaryaCommand;
import com.bedroom.application.karya.result.KaryaResult;
import com.bedroom.application.security.PenyediaPenggunaTerautentikasi;
import com.bedroom.domain.karya.model.Karya;
import com.bedroom.domain.karya.repository.RepositoriKarya;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

/**
 * Mengorkestrasi use case perubahan status penerbitan sebuah karya.
 */
public class PenerbitanKaryaApplicationService {

    private final RepositoriKarya repositoriKarya;
    private final PengambilKaryaTerotorisasi pengambilKaryaTerotorisasi;

    public PenerbitanKaryaApplicationService(
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
     * Menerbitkan karya.
     *
     * @param command perintah terbitkan karya
     * @return data karya yang telah diterbitkan
     */
    @Transactional
    public KaryaResult terbitkan(TerbitkanKaryaCommand command) {
        Objects.requireNonNull(command, "Perintah terbitkan karya tidak boleh kosong");

        Karya karya = pengambilKaryaTerotorisasi.ambilMilikSendiri(command.idKarya());
        karya.terbitkan();

        return KaryaMapper.keResult(repositoriKarya.simpan(karya));
    }

    /**
     * Menarik karya kembali ke status draft.
     *
     * @param command perintah tarik ke draft
     * @return data karya yang telah ditarik ke draft
     */
    @Transactional
    public KaryaResult tarikKeDraft(TarikKeDraftCommand command) {
        Objects.requireNonNull(command, "Perintah tarik ke draft tidak boleh kosong");

        Karya karya = pengambilKaryaTerotorisasi.ambilMilikSendiri(command.idKarya());
        karya.tarikKeDraft();

        return KaryaMapper.keResult(repositoriKarya.simpan(karya));
    }

    /**
     * Mengarsipkan karya.
     *
     * @param command perintah arsipkan karya
     * @return data karya yang telah diarsipkan
     */
    @Transactional
    public KaryaResult arsipkan(ArsipkanKaryaCommand command) {
        Objects.requireNonNull(command, "Perintah arsipkan karya tidak boleh kosong");

        Karya karya = pengambilKaryaTerotorisasi.ambilMilikSendiri(command.idKarya());
        karya.arsipkan();

        return KaryaMapper.keResult(repositoriKarya.simpan(karya));
    }

    /**
     * Memulihkan karya dari arsip.
     *
     * @param command perintah pulihkan dari arsip
     * @return data karya yang telah dipulihkan
     */
    @Transactional
    public KaryaResult pulihkanDariArsip(PulihkanDariArsipCommand command) {
        Objects.requireNonNull(command, "Perintah pulihkan dari arsip tidak boleh kosong");

        Karya karya = pengambilKaryaTerotorisasi.ambilMilikSendiri(command.idKarya());
        karya.pulihkanDariArsip();

        return KaryaMapper.keResult(repositoriKarya.simpan(karya));
    }
}