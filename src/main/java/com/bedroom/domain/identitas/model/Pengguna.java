/*
 * Copyright (c) 2026 Farich Murobic
 * Licensed under the MIT License.
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
 * Merepresentasikan seorang pengguna terdaftar di platform Bedroom.
 * Entity ini mengelola identitas profil (nama pengguna, status, peran),
 * terpisah dari kredensial autentikasi yang dikelola oleh {@code IdentitasAutentikasi}.
 */
public final class Pengguna {

    private final IdPengguna id;
    private NamaPengguna namaPengguna;
    private StatusPengguna status;
    private final Set<Peran> perans;
    private final Instant dibuatPada;
    private Instant diperbaruiPada;

    private Pengguna(
            IdPengguna id,
            NamaPengguna namaPengguna,
            StatusPengguna status,
            Set<Peran> perans,
            Instant dibuatPada,
            Instant diperbaruiPada
    ) {
        this.id = Objects.requireNonNull(
                id, "Id pengguna tidak boleh kosong"
        );
        this.namaPengguna = Objects.requireNonNull(
                namaPengguna, "Nama pengguna tidak boleh kosong"
        );
        this.status = Objects.requireNonNull(
                status, "Status pengguna tidak boleh kosong"
        );
        this.dibuatPada = Objects.requireNonNull(
                dibuatPada, "Waktu pembuatan tidak boleh kosong"
        );
        this.diperbaruiPada = Objects.requireNonNull(
                diperbaruiPada, "Waktu pembaruan tidak boleh kosong"
        );

        Objects.requireNonNull(perans, "Daftar peran tidak boleh kosong");
        if (perans.isEmpty()) {
            throw new IllegalArgumentException(
                    "Pengguna harus memiliki minimal satu peran"
            );
        }
        this.perans = EnumSet.copyOf(perans);
    }

    /**
     * Mendaftarkan pengguna baru yang membutuhkan verifikasi lebih lanjut
     * (mis. registrasi via email atau nomor telepon), sehingga statusnya
     * dimulai dari {@code MENUNGGU_VERIFIKASI}.
     */
    public static Pengguna daftarMenungguVerifikasi(NamaPengguna namaPengguna) {
        Instant sekarang = Instant.now();

        return new Pengguna(
                IdPengguna.baru(),
                namaPengguna,
                StatusPengguna.MENUNGGU_VERIFIKASI,
                EnumSet.of(Peran.PENGGUNA),
                sekarang,
                sekarang
        );
    }

    /**
     * Mendaftarkan pengguna baru yang identitasnya sudah terverifikasi
     * oleh penyedia eksternal (mis. registrasi via Google), sehingga
     * statusnya langsung dimulai dari {@code AKTIF}.
     */
    public static Pengguna daftarTerverifikasi(NamaPengguna namaPengguna) {
        Instant sekarang = Instant.now();

        return new Pengguna(
                IdPengguna.baru(),
                namaPengguna,
                StatusPengguna.AKTIF,
                EnumSet.of(Peran.PENGGUNA),
                sekarang,
                sekarang
        );
    }

    /**
     * Membangun kembali entity dari data yang tersimpan (mis. dari basis data).
     * Hanya digunakan oleh lapisan infrastruktur (mapper), bukan oleh logika bisnis.
     */
    public static Pengguna rekonstruksi(
            IdPengguna id,
            NamaPengguna namaPengguna,
            StatusPengguna status,
            Set<Peran> perans,
            Instant dibuatPada,
            Instant diperbaruiPada
    ) {
        return new Pengguna(
                id,
                namaPengguna,
                status,
                perans,
                dibuatPada,
                diperbaruiPada
        );
    }

    /**
     * Menandai bahwa pengguna telah berhasil memverifikasi identitasnya
     * (mis. setelah memasukkan kode OTP dengan benar).
     *
     * @throws IllegalStateException jika pengguna tidak sedang berstatus
     *         {@code MENUNGGU_VERIFIKASI}.
     */
    public void verifikasi() {
        if (status != StatusPengguna.MENUNGGU_VERIFIKASI) {
            throw new IllegalStateException(
                    "Hanya pengguna berstatus menunggu verifikasi yang dapat diverifikasi"
            );
        }
        ubahStatus(StatusPengguna.AKTIF);
    }

    /**
     * Mengganti nama pengguna.
     *
     * @throws IllegalStateException jika pengguna tidak berstatus {@code AKTIF}.
     */
    public void gantiNamaPengguna(NamaPengguna namaPenggunaBaru) {
        pastikanAktif();
        this.namaPengguna = Objects.requireNonNull(
                namaPenggunaBaru, "Nama pengguna tidak boleh kosong"
        );
        this.diperbaruiPada = Instant.now();
    }

    /**
     * Menonaktifkan akun secara sukarela (mis. permintaan pengguna sendiri).
     * Dapat dipulihkan kembali melalui {@link #aktifkan()}.
     */
    public void nonaktifkan() {
        ubahStatus(StatusPengguna.TIDAK_AKTIF);
    }

    /**
     * Mengaktifkan kembali akun yang sebelumnya {@code TIDAK_AKTIF} atau {@code DITANGGUHKAN}.
     *
     * @throws IllegalStateException jika akun berstatus {@code DIBLOKIR},
     *         karena pemblokiran bersifat permanen.
     */
    public void aktifkan() {
        if (status == StatusPengguna.DIBLOKIR) {
            throw new IllegalStateException(
                    "Akun yang diblokir tidak dapat diaktifkan kembali"
            );
        }
        ubahStatus(StatusPengguna.AKTIF);
    }

    /**
     * Menangguhkan akun untuk sementara, biasanya sebagai tindakan moderasi.
     * Berbeda dengan {@link #blokir()}, status ini masih dapat dipulihkan.
     */
    public void tangguhkan() {
        ubahStatus(StatusPengguna.DITANGGUHKAN);
    }

    /**
     * Memblokir akun secara permanen, biasanya karena pelanggaran berat.
     * Tindakan ini bersifat final dan tidak dapat dipulihkan melalui {@link #aktifkan()}.
     */
    public void blokir() {
        ubahStatus(StatusPengguna.DIBLOKIR);
    }

    /**
     * Menambahkan peran baru kepada pengguna.
     */
    public void tambahPeran(Peran peran) {
        Objects.requireNonNull(
                peran, "Peran tidak boleh kosong"
        );
        this.perans.add(peran);
        this.diperbaruiPada = Instant.now();
    }

    /**
     * Mencabut suatu peran dari pengguna.
     *
     * @throws IllegalStateException jika peran yang dicabut adalah satu-satunya
     *         peran yang dimiliki pengguna, karena pengguna wajib memiliki
     *         minimal satu peran.
     */
    public void hapusPeran(Peran peran) {
        Objects.requireNonNull(
                peran, "Peran tidak boleh kosong"
        );
        if (perans.size() == 1 && perans.contains(peran)) {
            throw new IllegalStateException(
                    "Pengguna harus memiliki minimal satu peran"
            );
        }
        this.perans.remove(peran);
        this.diperbaruiPada = Instant.now();
    }

    /**
     * Memastikan pengguna berstatus {@code AKTIF} sebelum melakukan
     * tindakan yang membutuhkan akun aktif, seperti menerbitkan karya.
     *
     * @throws IllegalStateException jika status pengguna bukan {@code AKTIF}.
     */
    public void pastikanAktif() {
        if (status != StatusPengguna.AKTIF) {
            throw new AkunTidakAktifException(
                    "Tindakan ini hanya dapat dilakukan oleh pengguna berstatus aktif"
            );
        }
    }

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

}
