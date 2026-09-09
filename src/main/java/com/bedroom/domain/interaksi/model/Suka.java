/*
 * Copyright (c) 2026 Farich Murobic
 *
 * This project is licensed under the MIT License.
 * See the LICENSE file in the project root for more information.
 */

package com.bedroom.domain.interaksi.model;

import com.bedroom.domain.identitas.valueobject.IdPengguna;
import com.bedroom.domain.interaksi.valueobject.IdSuka;
import com.bedroom.domain.karya.valueobject.IdKarya;

import java.time.Instant;
import java.util.Objects;

/**
 * Merepresentasikan relasi ketika seorang {@code Pengguna} menyukai sebuah
 * {@code Karya}.
 *
 * <p>{@code Suka} merupakan entity yang berfungsi sebagai pencatat relasi
 * antara pengguna dan karya. Entity ini tidak memiliki siklus status atau
 * behavior perubahan lainnya setelah dibuat, sehingga relasi hanya dapat
 * dibuat atau dihapus melalui proses pada lapisan aplikasi.</p>
 *
 * <p>Setiap entity memiliki {@code IdSuka} sebagai identitas unik serta
 * mencatat waktu ketika karya disukai.</p>
 */
public final class Suka {

    private final IdSuka id;
    private final IdPengguna idPengguna;
    private final IdKarya idKarya;
    private final Instant disukaiPada;

    private Suka(IdSuka id, IdPengguna idPengguna, IdKarya idKarya, Instant disukaiPada) {
        this.id = Objects.requireNonNull(id, "Id suka tidak boleh kosong");
        this.idPengguna = Objects.requireNonNull(idPengguna, "Id pengguna tidak boleh kosong");
        this.idKarya = Objects.requireNonNull(idKarya, "Id karya tidak boleh kosong");
        this.disukaiPada = Objects.requireNonNull(disukaiPada, "Waktu menyukai tidak boleh kosong");
    }

    /**
     * Membuat entity {@code Suka} baru untuk mencatat bahwa seorang pengguna
     * menyukai sebuah karya.
     *
     * @param idPengguna ID pengguna yang menyukai karya
     * @param idKarya ID karya yang disukai
     * @return entity {@code Suka} baru dengan ID dan waktu dibuat secara otomatis
     * @throws NullPointerException jika {@code idPengguna} atau {@code idKarya}
     *         bernilai {@code null}
     */
    public static Suka buat(IdPengguna idPengguna, IdKarya idKarya) {
        return new Suka(IdSuka.baru(), idPengguna, idKarya, Instant.now());
    }

    /**
     * Membangun kembali entity {@code Suka} dari data yang telah tersimpan.
     *
     * <p>Factory method ini digunakan ketika entity perlu direkonstruksi
     * dari sumber penyimpanan, seperti basis data, tanpa membuat ID atau
     * waktu baru.</p>
     *
     * @param id ID unik suka
     * @param idPengguna ID pengguna yang menyukai karya
     * @param idKarya ID karya yang disukai
     * @param disukaiPada waktu ketika karya disukai
     * @return entity {@code Suka} yang telah direkonstruksi
     * @throws NullPointerException jika salah satu parameter bernilai
     *         {@code null}
     */
    public static Suka rekonstruksi(IdSuka id, IdPengguna idPengguna, IdKarya idKarya, Instant disukaiPada) {
        return new Suka(id, idPengguna, idKarya, disukaiPada);
    }

    public IdSuka id() {
        return id;
    }

    public IdPengguna idPengguna() {
        return idPengguna;
    }

    public IdKarya idKarya() {
        return idKarya;
    }

    public Instant disukaiPada() {
        return disukaiPada;
    }
}