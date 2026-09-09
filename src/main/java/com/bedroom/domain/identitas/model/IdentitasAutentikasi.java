/*
 * Copyright (c) 2026 Farich Murobic
 *
 * This project is licensed under the MIT License.
 * See the LICENSE file in the project root for more information.
 */

package com.bedroom.domain.identitas.model;

import com.bedroom.domain.identitas.enums.PenyediaAutentikasi;
import com.bedroom.domain.identitas.valueobject.*;

import java.time.Instant;
import java.util.Objects;

/**
 * Merepresentasikan kredensial autentikasi milik seorang {@code Pengguna}.
 *
 * <p>Satu {@code Pengguna} dapat memiliki lebih dari satu
 * {@code IdentitasAutentikasi}, misalnya identitas berbasis email dan Google.
 * Setiap identitas hanya terikat pada satu {@code PenyediaAutentikasi} dan
 * memiliki kombinasi data autentikasi yang sesuai dengan penyedianya.</p>
 *
 * <p>Entity ini menjaga konsistensi antara penyedia autentikasi dan field
 * autentikasi yang dimilikinya sebagai bagian dari invariant domain.</p>
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
     * Memastikan kombinasi field autentikasi selalu konsisten dengan
     * {@code PenyediaAutentikasi} yang digunakan.
     *
     * <p>Validasi ini memastikan setiap jenis identitas hanya memiliki
     * field yang sesuai dan menolak kombinasi data yang tidak valid
     * menurut aturan domain.</p>
     *
     * @param penyediaAutentikasi penyedia autentikasi yang digunakan
     * @param email email yang terkait dengan identitas, jika ada
     * @param nomorTelepon nomor telepon yang terkait dengan identitas, jika ada
     * @param pengenalEksternal pengenal dari penyedia eksternal, jika ada
     * @param hashKataSandi hash kata sandi, jika ada
     * @throws IllegalArgumentException jika kombinasi field tidak sesuai
     *         dengan penyedia autentikasi
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
     *
     * @param idPengguna identitas pengguna yang memiliki kredensial ini
     * @param email email yang digunakan untuk autentikasi
     * @param hashKataSandi hash kata sandi yang digunakan untuk autentikasi
     * @return identitas autentikasi baru berbasis email
     * @throws NullPointerException jika salah satu parameter wajib bernilai
     *         {@code null}
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
     *
     * <p>Kata sandi tetap wajib dimiliki oleh identitas ini meskipun proses
     * registrasi melibatkan verifikasi OTP di lapisan aplikasi.</p>
     *
     * @param idPengguna identitas pengguna yang memiliki kredensial ini
     * @param nomorTelepon nomor telepon yang digunakan untuk autentikasi
     * @param hashKataSandi hash kata sandi yang digunakan untuk autentikasi
     * @return identitas autentikasi baru berbasis nomor telepon
     * @throws NullPointerException jika salah satu parameter wajib bernilai
     *         {@code null}
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
     *
     * <p>Identitas ini tidak memiliki kata sandi Bedroom karena proses
     * autentikasinya sepenuhnya didelegasikan kepada Google.</p>
     *
     * @param idPengguna identitas pengguna yang memiliki kredensial ini
     * @param email email yang terkait dengan akun Google
     * @param pengenalEksternal pengenal pengguna dari Google
     * @return identitas autentikasi baru berbasis Google
     * @throws NullPointerException jika salah satu parameter wajib bernilai
     *         {@code null}
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
     * Membangun kembali entity dari data yang telah tersimpan.
     *
     * <p>Factory method ini digunakan ketika entity perlu direkonstruksi
     * dari sumber penyimpanan, seperti basis data. Method ini mempertahankan
     * validasi invariant domain yang sama dengan proses pembuatan identitas baru.</p>
     *
     * @param id identitas unik autentikasi
     * @param idPengguna identitas pengguna pemilik kredensial
     * @param penyediaAutentikasi penyedia autentikasi
     * @param email email yang terkait dengan identitas, jika ada
     * @param nomorTelepon nomor telepon yang terkait dengan identitas, jika ada
     * @param pengenalEksternal pengenal dari penyedia eksternal, jika ada
     * @param hashKataSandi hash kata sandi, jika ada
     * @param dibuatPada waktu ketika identitas pertama kali dibuat
     * @param diperbaruiPada waktu terakhir identitas diperbarui
     * @return entity {@code IdentitasAutentikasi} yang telah direkonstruksi
     * @throws NullPointerException jika parameter wajib bernilai {@code null}
     * @throws IllegalArgumentException jika kombinasi field tidak konsisten
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
     * Mengganti hash kata sandi pada identitas autentikasi ini.
     *
     * <p>Perubahan kata sandi hanya diperbolehkan untuk identitas yang
     * menggunakan autentikasi email atau telepon. Identitas berbasis Google
     * tidak menyimpan kata sandi Bedroom.</p>
     *
     * @param hashKataSandiBaru hash kata sandi baru
     * @throws NullPointerException jika {@code hashKataSandiBaru} bernilai
     *         {@code null}
     * @throws IllegalStateException jika identitas berbasis Google
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