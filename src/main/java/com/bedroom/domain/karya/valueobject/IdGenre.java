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
 * Value object yang merepresentasikan pengenal unik sebuah {@code Genre}.
 *
 * <p>Nilai identitas menggunakan {@code UUID} sehingga keunikannya
 * tidak bergantung pada mekanisme auto-increment basis data.</p>
 *
 * @param nilai nilai UUID yang menjadi identitas unik genre
 * @throws NullPointerException jika {@code nilai} bernilai {@code null}
 */
public record IdGenre(UUID nilai) {

    public IdGenre {
        Objects.requireNonNull(nilai, "Id genre tidak boleh kosong");
    }

    /**
     * Membuat ID genre baru menggunakan UUID yang dihasilkan secara acak.
     *
     * @return ID genre baru
     */
    public static IdGenre baru() {
        return new IdGenre(UUID.randomUUID());
    }

    /**
     * Membuat ID genre dari objek {@code UUID}.
     *
     * @param nilai UUID yang akan digunakan sebagai ID
     * @return ID genre dengan nilai yang diberikan
     * @throws NullPointerException jika {@code nilai} bernilai {@code null}
     */
    public static IdGenre dari(UUID nilai) {
        return new IdGenre(nilai);
    }

    /**
     * Membuat ID genre dari representasi string UUID.
     *
     * <p>Nilai akan dinormalisasi dengan menghapus spasi di awal dan
     * akhir sebelum dikonversi menjadi {@code UUID}.</p>
     *
     * @param nilai representasi string UUID
     * @return ID genre yang telah dibuat
     * @throws NullPointerException jika {@code nilai} bernilai {@code null}
     * @throws IllegalArgumentException jika nilai kosong atau memiliki
     *         format UUID yang tidak valid
     */
    public static IdGenre dari(String nilai) {
        Objects.requireNonNull(nilai, "Id genre tidak boleh kosong");
        String dinormalisasi = nilai.trim();

        if (dinormalisasi.isBlank()) {
            throw new IllegalArgumentException("Id genre tidak boleh kosong");
        }

        try {
            return new IdGenre(UUID.fromString(dinormalisasi));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Format id genre tidak valid", e);
        }
    }
}