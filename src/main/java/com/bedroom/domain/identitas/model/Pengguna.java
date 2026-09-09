/*
 * Copyright (c) 2026 Farich Murobic
 *
 * This project is licensed under the MIT License.
 * See the LICENSE file in the project root for more information.
 */

package com.bedroom.domain.identitas.model;

import com.bedroom.domain.identitas.enums.Peran;
import com.bedroom.domain.identitas.enums.StatusPengguna;
import com.bedroom.domain.identitas.exception.AkunTidakAktifException;
import com.bedroom.domain.identitas.valueobject.IdPengguna;
import com.bedroom.domain.identitas.valueobject.NamaPengguna;

import java.time.Instant;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Objects;
import java.util.Set;

/**
 * Merepresentasikan pengguna dalam domain identitas Bedroom.
 *
 * <p>{@code Pengguna} merupakan entity yang memiliki identitas unik melalui
 * {@code IdPengguna} dan mengelola status akun, peran, nama pengguna,
 * serta informasi waktu yang berkaitan dengan aktivitas akun.</p>
 *
 * <p>Entity ini menjaga invariant bahwa setiap pengguna harus memiliki
 * setidaknya satu {@code Peran} dan menyediakan behavior untuk mengelola
 * status, peran, nama pengguna, serta waktu login terakhir.</p>
 */
public final class Pengguna {

    private final IdPengguna id;
    private NamaPengguna namaPengguna;
    private StatusPengguna status;
    private final Set<Peran> perans;
    private final Instant dibuatPada;
    private Instant diperbaruiPada;
    private Instant loginTerakhir;

    private Pengguna(
            IdPengguna id,
            NamaPengguna namaPengguna,
            StatusPengguna status,
            Set<Peran> perans,
            Instant dibuatPada,
            Instant diperbaruiPada,
            Instant loginTerakhir
    ) {
        this.id = Objects.requireNonNull(id, "Id pengguna tidak boleh kosong");
        this.namaPengguna = Objects.requireNonNull(namaPengguna, "Nama pengguna tidak boleh kosong");
        this.status = Objects.requireNonNull(status, "Status pengguna tidak boleh kosong");
        this.dibuatPada = Objects.requireNonNull(dibuatPada, "Waktu pembuatan tidak boleh kosong");
        this.diperbaruiPada = Objects.requireNonNull(diperbaruiPada, "Waktu pembaruan tidak boleh kosong");
        this.loginTerakhir = loginTerakhir;

        Objects.requireNonNull(perans, "Daftar peran tidak boleh kosong");
        if (perans.isEmpty()) {
            throw new IllegalArgumentException("Pengguna harus memiliki minimal satu peran");
        }
        this.perans = EnumSet.copyOf(perans);
    }

    /**
     * Mendaftarkan pengguna baru dengan status menunggu verifikasi.
     *
     * <p>Pengguna yang dibuat melalui factory method ini secara otomatis
     * mendapatkan peran {@code PENGGUNA} dan belum memiliki waktu login
     * terakhir.</p>
     *
     * @param namaPengguna nama pengguna yang digunakan oleh pengguna baru
     * @return pengguna baru dengan status menunggu verifikasi
     * @throws NullPointerException jika {@code namaPengguna} bernilai {@code null}
     */
    public static Pengguna daftarMenungguVerifikasi(NamaPengguna namaPengguna) {
        Instant sekarang = Instant.now();
        return new Pengguna(
                IdPengguna.baru(), namaPengguna, StatusPengguna.MENUNGGU_VERIFIKASI,
                EnumSet.of(Peran.PENGGUNA), sekarang, sekarang, null
        );
    }

    /**
     * Mendaftarkan pengguna baru yang telah terverifikasi.
     *
     * <p>Pengguna yang dibuat melalui factory method ini langsung memiliki
     * status {@code AKTIF} dan mendapatkan peran {@code PENGGUNA}.</p>
     *
     * @param namaPengguna nama pengguna yang digunakan oleh pengguna baru
     * @return pengguna baru dengan status aktif
     * @throws NullPointerException jika {@code namaPengguna} bernilai {@code null}
     */
    public static Pengguna daftarTerverifikasi(NamaPengguna namaPengguna) {
        Instant sekarang = Instant.now();
        return new Pengguna(
                IdPengguna.baru(), namaPengguna, StatusPengguna.AKTIF,
                EnumSet.of(Peran.PENGGUNA), sekarang, sekarang, null
        );
    }

    /**
     * Membangun kembali entity {@code Pengguna} dari data yang telah tersimpan.
     *
     * <p>Factory method ini digunakan ketika entity perlu direkonstruksi
     * dari sumber penyimpanan, seperti basis data, tanpa membuat identitas
     * atau waktu baru.</p>
     *
     * @param id identitas unik pengguna
     * @param namaPengguna nama pengguna
     * @param status status akun pengguna
     * @param perans kumpulan peran yang dimiliki pengguna
     * @param dibuatPada waktu ketika pengguna dibuat
     * @param diperbaruiPada waktu terakhir pengguna diperbarui
     * @param loginTerakhir waktu login pengguna yang terakhir berhasil, jika ada
     * @return entity {@code Pengguna} yang telah direkonstruksi
     * @throws NullPointerException jika parameter wajib bernilai {@code null}
     * @throws IllegalArgumentException jika pengguna tidak memiliki peran
     */
    public static Pengguna rekonstruksi(
            IdPengguna id, NamaPengguna namaPengguna, StatusPengguna status,
            Set<Peran> perans, Instant dibuatPada, Instant diperbaruiPada, Instant loginTerakhir
    ) {
        return new Pengguna(id, namaPengguna, status, perans, dibuatPada, diperbaruiPada, loginTerakhir);
    }

