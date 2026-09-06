package com.bedroom.application.identitas.service;

import com.bedroom.application.identitas.command.VerifikasiEmailCommand;
import com.bedroom.application.identitas.command.VerifikasiOtpCommand;
import com.bedroom.application.identitas.port.PengelolaKodeOtp;
import com.bedroom.application.identitas.port.PengelolaVerifikasiEmail;
import com.bedroom.application.identitas.port.PenerbitTokenAutentikasi;
import com.bedroom.application.identitas.result.HasilAutentikasi;
import com.bedroom.domain.identitas.model.IdentitasAutentikasi;
import com.bedroom.domain.identitas.model.Pengguna;
import com.bedroom.domain.identitas.repository.RepositoriIdentitasAutentikasi;
import com.bedroom.domain.identitas.repository.RepositoriPengguna;
import com.bedroom.domain.identitas.valueobject.Email;
import com.bedroom.domain.identitas.valueobject.NomorTelepon;
import com.bedroom.shared.exception.KredensialTidakValidException;
import com.bedroom.shared.exception.SumberDayaTidakDitemukanException;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

/**
 * Mengorkestrasi use case verifikasi akun pengguna, baik melalui tautan
 * konfirmasi email maupun kode OTP telepon. Verifikasi yang berhasil
 * langsung menyertakan token akses (auto-login).
 */
public class VerifikasiApplicationService {

    private final RepositoriPengguna repositoriPengguna;
    private final RepositoriIdentitasAutentikasi repositoriIdentitasAutentikasi;
    private final PengelolaVerifikasiEmail pengelolaVerifikasiEmail;
    private final PengelolaKodeOtp pengelolaKodeOtp;
    private final PenerbitTokenAutentikasi penerbitTokenAutentikasi;

    public VerifikasiApplicationService(
            RepositoriPengguna repositoriPengguna,
            RepositoriIdentitasAutentikasi repositoriIdentitasAutentikasi,
            PengelolaVerifikasiEmail pengelolaVerifikasiEmail,
            PengelolaKodeOtp pengelolaKodeOtp,
            PenerbitTokenAutentikasi penerbitTokenAutentikasi
    ) {
        this.repositoriPengguna = Objects.requireNonNull(repositoriPengguna, "Repositori pengguna tidak boleh kosong");
        this.repositoriIdentitasAutentikasi = Objects.requireNonNull(repositoriIdentitasAutentikasi, "Repositori identitas autentikasi tidak boleh kosong");
        this.pengelolaVerifikasiEmail = Objects.requireNonNull(pengelolaVerifikasiEmail, "Pengelola verifikasi email tidak boleh kosong");
        this.pengelolaKodeOtp = Objects.requireNonNull(pengelolaKodeOtp, "Pengelola kode OTP tidak boleh kosong");
        this.penerbitTokenAutentikasi = Objects.requireNonNull(penerbitTokenAutentikasi, "Penerbit token autentikasi tidak boleh kosong");
    }

    @Transactional
    public HasilAutentikasi verifikasiEmail(VerifikasiEmailCommand command) {
        Objects.requireNonNull(command, "Perintah verifikasi email tidak boleh kosong");

        Email email = new Email(command.email());

        if (!pengelolaVerifikasiEmail.verifikasi(email.nilai(), command.token())) {
            throw new KredensialTidakValidException("Token verifikasi tidak valid atau sudah kedaluwarsa");
        }

        IdentitasAutentikasi identitasAutentikasi = repositoriIdentitasAutentikasi
                .cariBerdasarkanEmail(email)
                .orElseThrow(() -> new SumberDayaTidakDitemukanException("Identitas autentikasi tidak ditemukan"));

        Pengguna pengguna = repositoriPengguna
                .cariBerdasarkanId(identitasAutentikasi.idPengguna())
                .orElseThrow(() -> new SumberDayaTidakDitemukanException("Pengguna terkait identitas autentikasi tidak ditemukan"));

        pengguna.verifikasi();
        repositoriPengguna.simpan(pengguna);

        String tokenAkses = penerbitTokenAutentikasi.terbitkan(pengguna.id());

        return new HasilAutentikasi(pengguna.id(), tokenAkses, true);
    }

    @Transactional
    public HasilAutentikasi verifikasiOtp(VerifikasiOtpCommand command) {
        Objects.requireNonNull(command, "Perintah verifikasi OTP tidak boleh kosong");

        NomorTelepon nomorTelepon = new NomorTelepon(command.nomorTelepon());

        if (!pengelolaKodeOtp.verifikasi(nomorTelepon.nilai(), command.kodeOtp())) {
            throw new KredensialTidakValidException("Kode OTP tidak valid atau sudah kedaluwarsa");
        }

        IdentitasAutentikasi identitasAutentikasi = repositoriIdentitasAutentikasi
                .cariBerdasarkanNomorTelepon(nomorTelepon)
                .orElseThrow(() -> new SumberDayaTidakDitemukanException("Identitas autentikasi tidak ditemukan"));

        Pengguna pengguna = repositoriPengguna
                .cariBerdasarkanId(identitasAutentikasi.idPengguna())
                .orElseThrow(() -> new SumberDayaTidakDitemukanException("Pengguna terkait identitas autentikasi tidak ditemukan"));

        pengguna.verifikasi();
        repositoriPengguna.simpan(pengguna);

        String tokenAkses = penerbitTokenAutentikasi.terbitkan(pengguna.id());

        return new HasilAutentikasi(pengguna.id(), tokenAkses, true);
    }
}