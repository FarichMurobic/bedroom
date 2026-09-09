/*
 * Copyright (c) 2026 Farich Murobic
 *
 * This project is licensed under the MIT License.
 * See the LICENSE file in the project root for more information.
 */

package com.bedroom.domain.karya.model;

import com.bedroom.domain.karya.valueobject.IdGenre;
import com.bedroom.domain.karya.valueobject.NamaGenre;

import java.time.Instant;
import java.util.Objects;

/**
 * Merepresentasikan sebuah kategori atau genre karya yang dikelola
 * untuk menjaga konsistensi klasifikasi karya di seluruh platform Bedroom.
 *
 * <p>{@code Genre} merupakan entity yang memiliki identitas unik melalui
 * {@code IdGenre} dan menyimpan nama genre serta waktu pembuatannya.</p>
 */
public final class Genre {

    private final IdGenre id;
    private NamaGenre nama;
    private final Instant dibuatPada;

    private Genre(
            IdGenre id,
            NamaGenre nama,
            Instant dibuatPada
    ) {
        this.id = Objects.requireNonNull(id, "Id genre tidak boleh kosong");
        this.nama = Objects.requireNonNull(nama, "Nama genre tidak boleh kosong");
        this.dibuatPada = Objects.requireNonNull(dibuatPada, "Waktu pembuatan tidak boleh kosong");
    }

    /**
     * Membuat entity {@code Genre} baru dengan ID dan waktu pembuatan
     * yang dibuat secara otomatis.
     *
     * @param nama nama genre
     * @return entity {@code Genre} baru
     * @throws NullPointerException jika {@code nama} bernilai {@code null}
     */
    public static Genre buat(NamaGenre nama) {
        return new Genre(IdGenre.baru(), nama, Instant.now());
    }

    /**
     * Membangun kembali entity {@code Genre} dari data yang telah tersimpan.
     *
     * <p>Factory method ini digunakan ketika entity perlu direkonstruksi
     * dari sumber penyimpanan, seperti basis data, tanpa membuat ID atau
     * waktu baru.</p>
     *
     * @param id ID unik genre
     * @param nama nama genre
     * @param dibuatPada waktu ketika genre dibuat
     * @return entity {@code Genre} yang telah direkonstruksi
     * @throws NullPointerException jika salah satu parameter bernilai {@code null}
     */
    public static Genre rekonstruksi(
            IdGenre id,
            NamaGenre nama,
            Instant dibuatPada
    ) {
        return new Genre(id, nama, dibuatPada);
    }

    /**
     * Mengganti nama genre tanpa mengubah identitas dan waktu pembuatannya.
     *
     * @param namaBaru nama baru untuk genre
     * @throws NullPointerException jika {@code namaBaru} bernilai {@code null}
     */
    public void gantiNama(NamaGenre namaBaru) {
        this.nama = Objects.requireNonNull(namaBaru, "Nama genre tidak boleh kosong");
    }

    public IdGenre id() {
        return id;
    }

    public NamaGenre nama() {
        return nama;
    }

    public Instant dibuatPada() {
        return dibuatPada;
    }
}