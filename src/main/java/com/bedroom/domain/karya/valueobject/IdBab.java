/*
 * Copyright (c) 2026 Farich Murobic
 *
 * This project is licensed under the MIT License.
 * See the LICENSE file in the project root for more information.
 */

package com.bedroom.domain.karya.valueobject;

import java.util.Objects;
import java.util.UUID;

/**
 * Value object yang merepresentasikan pengenal unik sebuah {@code Bab}.
 *
 * <p>Nilai identitas menggunakan {@code UUID} sehingga keunikannya
 * tidak bergantung pada mekanisme auto-increment basis data.</p>
 *
 * @param nilai nilai UUID yang menjadi identitas unik bab
 * @throws NullPointerException jika {@code nilai} bernilai {@code null}
 */
public record IdBab(UUID nilai) {

    public IdBab {
        Objects.requireNonNull(nilai, "Id bab tidak boleh kosong");
    }

    /**
     * Membuat ID bab baru menggunakan UUID yang dihasilkan secara acak.
     *
     * @return ID bab baru
     */
    public static IdBab baru() {
        return new IdBab(UUID.randomUUID());
    }

    /**
     * Membuat ID bab dari objek {@code UUID}.
     *
     * @param nilai UUID yang akan digunakan sebagai ID
     * @return ID bab dengan nilai yang diberikan
     * @throws NullPointerException jika {@code nilai} bernilai {@code null}
     */
    public static IdBab dari(UUID nilai) {
        return new IdBab(nilai);
    }

    /**
     * Membuat ID bab dari representasi string UUID.
     *
     * <p>Nilai akan dinormalisasi dengan menghapus spasi di awal dan
     * akhir sebelum dikonversi menjadi {@code UUID}.</p>
     *
     * @param nilai representasi string UUID
     * @return ID bab yang telah dibuat
     * @throws NullPointerException jika {@code nilai} bernilai {@code null}
     * @throws IllegalArgumentException jika nilai kosong atau memiliki
     *         format UUID yang tidak valid
     */
    public static IdBab dari(String nilai) {
        Objects.requireNonNull(nilai, "Id bab tidak boleh kosong");
        String dinormalisasi = nilai.trim();

        if (dinormalisasi.isBlank()) {
            throw new IllegalArgumentException("Id bab tidak boleh kosong");
        }

        try {
            return new IdBab(UUID.fromString(dinormalisasi));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(
                    "Format id bab tidak boleh kosong",
                    e
            );
        }
    }
}