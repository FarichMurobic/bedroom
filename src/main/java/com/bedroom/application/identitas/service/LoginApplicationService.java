package com.bedroom.application.identitas.service;

import com.bedroom.application.identitas.command.LoginEmailCommand;
import com.bedroom.application.identitas.command.LoginGoogleCommand;
import com.bedroom.application.identitas.command.LoginTeleponCommand;
import com.bedroom.application.identitas.port.PemeriksaKataSandi;
import com.bedroom.application.identitas.port.PemverifikasiTokenGoogle;
import com.bedroom.application.identitas.port.PemverifikasiTokenGoogle.DataPenggunaGoogle;
import com.bedroom.application.identitas.port.PenerbitTokenAutentikasi;
import com.bedroom.application.identitas.result.HasilAutentikasi;
import com.bedroom.domain.identitas.enums.PenyediaAutentikasi;
import com.bedroom.domain.identitas.model.IdentitasAutentikasi;
import com.bedroom.domain.identitas.model.Pengguna;
import com.bedroom.domain.identitas.repository.RepositoriIdentitasAutentikasi;
import com.bedroom.domain.identitas.repository.RepositoriPengguna;
import com.bedroom.domain.identitas.valueobject.Email;
import com.bedroom.domain.identitas.valueobject.IdPengguna;
import com.bedroom.domain.identitas.valueobject.NamaPengguna;
import com.bedroom.domain.identitas.valueobject.NomorTelepon;
import com.bedroom.domain.identitas.valueobject.PengenalEksternal;
import com.bedroom.shared.exception.AksesDitolakException;
import com.bedroom.shared.exception.KonflikDataException;
import com.bedroom.shared.exception.KredensialTidakValidException;
import com.bedroom.shared.exception.SumberDayaTidakDitemukanException;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.util.Locale;
import java.util.Objects;

/**
 * Mengorkestrasi use case login pengguna melalui email, nomor telepon,
 * maupun akun Google.
 */
public class LoginApplicationService {

    private static final String PESAN_KREDENSIAL_SALAH = "Email/nomor telepon atau kata sandi salah";
    private static final int PANJANG_NAMA_PENGGUNA_MINIMAL = 3;
    private static final SecureRandom ACAK = new SecureRandom();

    private final RepositoriPengguna repositoriPengguna;
    private final RepositoriIdentitasAutentikasi repositoriIdentitasAutentikasi;
    private final PemeriksaKataSandi pemeriksaKataSandi;
    private final PenerbitTokenAutentikasi penerbitTokenAutentikasi;
    private final PemverifikasiTokenGoogle pemverifikasiTokenGoogle;

    public LoginApplicationService(
            RepositoriPengguna repositoriPengguna,
            RepositoriIdentitasAutentikasi repositoriIdentitasAutentikasi,
            PemeriksaKataSandi pemeriksaKataSandi,
            PenerbitTokenAutentikasi penerbitTokenAutentikasi,
            PemverifikasiTokenGoogle pemverifikasiTokenGoogle
    ) {
        this.repositoriPengguna = Objects.requireNonNull(repositoriPengguna, "Repositori pengguna tidak boleh kosong");
        this.repositoriIdentitasAutentikasi = Objects.requireNonNull(repositoriIdentitasAutentikasi, "Repositori identitas autentikasi tidak boleh kosong");
        this.pemeriksaKataSandi = Objects.requireNonNull(pemeriksaKataSandi, "Pemeriksa kata sandi tidak boleh kosong");
        this.penerbitTokenAutentikasi = Objects.requireNonNull(penerbitTokenAutentikasi, "Penerbit token autentikasi tidak boleh kosong");
        this.pemverifikasiTokenGoogle = Objects.requireNonNull(pemverifikasiTokenGoogle, "Pemverifikasi token Google tidak boleh kosong");
    }

    @Transactional(readOnly = true)
    public HasilAutentikasi loginEmail(LoginEmailCommand command) {
        Objects.requireNonNull(command, "Perintah login email tidak boleh kosong");

        Email email = new Email(command.email());

        IdentitasAutentikasi identitasAutentikasi = repositoriIdentitasAutentikasi
                .cariBerdasarkanEmail(email)
                .orElseThrow(() -> new KredensialTidakValidException(PESAN_KREDENSIAL_SALAH));

        if (!pemeriksaKataSandi.cocok(command.kataSandi(), identitasAutentikasi.hashKataSandi())) {
            throw new KredensialTidakValidException(PESAN_KREDENSIAL_SALAH);
        }

        Pengguna pengguna = ambilPenggunaAtauGagal(identitasAutentikasi.idPengguna());
        pastikanBisaLogin(pengguna);

        String tokenAkses = penerbitTokenAutentikasi.terbitkan(pengguna.id());
        return new HasilAutentikasi(pengguna.id(), tokenAkses, false);
    }

