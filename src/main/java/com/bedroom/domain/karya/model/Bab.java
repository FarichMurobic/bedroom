/*
 * Copyright (c) 2026 Farich Murobic
 *
 * This project is licensed under the MIT License.
 * See the LICENSE file in the project root for more information.
 */

package com.bedroom.domain.karya.model;

import com.bedroom.domain.karya.valueobject.IdBab;
import com.bedroom.domain.karya.valueobject.IsiBab;
import com.bedroom.domain.karya.valueobject.JudulBab;
import com.bedroom.domain.karya.valueobject.UrutanBab;

import java.time.Instant;
import java.util.Objects;

/**
 * Merepresentasikan satu bagian atau bab di dalam sebuah {@code Karya}.
 *
 * <p>{@code Bab} merupakan child entity dari aggregate {@code Karya},
 * sehingga pembuatan dan perubahan bab dikelola melalui konteks
 * {@code Karya} yang menaunginya.</p>
 *
 * <p>Entity ini memiliki identitas unik melalui {@code IdBab} serta
 * menyimpan judul, isi, urutan, dan informasi waktu pembuatan serta
 * pembaruan terakhir.</p>
 */
public final class Bab {

    private final IdBab id;
    private JudulBab judul;
    private IsiBab isi;
    private UrutanBab urutan;
    private final Instant dibuatPada;
    private Instant diperbaruiPada;

    Bab(IdBab id, JudulBab judul, IsiBab isi, UrutanBab urutan, Instant dibuatPada, Instant diperbaruiPada) {
        this.id = Objects.requireNonNull(id, "Id bab tidak boleh kosong");
        this.judul = Objects.requireNonNull(judul, "Judul bab tidak boleh kosong");
        this.isi = Objects.requireNonNull(isi, "Isi bab tidak boleh kosong");
        this.urutan = Objects.requireNonNull(urutan, "Urutan bab tidak boleh kosong");
        this.dibuatPada = Objects.requireNonNull(dibuatPada, "Waktu pembuatan tidak boleh kosong");
        this.diperbaruiPada = Objects.requireNonNull(diperbaruiPada, "Waktu pembaruan tidak boleh kosong");
    }

    /**
     * Membuat bab baru untuk sebuah {@code Karya}.
     *
     * <p>Method ini digunakan oleh aggregate {@code Karya} ketika
     * menambahkan bab baru dan secara otomatis membuat ID serta
     * mencatat waktu pembuatan dan pembaruan.</p>
     *
     * @param judul judul bab
     * @param isi isi bab
     * @param urutan urutan bab dalam karya
     * @return entity {@code Bab} baru
     * @throws NullPointerException jika salah satu parameter bernilai {@code null}
     */
    static Bab buat(JudulBab judul, IsiBab isi, UrutanBab urutan) {
        Instant sekarang = Instant.now();
        return new Bab(IdBab.baru(), judul, isi, urutan, sekarang, sekarang);
    }

    /**
     * Membangun kembali entity {@code Bab} dari data yang telah tersimpan.
     *
     * <p>Factory method ini digunakan ketika entity perlu direkonstruksi
     * dari sumber penyimpanan, seperti basis data, tanpa membuat ID atau
     * waktu baru.</p>
     *
     * @param id ID unik bab
     * @param judul judul bab
     * @param isi isi bab
     * @param urutan urutan bab dalam karya
     * @param dibuatPada waktu ketika bab dibuat
     * @param diperbaruiPada waktu terakhir bab diperbarui
     * @return entity {@code Bab} yang telah direkonstruksi
     * @throws NullPointerException jika salah satu parameter bernilai {@code null}
     */
    public static Bab rekonstruksi(
            IdBab id, JudulBab judul, IsiBab isi, UrutanBab urutan,
            Instant dibuatPada, Instant diperbaruiPada
    ) {
        return new Bab(id, judul, isi, urutan, dibuatPada, diperbaruiPada);
    }

    /**
     * Mengubah judul bab dan memperbarui waktu perubahan terakhir.
     *
     * @param judulBaru judul baru untuk bab
     * @throws NullPointerException jika {@code judulBaru} bernilai {@code null}
     */
    void ubahJudul(JudulBab judulBaru) {
        this.judul = Objects.requireNonNull(judulBaru, "Judul bab tidak boleh kosong");
        this.diperbaruiPada = Instant.now();
    }

    /**
     * Mengubah isi bab dan memperbarui waktu perubahan terakhir.
     *
     * @param isiBaru isi baru untuk bab
     * @throws NullPointerException jika {@code isiBaru} bernilai {@code null}
     */
    void ubahIsi(IsiBab isiBaru) {
        this.isi = Objects.requireNonNull(isiBaru, "Isi bab tidak boleh kosong");
        this.diperbaruiPada = Instant.now();
    }

    /**
     * Mengubah urutan bab dan memperbarui waktu perubahan terakhir.
     *
     * @param urutanBaru urutan baru untuk bab
     * @throws NullPointerException jika {@code urutanBaru} bernilai {@code null}
     */
    void ubahUrutan(UrutanBab urutanBaru) {
        this.urutan = Objects.requireNonNull(urutanBaru, "Urutan bab tidak boleh kosong");
        this.diperbaruiPada = Instant.now();
    }

    public IdBab id() {
        return id;
    }

    public JudulBab judul() {
        return judul;
    }

    public IsiBab isi() {
        return isi;
    }

    public UrutanBab urutan() {
        return urutan;
    }

    public Instant dibuatPada() {
        return dibuatPada;
    }

    public Instant diperbaruiPada() {
        return diperbaruiPada;
    }
}