/*
 * Copyright (c) 2026 Farich Murobic
 * Licensed under the MIT License.
 */
package com.bedroom.application.identitas.service;

import com.bedroom.application.identitas.command.RegistrasiEmailCommand;
import com.bedroom.application.identitas.command.RegistrasiTeleponCommand;
import com.bedroom.application.identitas.port.PengelolaKodeOtp;
import com.bedroom.application.identitas.port.PengelolaVerifikasiEmail;
import com.bedroom.application.identitas.port.PenghasilHashKataSandi;
import com.bedroom.application.identitas.result.HasilRegistrasi;
import com.bedroom.domain.identitas.model.IdentitasAutentikasi;
import com.bedroom.domain.identitas.model.Pengguna;
import com.bedroom.domain.identitas.repository.RepositoriIdentitasAutentikasi;
import com.bedroom.domain.identitas.repository.RepositoriPengguna;
import com.bedroom.domain.identitas.valueobject.Email;
import com.bedroom.domain.identitas.valueobject.HashKataSandi;
import com.bedroom.domain.identitas.valueobject.NamaPengguna;
import com.bedroom.domain.identitas.valueobject.NomorTelepon;
import com.bedroom.shared.exception.KonflikDataException;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

/**
 * Mengorkestrasi use case registrasi pengguna baru melalui email
 * maupun nomor telepon.
 */
public class RegistrasiApplicationService {

    private final RepositoriPengguna repositoriPengguna;
    private final RepositoriIdentitasAutentikasi repositoriIdentitasAutentikasi;
    private final PenghasilHashKataSandi penghasilHashKataSandi;
    private final PengelolaVerifikasiEmail pengelolaVerifikasiEmail;
    private final PengelolaKodeOtp pengelolaKodeOtp;

    public RegistrasiApplicationService(
            RepositoriPengguna repositoriPengguna,
            RepositoriIdentitasAutentikasi repositoriIdentitasAutentikasi,
            PenghasilHashKataSandi penghasilHashKataSandi,
            PengelolaVerifikasiEmail pengelolaVerifikasiEmail,
            PengelolaKodeOtp pengelolaKodeOtp
    ) {
        this.repositoriPengguna = Objects.requireNonNull(repositoriPengguna, "Repositori pengguna tidak boleh kosong");
        this.repositoriIdentitasAutentikasi = Objects.requireNonNull(repositoriIdentitasAutentikasi, "Repositori identitas autentikasi tidak boleh kosong");
        this.penghasilHashKataSandi = Objects.requireNonNull(penghasilHashKataSandi, "Penghasil hash kata sandi tidak boleh kosong");
        this.pengelolaVerifikasiEmail = Objects.requireNonNull(pengelolaVerifikasiEmail, "Pengelola verifikasi email tidak boleh kosong");
        this.pengelolaKodeOtp = Objects.requireNonNull(pengelolaKodeOtp, "Pengelola kode OTP tidak boleh kosong");
    }

    @Transactional
    public HasilRegistrasi registrasiEmail(RegistrasiEmailCommand command) {
        Objects.requireNonNull(command, "Perintah registrasi email tidak boleh kosong");

        NamaPengguna namaPengguna = new NamaPengguna(command.namaPengguna());
        Email email = new Email(command.email());

        if (repositoriPengguna.adaBerdasarkanNamaPengguna(namaPengguna)) {
            throw new KonflikDataException("Nama pengguna sudah digunakan");
        }
        if (repositoriIdentitasAutentikasi.adaBerdasarkanEmail(email)) {
            throw new KonflikDataException("Email sudah digunakan");
        }

        HashKataSandi hashKataSandi = penghasilHashKataSandi.hash(command.kataSandi());

        Pengguna pengguna = Pengguna.daftarMenungguVerifikasi(namaPengguna);
        IdentitasAutentikasi identitasAutentikasi =
                IdentitasAutentikasi.untukEmail(pengguna.id(), email, hashKataSandi);

        repositoriPengguna.simpan(pengguna);
        repositoriIdentitasAutentikasi.simpan(identitasAutentikasi);

        pengelolaVerifikasiEmail.buatDanKirim(email.nilai());

        return new HasilRegistrasi(pengguna.id(), pengguna.namaPengguna().nilai());
    }

    @Transactional
    public HasilRegistrasi registrasiTelepon(RegistrasiTeleponCommand command) {
        Objects.requireNonNull(command, "Perintah registrasi telepon tidak boleh kosong");

        NamaPengguna namaPengguna = new NamaPengguna(command.namaPengguna());
        NomorTelepon nomorTelepon = new NomorTelepon(command.nomorTelepon());

        if (repositoriPengguna.adaBerdasarkanNamaPengguna(namaPengguna)) {
            throw new KonflikDataException("Nama pengguna sudah digunakan");
        }
        if (repositoriIdentitasAutentikasi.adaBerdasarkanNomorTelepon(nomorTelepon)) {
            throw new KonflikDataException("Nomor telepon sudah digunakan");
        }

        HashKataSandi hashKataSandi = penghasilHashKataSandi.hash(command.kataSandi());

        Pengguna pengguna = Pengguna.daftarMenungguVerifikasi(namaPengguna);
        IdentitasAutentikasi identitasAutentikasi =
                IdentitasAutentikasi.untukTelepon(pengguna.id(), nomorTelepon, hashKataSandi);

        repositoriPengguna.simpan(pengguna);
        repositoriIdentitasAutentikasi.simpan(identitasAutentikasi);

        pengelolaKodeOtp.buatDanKirim(nomorTelepon.nilai());

        return new HasilRegistrasi(pengguna.id(), pengguna.namaPengguna().nilai());
    }
}