/*
 * Copyright (c) 2026 Farich Murobic
 *
 * This project is licensed under the MIT License.
 * See the LICENSE file in the project root for more information.
 */

package com.bedroom.domain.identitas.model;

import com.bedroom.domain.identitas.enums.JenisKelamin;
import com.bedroom.domain.identitas.valueobject.IdPengguna;
import com.bedroom.domain.identitas.valueobject.NamaLengkap;
import com.bedroom.domain.identitas.valueobject.TanggalLahir;
import com.bedroom.domain.identitas.valueobject.UrlAvatar;

import java.time.Instant;
import java.util.Objects;

/**
 * Merepresentasikan data biografis seorang {@code Pengguna}.
 *
 * <p>Profil menyimpan informasi tambahan pengguna seperti nama lengkap,
 * tanggal lahir, jenis kelamin, dan URL avatar. Seluruh data biografis
 * bersifat opsional sehingga profil dapat dibuat terlebih dahulu dan
 * dilengkapi secara bertahap oleh pengguna.</p>
 *
 * <p>Setiap {@code Profil} terikat pada satu {@code IdPengguna} dan
 * mencatat waktu terakhir terjadinya perubahan data profil.</p>
 */
public final class Profil {

    private final IdPengguna idPengguna;
    private NamaLengkap namaLengkap;
    private TanggalLahir tanggalLahir;
    private JenisKelamin jenisKelamin;
    private UrlAvatar urlAvatar;
    private Instant diperbaruiPada;

    private Profil(
            IdPengguna idPengguna, NamaLengkap namaLengkap, TanggalLahir tanggalLahir,
            JenisKelamin jenisKelamin, UrlAvatar urlAvatar, Instant diperbaruiPada
    ) {
        this.idPengguna = Objects.requireNonNull(idPengguna, "Id pengguna tidak boleh kosong");
        this.namaLengkap = namaLengkap;
        this.tanggalLahir = tanggalLahir;
        this.jenisKelamin = jenisKelamin;
        this.urlAvatar = urlAvatar;
        this.diperbaruiPada = Objects.requireNonNull(diperbaruiPada, "Waktu pembaruan tidak boleh kosong");
    }

    /**
     * Membuat profil kosong untuk pengguna yang baru mendaftar.
     *
     * <p>Seluruh data biografis dibiarkan kosong dan dapat dilengkapi
     * secara bertahap setelah profil dibuat.</p>
     *
     * @param idPengguna identitas pengguna yang memiliki profil
     * @return profil baru tanpa data biografis
     * @throws NullPointerException jika {@code idPengguna} bernilai {@code null}
     */
    public static Profil buatKosong(IdPengguna idPengguna) {
        return new Profil(idPengguna, null, null, null, null, Instant.now());
    }

    /**
     * Membangun kembali entity {@code Profil} dari data yang telah tersimpan.
     *
     * <p>Factory method ini digunakan ketika profil perlu direkonstruksi
     * dari sumber penyimpanan, seperti basis data, tanpa membuat waktu
     * pembaruan baru.</p>
     *
     * @param idPengguna identitas pengguna yang memiliki profil
     * @param namaLengkap nama lengkap pengguna, jika telah diisi
     * @param tanggalLahir tanggal lahir pengguna, jika telah diisi
     * @param jenisKelamin jenis kelamin pengguna, jika telah diisi
     * @param urlAvatar URL avatar pengguna, jika telah diisi
     * @param diperbaruiPada waktu terakhir profil diperbarui
     * @return entity {@code Profil} yang telah direkonstruksi
     * @throws NullPointerException jika {@code idPengguna} atau
     *         {@code diperbaruiPada} bernilai {@code null}
     */
    public static Profil rekonstruksi(
            IdPengguna idPengguna, NamaLengkap namaLengkap, TanggalLahir tanggalLahir,
            JenisKelamin jenisKelamin, UrlAvatar urlAvatar, Instant diperbaruiPada
    ) {
        return new Profil(idPengguna, namaLengkap, tanggalLahir, jenisKelamin, urlAvatar, diperbaruiPada);
    }

    /**
     * Mengganti nama lengkap yang tersimpan pada profil.
     *
     * @param namaLengkapBaru nama lengkap yang baru, dapat bernilai {@code null}
     */
    public void gantiNamaLengkap(NamaLengkap namaLengkapBaru) {
        this.namaLengkap = namaLengkapBaru;
        sentuh();
    }

    /**
     * Mengganti tanggal lahir yang tersimpan pada profil.
     *
     * @param tanggalLahirBaru tanggal lahir yang baru, dapat bernilai {@code null}
     */
    public void gantiTanggalLahir(TanggalLahir tanggalLahirBaru) {
        this.tanggalLahir = tanggalLahirBaru;
        sentuh();
    }

    /**
     * Mengganti jenis kelamin yang tersimpan pada profil.
     *
     * @param jenisKelaminBaru jenis kelamin yang baru, dapat bernilai {@code null}
     */
    public void gantiJenisKelamin(JenisKelamin jenisKelaminBaru) {
        this.jenisKelamin = jenisKelaminBaru;
        sentuh();
    }

    /**
     * Mengganti URL avatar yang tersimpan pada profil.
     *
     * @param urlAvatarBaru URL avatar yang baru, dapat bernilai {@code null}
     */
    public void gantiUrlAvatar(UrlAvatar urlAvatarBaru) {
        this.urlAvatar = urlAvatarBaru;
        sentuh();
    }

    /**
     * Memperbarui waktu terakhir perubahan pada profil.
     */
    private void sentuh() {
        this.diperbaruiPada = Instant.now();
    }

    public IdPengguna idPengguna() {
        return idPengguna;
    }

    public NamaLengkap namaLengkap() {
        return namaLengkap;
    }

    public TanggalLahir tanggalLahir() {
        return tanggalLahir;
    }

    public JenisKelamin jenisKelamin() {
        return jenisKelamin;
    }

    public UrlAvatar urlAvatar() {
        return urlAvatar;
    }

    public Instant diperbaruiPada() {
        return diperbaruiPada;
    }
}