/*
 * Copyright (c) 2026 Farich Murobic
 * Licensed under the MIT License.
 */
package com.bedroom.application.identitas.service;

import com.bedroom.application.identitas.command.GantiNamaPenggunaCommand;
import com.bedroom.application.security.PenggunaTerautentikasi;
import com.bedroom.application.security.PenyediaPenggunaTerautentikasi;
import com.bedroom.domain.identitas.model.Pengguna;
import com.bedroom.domain.identitas.repository.RepositoriPengguna;
import com.bedroom.domain.identitas.valueobject.IdPengguna;
import com.bedroom.domain.identitas.valueobject.NamaPengguna;
import com.bedroom.shared.exception.KonflikDataException;
import com.bedroom.shared.exception.SumberDayaTidakDitemukanException;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

/**
 * Mengorkestrasi use case penggantian nama pengguna oleh pengguna
 * yang sedang login.
 */
public class GantiNamaPenggunaApplicationService {

    private final RepositoriPengguna repositoriPengguna;
    private final PenyediaPenggunaTerautentikasi penyediaPenggunaTerautentikasi;

    public GantiNamaPenggunaApplicationService(
            RepositoriPengguna repositoriPengguna,
            PenyediaPenggunaTerautentikasi penyediaPenggunaTerautentikasi
    ) {
        this.repositoriPengguna = Objects.requireNonNull(repositoriPengguna, "Repositori pengguna tidak boleh kosong");
        this.penyediaPenggunaTerautentikasi = Objects.requireNonNull(penyediaPenggunaTerautentikasi, "Penyedia pengguna terautentikasi tidak boleh kosong");
    }

    @Transactional
    public Pengguna execute(GantiNamaPenggunaCommand command) {
        Objects.requireNonNull(command, "Perintah ganti nama pengguna tidak boleh kosong");

        NamaPengguna namaPenggunaBaru = new NamaPengguna(command.namaPenggunaBaru());

        PenggunaTerautentikasi penggunaTerautentikasi = penyediaPenggunaTerautentikasi.ambilPenggunaTerautentikasi();
        IdPengguna idPengguna = penggunaTerautentikasi.idPengguna();

        Pengguna pengguna = repositoriPengguna.cariBerdasarkanId(idPengguna)
                .orElseThrow(() -> new SumberDayaTidakDitemukanException("Pengguna tidak ditemukan"));

        boolean namaBerubah = !pengguna.namaPengguna().equals(namaPenggunaBaru);
        if (namaBerubah && repositoriPengguna.adaBerdasarkanNamaPengguna(namaPenggunaBaru)) {
            throw new KonflikDataException("Nama pengguna sudah digunakan");
        }

        pengguna.gantiNamaPengguna(namaPenggunaBaru);
        return repositoriPengguna.simpan(pengguna);
    }
}