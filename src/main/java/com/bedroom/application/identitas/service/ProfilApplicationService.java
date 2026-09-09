/*
 * Copyright (c) 2026 Farich Murobic
 *
 * This project is licensed under the MIT License.
 * See the LICENSE file in the project root for more information.
 */

package com.bedroom.application.identitas.service;

import com.bedroom.application.identitas.command.LengkapiProfilCommand;
import com.bedroom.application.identitas.result.ProfilResult;
import com.bedroom.application.security.PenggunaTerautentikasi;
import com.bedroom.application.security.PenyediaPenggunaTerautentikasi;
import com.bedroom.domain.identitas.enums.JenisKelamin;
import com.bedroom.domain.identitas.model.Profil;
import com.bedroom.domain.identitas.repository.RepositoriProfil;
import com.bedroom.domain.identitas.valueobject.IdPengguna;
import com.bedroom.domain.identitas.valueobject.NamaLengkap;
import com.bedroom.domain.identitas.valueobject.TanggalLahir;
import com.bedroom.domain.identitas.valueobject.UrlAvatar;
import com.bedroom.shared.exception.SumberDayaTidakDitemukanException;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

/**
 * Mengorkestrasi use case pengambilan dan pelengkapan data profil
 * pengguna yang sedang login.
 */
public class ProfilApplicationService {

    private final RepositoriProfil repositoriProfil;
    private final PenyediaPenggunaTerautentikasi penyediaPenggunaTerautentikasi;

    public ProfilApplicationService(
            RepositoriProfil repositoriProfil,
            PenyediaPenggunaTerautentikasi penyediaPenggunaTerautentikasi
    ) {
        this.repositoriProfil = Objects.requireNonNull(repositoriProfil, "Repositori profil tidak boleh kosong");
        this.penyediaPenggunaTerautentikasi = Objects.requireNonNull(penyediaPenggunaTerautentikasi, "Penyedia pengguna terautentikasi tidak boleh kosong");
    }

    /**
     * Melihat profil pengguna yang sedang terautentikasi.
     *
     * @return data profil pengguna
     * @throws SumberDayaTidakDitemukanException jika profil tidak ditemukan
     */
    @Transactional(readOnly = true)
    public ProfilResult lihatProfilSaya() {
        IdPengguna idPengguna = ambilIdPenggunaTerautentikasi();
        Profil profil = ambilProfilAtauGagal(idPengguna);
        return keResult(profil);
    }

    /**
     * Melengkapi atau memperbarui data profil pengguna yang sedang terautentikasi.
     *
     * @param command perintah lengkapi profil
     * @return data profil yang telah diperbarui
     * @throws SumberDayaTidakDitemukanException jika profil tidak ditemukan
     */
    @Transactional
    public ProfilResult lengkapiProfil(LengkapiProfilCommand command) {
        Objects.requireNonNull(command, "Perintah lengkapi profil tidak boleh kosong");

        IdPengguna idPengguna = ambilIdPenggunaTerautentikasi();
        Profil profil = ambilProfilAtauGagal(idPengguna);

        if (command.namaLengkap() != null) {
            profil.gantiNamaLengkap(new NamaLengkap(command.namaLengkap()));
        }
        if (command.tanggalLahir() != null) {
            profil.gantiTanggalLahir(new TanggalLahir(command.tanggalLahir()));
        }
        if (command.jenisKelamin() != null) {
            profil.gantiJenisKelamin(JenisKelamin.valueOf(command.jenisKelamin()));
        }
        if (command.urlAvatar() != null) {
            profil.gantiUrlAvatar(new UrlAvatar(command.urlAvatar()));
        }

        Profil profilTersimpan = repositoriProfil.simpan(profil);
        return keResult(profilTersimpan);
    }

    private Profil ambilProfilAtauGagal(IdPengguna idPengguna) {
        return repositoriProfil.cariBerdasarkanIdPengguna(idPengguna)
                .orElseThrow(() -> new SumberDayaTidakDitemukanException("Profil tidak ditemukan"));
    }

    private IdPengguna ambilIdPenggunaTerautentikasi() {
        PenggunaTerautentikasi penggunaTerautentikasi = penyediaPenggunaTerautentikasi.ambilPenggunaTerautentikasi();
        return penggunaTerautentikasi.idPengguna();
    }

    private ProfilResult keResult(Profil profil) {
        return new ProfilResult(
                profil.idPengguna().nilai().toString(),
                profil.namaLengkap() != null ? profil.namaLengkap().nilai() : null,
                profil.tanggalLahir() != null ? profil.tanggalLahir().nilai() : null,
                profil.jenisKelamin() != null ? profil.jenisKelamin().name() : null,
                profil.urlAvatar() != null ? profil.urlAvatar().nilai() : null,
                profil.diperbaruiPada()
        );
    }
}