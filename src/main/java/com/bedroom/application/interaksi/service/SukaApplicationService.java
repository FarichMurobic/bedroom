/*
 * Copyright (c) 2026 Farich Murobic
 *
 * This project is licensed under the MIT License.
 * See the LICENSE file in the project root for more information.
 */

package com.bedroom.application.interaksi.service;

import com.bedroom.application.interaksi.command.BatalSukaKaryaCommand;
import com.bedroom.application.interaksi.command.SukaKaryaCommand;
import com.bedroom.application.interaksi.result.StatusSukaResult;
import com.bedroom.application.security.PenggunaTerautentikasi;
import com.bedroom.application.security.PenyediaPenggunaTerautentikasi;
import com.bedroom.domain.identitas.valueobject.IdPengguna;
import com.bedroom.domain.interaksi.exception.TidakBisaMenyukaiKaryaSendiriException;
import com.bedroom.domain.interaksi.model.Suka;
import com.bedroom.domain.interaksi.repository.RepositoriSuka;
import com.bedroom.domain.karya.enums.StatusKarya;
import com.bedroom.domain.karya.model.Karya;
import com.bedroom.domain.karya.repository.RepositoriKarya;
import com.bedroom.domain.karya.valueobject.IdKarya;
import com.bedroom.shared.exception.KonflikDataException;
import com.bedroom.shared.exception.SumberDayaTidakDitemukanException;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

/**
 * Mengorkestrasi use case menyukai dan membatalkan suka pada sebuah karya.
 */
public class SukaApplicationService {

    private final RepositoriSuka repositoriSuka;
    private final RepositoriKarya repositoriKarya;
    private final PenyediaPenggunaTerautentikasi penyediaPenggunaTerautentikasi;

    public SukaApplicationService(
            RepositoriSuka repositoriSuka,
            RepositoriKarya repositoriKarya,
            PenyediaPenggunaTerautentikasi penyediaPenggunaTerautentikasi
    ) {
        this.repositoriSuka = Objects.requireNonNull(repositoriSuka, "Repositori suka tidak boleh kosong");
        this.repositoriKarya = Objects.requireNonNull(repositoriKarya, "Repositori karya tidak boleh kosong");
        this.penyediaPenggunaTerautentikasi = Objects.requireNonNull(penyediaPenggunaTerautentikasi, "Penyedia pengguna terautentikasi tidak boleh kosong");
    }

    /**
     * Memberikan like pada sebuah karya.
     *
     * @param command perintah suka karya
     * @return status like terbaru
     * @throws TidakBisaMenyukaiKaryaSendiriException jika mencoba menyukai karya sendiri
     * @throws KonflikDataException jika sudah menyukai karya tersebut
     * @throws SumberDayaTidakDitemukanException jika karya tidak ditemukan
     */
    @Transactional
    public StatusSukaResult sukai(SukaKaryaCommand command) {
        Objects.requireNonNull(command, "Perintah suka karya tidak boleh kosong");

        IdPengguna idPengguna = ambilIdPenggunaTerautentikasi();
        Karya karya = ambilKaryaTerbitAtauGagal(command.idKarya());

        if (karya.idPenulis().equals(idPengguna)) {
            throw new TidakBisaMenyukaiKaryaSendiriException("Anda tidak dapat menyukai karya Anda sendiri");
        }

        if (repositoriSuka.cariBerdasarkanPenggunaDanKarya(idPengguna, karya.id()).isPresent()) {
            throw new KonflikDataException("Anda sudah menyukai karya ini");
        }

        repositoriSuka.simpan(Suka.buat(idPengguna, karya.id()));

        long jumlahSuka = repositoriSuka.hitungBerdasarkanKarya(karya.id());
        return new StatusSukaResult(true, jumlahSuka);
    }

    /**
     * Membatalkan like pada sebuah karya.
     *
     * @param command perintah batal suka karya
     * @return status like terbaru
     * @throws SumberDayaTidakDitemukanException jika belum menyukai karya tersebut
     */
    @Transactional
    public StatusSukaResult batalSukai(BatalSukaKaryaCommand command) {
        Objects.requireNonNull(command, "Perintah batal suka karya tidak boleh kosong");

        IdPengguna idPengguna = ambilIdPenggunaTerautentikasi();
        IdKarya idKarya = IdKarya.dari(command.idKarya());

        Suka suka = repositoriSuka.cariBerdasarkanPenggunaDanKarya(idPengguna, idKarya)
                .orElseThrow(() -> new SumberDayaTidakDitemukanException("Anda belum menyukai karya ini"));

        repositoriSuka.hapus(suka);

        long jumlahSuka = repositoriSuka.hitungBerdasarkanKarya(idKarya);
        return new StatusSukaResult(false, jumlahSuka);
    }

    private Karya ambilKaryaTerbitAtauGagal(String idKaryaString) {
        IdKarya idKarya = IdKarya.dari(idKaryaString);
        return repositoriKarya.cariBerdasarkanId(idKarya)
                .filter(karya -> karya.status() == StatusKarya.TERBIT)
                .orElseThrow(() -> new SumberDayaTidakDitemukanException("Karya tidak ditemukan"));
    }

    private IdPengguna ambilIdPenggunaTerautentikasi() {
        PenggunaTerautentikasi penggunaTerautentikasi = penyediaPenggunaTerautentikasi.ambilPenggunaTerautentikasi();
        return penggunaTerautentikasi.idPengguna();
    }
}