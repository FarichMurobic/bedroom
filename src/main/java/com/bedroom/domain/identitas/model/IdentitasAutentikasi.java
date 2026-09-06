package com.bedroom.domain.identitas.model;

import com.bedroom.domain.identitas.enums.PenyediaAutentikasi;
import com.bedroom.domain.identitas.valueobject.*;

import java.time.Instant;
import java.util.Objects;

/**
 * Merepresentasikan kredensial autentikasi seorang {@code Pengguna}.
 * Satu {@code Pengguna} dapat memiliki lebih dari satu {@code IdentitasAutentikasi}
 * (misalnya email dan Google sekaligus), namun setiap identitas hanya terikat
 * pada satu {@code PenyediaAutentikasi}.
 */
public final class IdentitasAutentikasi {

    private final IdIdentitasAutentikasi id;
    private final IdPengguna idPengguna;
    private final PenyediaAutentikasi penyediaAutentikasi;
    private final Email email;
    private final NomorTelepon nomorTelepon;
    private final PengenalEksternal pengenalEksternal;
    private HashKataSandi hashKataSandi;
    private final Instant dibuatPada;
    private Instant diperbaruiPada;

    private IdentitasAutentikasi(
            IdIdentitasAutentikasi id,
            IdPengguna idPengguna,
            PenyediaAutentikasi penyediaAutentikasi,
            Email email,
            NomorTelepon nomorTelepon,
            PengenalEksternal pengenalEksternal,
            HashKataSandi hashKataSandi,
            Instant dibuatPada,
            Instant diperbaruiPada
    ) {
        this.id = Objects.requireNonNull(
                id, "Id identitas utentikasi tidak boleh kosong");
        this.idPengguna = Objects.requireNonNull(
                idPengguna, "Id pengguna tidak boleh kosong");
        this.penyediaAutentikasi = Objects.requireNonNull(
                penyediaAutentikasi, "Penyedia autentikasi tidak boleh kosong");
        this.dibuatPada = Objects.requireNonNull(
                dibuatPada, "Waktu pembuatan tidak boleh kosong");
        this.diperbaruiPada = Objects.requireNonNull(
                diperbaruiPada, "Waktu pembaruan tidak boleh kosong");

        validasiKonsistensiField(
                penyediaAutentikasi,
                email,
                nomorTelepon,
                pengenalEksternal,
                hashKataSandi);

        this.email = email;
        this.nomorTelepon = nomorTelepon;
        this.pengenalEksternal = pengenalEksternal;
        this.hashKataSandi = hashKataSandi;
    }

    /**
     * Memastikan kombinasi field yang terisi selalu konsisten dengan
     * penyedia autentikasi yang bersangkutan. Menjadi gerbang terakhir
     * validasi, baik dipanggil dari factory method maupun dari rekonstruksi().
     */
    private static void validasiKonsistensiField(
            PenyediaAutentikasi penyediaAutentikasi,
            Email email,
            NomorTelepon nomorTelepon,
            PengenalEksternal pengenalEksternal,
            HashKataSandi hashKataSandi
    ) {
        switch (penyediaAutentikasi) {
            case EMAIL -> {
                if (email == null) {
                    throw new IllegalArgumentException(
                            "Identitas berbasis email wajib memiliki email");
                }
                if (hashKataSandi == null) {
                    throw new IllegalArgumentException(
                            "Identitas berbasis email wajib memiliki hash kata sandi");
                }
                if (nomorTelepon != null || pengenalEksternal != null) {
                    throw new IllegalArgumentException(
                            "Identitas berbasis email tidak boleh memiliki nomor telepon atau pengenal eksternal");
                }
            }
            case TELEPON -> {
                if (nomorTelepon == null) {
                    throw new IllegalArgumentException(
                            "Identitas berbasis telepon wajib memiliki nomor telepon");
                }
                if (hashKataSandi == null) {
                    throw new IllegalArgumentException(
                            "Identitas berbasis telepon wajib memiliki kata sandi");
                }
                if (email != null || pengenalEksternal != null) {
                    throw new IllegalArgumentException(
                            "Identitas berbasis telepon tidak boleh memiliki email atau pengenal eksternal");
                }
            }
            case GOOGLE -> {
                if (email == null) {
                    throw new IllegalArgumentException(
                            "Identitas berbasis Google wajib memiliki email");
                }
                if (pengenalEksternal == null) {
                    throw new IllegalArgumentException(
                            "Identitas berbasis Google wajib memiliki pengenal eksternal");
                }
                if (nomorTelepon != null || hashKataSandi != null) {
                    throw new IllegalArgumentException(
                            "Identitas berbasis Google tidak boleh memiliki nomor telepon atau kata sandi");
                }
            }
        }
    }