    @Transactional(readOnly = true)
    public HasilAutentikasi loginTelepon(LoginTeleponCommand command) {
        Objects.requireNonNull(command, "Perintah login telepon tidak boleh kosong");

        NomorTelepon nomorTelepon = new NomorTelepon(command.nomorTelepon());

        IdentitasAutentikasi identitasAutentikasi = repositoriIdentitasAutentikasi
                .cariBerdasarkanNomorTelepon(nomorTelepon)
                .orElseThrow(() -> new KredensialTidakValidException(PESAN_KREDENSIAL_SALAH));

        if (!pemeriksaKataSandi.cocok(command.kataSandi(), identitasAutentikasi.hashKataSandi())) {
            throw new KredensialTidakValidException(PESAN_KREDENSIAL_SALAH);
        }

        Pengguna pengguna = ambilPenggunaAtauGagal(identitasAutentikasi.idPengguna());
        pastikanBisaLogin(pengguna);

        String tokenAkses = penerbitTokenAutentikasi.terbitkan(pengguna.id());
        return new HasilAutentikasi(pengguna.id(), tokenAkses, false);
    }

    @Transactional
    public HasilAutentikasi loginGoogle(LoginGoogleCommand command) {
        Objects.requireNonNull(command, "Perintah login Google tidak boleh kosong");

        DataPenggunaGoogle dataGoogle = pemverifikasiTokenGoogle.verifikasi(command.idTokenGoogle());
        PengenalEksternal pengenalEksternal = new PengenalEksternal(dataGoogle.pengenalEksternal());

        IdentitasAutentikasi identitasAutentikasi = repositoriIdentitasAutentikasi
                .cariBerdasarkanPenyediaDanPengenalEksternal(PenyediaAutentikasi.GOOGLE, pengenalEksternal)
                .orElse(null);

        if (identitasAutentikasi != null) {
            Pengguna pengguna = ambilPenggunaAtauGagal(identitasAutentikasi.idPengguna());
            pastikanBisaLogin(pengguna);
            String tokenAkses = penerbitTokenAutentikasi.terbitkan(pengguna.id());
            return new HasilAutentikasi(pengguna.id(), tokenAkses, false);
        }

        return daftarkanPenggunaBaruDariGoogle(dataGoogle, pengenalEksternal);
    }

    private HasilAutentikasi daftarkanPenggunaBaruDariGoogle(DataPenggunaGoogle dataGoogle, PengenalEksternal pengenalEksternal) {
        Email email = new Email(dataGoogle.email());

        if (repositoriIdentitasAutentikasi.adaBerdasarkanEmail(email)) {
            throw new KonflikDataException(
                    "Email ini sudah terdaftar menggunakan metode lain, silakan login dengan email dan kata sandi"
            );
        }

        NamaPengguna namaPengguna = buatNamaPenggunaUnikDariGoogle(dataGoogle.namaTampilan());

        Pengguna pengguna = Pengguna.daftarTerverifikasi(namaPengguna);
        IdentitasAutentikasi identitasAutentikasi =
                IdentitasAutentikasi.untukGoogle(pengguna.id(), email, pengenalEksternal);

        repositoriPengguna.simpan(pengguna);
        repositoriIdentitasAutentikasi.simpan(identitasAutentikasi);

        String tokenAkses = penerbitTokenAutentikasi.terbitkan(pengguna.id());
        return new HasilAutentikasi(pengguna.id(), tokenAkses, true);
    }

    /**
     * Membersihkan nama tampilan Google menjadi kandidat nama pengguna yang valid,
     * lalu menambahkan akhiran angka acak apabila terjadi tabrakan dengan nama
     * pengguna yang sudah ada.
     */
    private NamaPengguna buatNamaPenggunaUnikDariGoogle(String namaTampilan) {
        String dasar = namaTampilan.toLowerCase(Locale.ROOT).replaceAll("[^a-z0-9]", "");
        if (dasar.length() < PANJANG_NAMA_PENGGUNA_MINIMAL) {
            dasar = dasar + "pengguna";
        }
        if (dasar.length() > 20) {
            dasar = dasar.substring(0, 20);
        }

        NamaPengguna kandidat = new NamaPengguna(dasar);
        while (repositoriPengguna.adaBerdasarkanNamaPengguna(kandidat)) {
            String denganAkhiran = dasar + (1000 + ACAK.nextInt(9000));
            kandidat = new NamaPengguna(denganAkhiran);
        }
        return kandidat;
    }

    private Pengguna ambilPenggunaAtauGagal(IdPengguna idPengguna) {
        return repositoriPengguna.cariBerdasarkanId(idPengguna)
                .orElseThrow(() -> new SumberDayaTidakDitemukanException("Pengguna terkait identitas autentikasi tidak ditemukan"));
    }

    private void pastikanBisaLogin(Pengguna pengguna) {
        switch (pengguna.status()) {
            case AKTIF -> { }
            case MENUNGGU_VERIFIKASI -> throw new AksesDitolakException("Akun belum diverifikasi, silakan periksa email/SMS Anda");
            case TIDAK_AKTIF -> throw new AksesDitolakException("Akun tidak aktif, silakan aktifkan kembali akun Anda");
            case DITANGGUHKAN -> throw new AksesDitolakException("Akun Anda sedang ditangguhkan sementara");
            case DIBLOKIR -> throw new AksesDitolakException("Akun Anda telah diblokir");
        }
    }
}