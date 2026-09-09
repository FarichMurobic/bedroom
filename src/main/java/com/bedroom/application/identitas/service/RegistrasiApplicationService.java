/*
 * Copyright (c) 2026 Farich Murobic
 *
 * This project is licensed under the MIT License.
 * See the LICENSE file in the project root for more information.
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
import com.bedroom.domain.identitas.model.Profil;
import com.bedroom.domain.identitas.repository.RepositoriIdentitasAutentikasi;
import com.bedroom.domain.identitas.repository.RepositoriPengguna;
import com.bedroom.domain.identitas.repository.RepositoriProfil;
import com.bedroom.domain.identitas.valueobject.Email;
import com.bedroom.domain.identitas.valueobject.HashKataSandi;
import com.bedroom.domain.identitas.valueobject.NamaPengguna;
import com.bedroom.domain.identitas.valueobject.NomorTelepon;
import com.bedroom.shared.exception.KonflikDataException;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

/**
 * Application service untuk menangani proses registrasi pengguna baru.
 */
public class RegistrasiApplicationService {

    private final RepositoriPengguna repositoriPengguna;
    private final RepositoriIdentitasAutentikasi repositoriIdentitasAutentikasi;
    private final RepositoriProfil repositoriProfil;
    private final PenghasilHashKataSandi penghasilHashKataSandi;
    private final PengelolaVerifikasiEmail pengelolaVerifikasiEmail;
    private final PengelolaKodeOtp pengelolaKodeOtp;

    public RegistrasiApplicationService(
            RepositoriPengguna repositoriPengguna,
            RepositoriIdentitasAutentikasi repositoriIdentitasAutentikasi,
            RepositoriProfil repositoriProfil,
            PenghasilHashKataSandi penghasilHashKataSandi,
            PengelolaVerifikasiEmail pengelolaVerifikasiEmail,
            PengelolaKodeOtp pengelolaKodeOtp
    ) {
        this.repositoriPengguna = Objects.requireNonNull(repositoriPengguna, "Repositori pengguna tidak boleh kosong");
        this.repositoriIdentitasAutentikasi = Objects.requireNonNull(repositoriIdentitasAutentikasi, "Repositori identitas autentikasi tidak boleh kosong");
        this.repositoriProfil = Objects.requireNonNull(repositoriProfil, "Repositori profil tidak boleh kosong");
        this.penghasilHashKataSandi = Objects.requireNonNull(penghasilHashKataSandi, "Penghasil hash kata sandi tidak boleh kosong");
        this.pengelolaVerifikasiEmail = Objects.requireNonNull(pengelolaVerifikasiEmail, "Pengelola verifikasi email tidak boleh kosong");
        this.pengelolaKodeOtp = Objects.requireNonNull(pengelolaKodeOtp, "Pengelola kode OTP tidak boleh kosong");
    }

    /**
     * Registrasi pengguna baru menggunakan email dan kata sandi.
     *
     * @param command perintah registrasi email
     * @return hasil registrasi berisi id dan nama pengguna
     * @throws KonflikDataException jika nama pengguna atau email sudah digunakan
     */
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
        Profil profil = Profil.buatKosong(pengguna.id());

        repositoriPengguna.simpan(pengguna);
        repositoriIdentitasAutentikasi.simpan(identitasAutentikasi);
        repositoriProfil.simpan(profil);

        pengelolaVerifikasiEmail.buatDanKirim(email.nilai());

        return new HasilRegistrasi(pengguna.id(), pengguna.namaPengguna().nilai());
    }

    /**
     * Registrasi pengguna baru menggunakan nomor telepon dan kata sandi.
     *
     * @param command perintah registrasi telepon
     * @return hasil registrasi berisi id dan nama pengguna
     * @throws KonflikDataException jika nama pengguna atau nomor telepon sudah digunakan
     */
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
        Profil profil = Profil.buatKosong(pengguna.id());

        repositoriPengguna.simpan(pengguna);
        repositoriIdentitasAutentikasi.simpan(identitasAutentikasi);
        repositoriProfil.simpan(profil);

        pengelolaKodeOtp.buatDanKirim(nomorTelepon.nilai());

        return new HasilRegistrasi(pengguna.id(), pengguna.namaPengguna().nilai());
    }
}