    /**
     * Membuat identitas autentikasi baru berbasis email dan kata sandi.
     */
    public static IdentitasAutentikasi untukEmail(
            IdPengguna idPengguna,
            Email email,
            HashKataSandi hashKataSandi
    ) {
        Objects.requireNonNull(
                email, "Email tidak boleh kosong");
        Objects.requireNonNull(hashKataSandi,
                "Hash kata sandi tidak boleh kosong");
        Instant sekarang = Instant.now();

        return new IdentitasAutentikasi(
                IdIdentitasAutentikasi.baru(),
                idPengguna,
                PenyediaAutentikasi.EMAIL,
                email,
                null,
                null,
                hashKataSandi,
                sekarang,
                sekarang
        );
    }

    /**
     * Membuat identitas autentikasi baru berbasis nomor telepon dan kata sandi.
     * Kata sandi tetap wajib diisi meskipun proses registrasi melibatkan
     * verifikasi OTP di lapisan aplikasi.
     */
    public static IdentitasAutentikasi untukTelepon(
            IdPengguna idPengguna,
            NomorTelepon nomorTelepon,
            HashKataSandi hashKataSandi
    ) {
        Objects.requireNonNull(
                nomorTelepon, "Nomor telepon tidak boleh kosong");
        Objects.requireNonNull(
                hashKataSandi, "Hash kata sandi tidak boleh kosong");
        Instant sekarang = Instant.now();

        return new IdentitasAutentikasi(
                IdIdentitasAutentikasi.baru(),
                idPengguna,
                PenyediaAutentikasi.TELEPON,
                null,
                nomorTelepon,
                null,
                hashKataSandi,
                sekarang,
                sekarang
        );
    }

    /**
     * Membuat identitas autentikasi baru berbasis akun Google.
     * Tidak memerlukan kata sandi karena autentikasi sepenuhnya
     * didelegasikan ke Google.
     */
    public static IdentitasAutentikasi untukGoogle(
            IdPengguna idPengguna,
            Email email,
            PengenalEksternal pengenalEksternal
    ) {
        Objects.requireNonNull(
                email, "Email tidak boleh kosong");
        Objects.requireNonNull(
                pengenalEksternal, "Pengenal eksternal tidak boleh kosong");
        Instant sekarang = Instant.now();

        return new IdentitasAutentikasi(
                IdIdentitasAutentikasi.baru(),
                idPengguna,
                PenyediaAutentikasi.GOOGLE,
                email,
                null,
                pengenalEksternal,
                null,
                sekarang,
                sekarang
        );

    }

    /**
     * Membangun kembali entity dari data yang tersimpan (mis. dari basis data).
     * Hanya digunakan oleh lapisan infrastruktur (mapper), bukan oleh logika bisnis.
     */
    public static IdentitasAutentikasi rekonstruksi(
            IdIdentitasAutentikasi id,
            IdPengguna idPengguna,
            PenyediaAutentikasi penyediaAutentikasi,
            Email email,
            NomorTelepon nomorTelepon,
            PengenalEksternal pengenalEksternal,
            HashKataSandi hashKataSandi,
            Instant dibuatPada,
            Instant diperbaruiPada
    ) {
        return new IdentitasAutentikasi(
                id,
                idPengguna,
                penyediaAutentikasi,
                email,
                nomorTelepon,
                pengenalEksternal,
                hashKataSandi,
                dibuatPada,
                diperbaruiPada
        );
    }

    /**
     * Mengganti kata sandi identitas ini.
     *
     * @throws IllegalStateException jika identitas ini berbasis Google,
     *         karena Google tidak menggunakan kata sandi Bedroom.
     */
    public void gantiKataSandi(HashKataSandi hashKataSandiBaru) {
        Objects.requireNonNull(
                hashKataSandiBaru, "Hash kata sandi tidak boleh kosong");

        if (penyediaAutentikasi == PenyediaAutentikasi.GOOGLE) {
            throw new IllegalStateException(
                    "Autentikasi google tidak menggunakan kata sandi Bedroom");
        }

        this.hashKataSandi = hashKataSandiBaru;
        this.diperbaruiPada = Instant.now();
    }

    public IdIdentitasAutentikasi id() {
        return id;
    }

    public IdPengguna idPengguna() {
        return idPengguna;
    }

    public PenyediaAutentikasi penyediaAutentikasi() {
        return penyediaAutentikasi;
    }

    public Email email() {
        return email;
    }

    public NomorTelepon nomorTelepon() {
        return nomorTelepon;
    }

    public PengenalEksternal pengenalEksternal() {
        return pengenalEksternal;
    }

    public HashKataSandi hashKataSandi() {
        return hashKataSandi;
    }

    public Instant dibuatPada() {
        return dibuatPada;
    }

    public Instant diperbaruiPada() {
        return diperbaruiPada;
    }

}