    /**
     * Mencatat waktu login pengguna saat ini.
     *
     * <p>Waktu login diperbarui setiap kali pengguna berhasil melewati
     * proses autentikasi.</p>
     */
    public void catatLogin() {
        this.loginTerakhir = Instant.now();
    }

    /**
     * Memverifikasi pengguna yang masih berstatus menunggu verifikasi.
     *
     * <p>Setelah berhasil diverifikasi, status pengguna berubah menjadi
     * {@code AKTIF}.</p>
     *
     * @throws IllegalStateException jika status pengguna bukan
     *         {@code MENUNGGU_VERIFIKASI}
     */
    public void verifikasi() {
        if (status != StatusPengguna.MENUNGGU_VERIFIKASI) {
            throw new IllegalStateException("Hanya pengguna berstatus menunggu verifikasi yang dapat diverifikasi");
        }
        ubahStatus(StatusPengguna.AKTIF);
    }

    /**
     * Mengganti nama pengguna.
     *
     * <p>Perubahan nama pengguna hanya dapat dilakukan oleh akun yang
     * berstatus {@code AKTIF}.</p>
     *
     * @param namaPenggunaBaru nama pengguna yang baru
     * @throws AkunTidakAktifException jika akun pengguna tidak berstatus aktif
     * @throws NullPointerException jika {@code namaPenggunaBaru} bernilai
     *         {@code null}
     */
    public void gantiNamaPengguna(NamaPengguna namaPenggunaBaru) {
        pastikanAktif();
        this.namaPengguna = Objects.requireNonNull(namaPenggunaBaru, "Nama pengguna tidak boleh kosong");
        this.diperbaruiPada = Instant.now();
    }

    /**
     * Menonaktifkan akun pengguna.
     *
     * <p>Status akun diubah menjadi {@code TIDAK_AKTIF}.</p>
     */
    public void nonaktifkan() {
        ubahStatus(StatusPengguna.TIDAK_AKTIF);
    }

    /**
     * Mengaktifkan kembali akun pengguna.
     *
     * <p>Akun yang telah diblokir tidak dapat diaktifkan kembali melalui
     * behavior ini.</p>
     *
     * @throws IllegalStateException jika akun berstatus {@code DIBLOKIR}
     */
    public void aktifkan() {
        if (status == StatusPengguna.DIBLOKIR) {
            throw new IllegalStateException("Akun yang diblokir tidak dapat diaktifkan kembali");
        }
        ubahStatus(StatusPengguna.AKTIF);
    }

    /**
     * Menangguhkan akun pengguna.
     *
     * <p>Status akun diubah menjadi {@code DITANGGUHKAN}.</p>
     */
    public void tangguhkan() {
        ubahStatus(StatusPengguna.DITANGGUHKAN);
    }

    /**
     * Memblokir akun pengguna.
     *
     * <p>Status akun diubah menjadi {@code DIBLOKIR}.</p>
     */
    public void blokir() {
        ubahStatus(StatusPengguna.DIBLOKIR);
    }

    /**
     * Menambahkan peran baru kepada pengguna.
     *
     * @param peran peran yang akan ditambahkan
     * @throws NullPointerException jika {@code peran} bernilai {@code null}
     */
    public void tambahPeran(Peran peran) {
        Objects.requireNonNull(peran, "Peran tidak boleh kosong");
        this.perans.add(peran);
        this.diperbaruiPada = Instant.now();
    }

    /**
     * Menghapus peran dari pengguna.
     *
     * <p>Pengguna tidak diperbolehkan kehilangan seluruh perannya.
     * Setidaknya satu peran harus tetap dimiliki.</p>
     *
     * @param peran peran yang akan dihapus
     * @throws NullPointerException jika {@code peran} bernilai {@code null}
     * @throws IllegalStateException jika peran yang akan dihapus merupakan
     *         satu-satunya peran yang dimiliki pengguna
     */
    public void hapusPeran(Peran peran) {
        Objects.requireNonNull(peran, "Peran tidak boleh kosong");
        if (perans.size() == 1 && perans.contains(peran)) {
            throw new IllegalStateException("Pengguna harus memiliki minimal satu peran");
        }
        this.perans.remove(peran);
        this.diperbaruiPada = Instant.now();
    }

    /**
     * Memastikan pengguna sedang berada dalam status aktif.
     *
     * <p>Method ini digunakan sebagai pemeriksaan terhadap behavior yang
     * hanya boleh dilakukan oleh pengguna dengan akun aktif.</p>
     *
     * @throws AkunTidakAktifException jika status pengguna bukan
     *         {@code AKTIF}
     */
    public void pastikanAktif() {
        if (status != StatusPengguna.AKTIF) {
            throw new AkunTidakAktifException("Tindakan ini hanya dapat dilakukan oleh pengguna berstatus aktif");
        }
    }

    /**
     * Mengubah status pengguna dan memperbarui waktu perubahan jika
     * status yang baru berbeda dari status saat ini.
     *
     * @param statusBaru status baru yang akan ditetapkan
     */
    private void ubahStatus(StatusPengguna statusBaru) {
        if (this.status == statusBaru) {
            return;
        }
        this.status = statusBaru;
        this.diperbaruiPada = Instant.now();
    }

    public IdPengguna id() {
        return id;
    }

    public NamaPengguna namaPengguna() {
        return namaPengguna;
    }

    public StatusPengguna status() {
        return status;
    }

    public Set<Peran> perans() {
        return Collections.unmodifiableSet(perans);
    }

    public Instant dibuatPada() {
        return dibuatPada;
    }

    public Instant diperbaruiPada() {
        return diperbaruiPada;
    }

    public Instant loginTerakhir() {
        return loginTerakhir;
    }
}