/*
 * Copyright (c) 2026 Farich Murobic
 *
 * This project is licensed under the MIT License.
 * See the LICENSE file in the project root for more information.
 */

package com.bedroom.domain.interaksi.valueobject;

import java.util.Objects;
import java.util.UUID;

/**
 * Value object yang merepresentasikan pengenal unik sebuah {@code Suka}.
 *
 * <p>Nilai identitas menggunakan {@code UUID} sehingga keunikannya
 * tidak bergantung pada mekanisme auto-increment basis data.</p>
 *
 * @param nilai nilai UUID yang menjadi identitas unik suka
 * @throws NullPointerException jika {@code nilai} bernilai {@code null}
 */
public record IdSuka(UUID nilai) {

    public IdSuka {
        Objects.requireNonNull(nilai, "Id suka tidak boleh kosong");
    }

    /**
     * Membuat ID suka baru menggunakan UUID yang dihasilkan secara acak.
     *
     * @return ID suka baru
     */
    public static IdSuka baru() {
        return new IdSuka(UUID.randomUUID());
    }

    /**
     * Membuat ID suka dari objek {@code UUID}.
     *
     * @param nilai UUID yang akan digunakan sebagai ID
     * @return ID suka dengan nilai yang diberikan
     * @throws NullPointerException jika {@code nilai} bernilai {@code null}
     */
    public static IdSuka dari(UUID nilai) {
        return new IdSuka(nilai);
    }

    /**
     * Membuat ID suka dari representasi string UUID.
     *
     * <p>Nilai akan dinormalisasi dengan menghapus spasi di awal dan
     * akhir sebelum dikonversi menjadi {@code UUID}.</p>
     *
     * @param nilai representasi string UUID
     * @return ID suka yang telah dibuat
     * @throws NullPointerException jika {@code nilai} bernilai {@code null}
     * @throws IllegalArgumentException jika nilai kosong atau memiliki
     *         format UUID yang tidak valid
     */
    public static IdSuka dari(String nilai) {
        Objects.requireNonNull(nilai, "Id suka tidak boleh kosong");
        String dinormalisasi = nilai.trim();
        if (dinormalisasi.isBlank()) {
            throw new IllegalArgumentException("Id suka tidak boleh kosong");
        }
        try {
            return new IdSuka(UUID.fromString(dinormalisasi));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Format id suka tidak valid", e);
        }
    }
}