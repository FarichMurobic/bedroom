/*
 * Copyright (c) 2026 Farich Murobic
 *
 * This project is licensed under the MIT License.
 * See the LICENSE file in the project root for more information.
 */

package com.bedroom.domain.identitas.valueobject;

import java.util.Objects;
import java.util.UUID;

/**
 * Value object yang merepresentasikan pengenal unik seorang {@code Pengguna}.
 *
 * <p>Nilai identitas menggunakan {@code UUID} sehingga keunikannya
 * tidak bergantung pada mekanisme auto-increment basis data.</p>
 *
 * @param nilai nilai UUID yang menjadi identitas unik pengguna
 * @throws NullPointerException jika {@code nilai} bernilai {@code null}
 */
public record IdPengguna(UUID nilai) {

    public IdPengguna {
        Objects.requireNonNull(
                nilai, "Id pengguna tidak boleh kosong");
    }

    /**
     * Membuat ID pengguna baru menggunakan UUID yang dihasilkan secara acak.
     *
     * @return ID pengguna baru
     */
    public static IdPengguna baru() {
        return new IdPengguna(UUID.randomUUID());
    }

    /**
     * Membuat ID pengguna dari objek {@code UUID}.
     *
     * @param nilai UUID yang akan digunakan sebagai ID
     * @return ID pengguna dengan nilai yang diberikan
     * @throws NullPointerException jika {@code nilai} bernilai {@code null}
     */
    public static IdPengguna dari(UUID nilai) {
        return new IdPengguna(nilai);
    }

    /**
     * Membuat ID pengguna dari representasi string UUID.
     *
     * <p>Nilai akan dinormalisasi dengan menghapus spasi di awal dan
     * akhir sebelum dikonversi menjadi {@code UUID}.</p>
     *
     * @param nilai representasi string UUID
     * @return ID pengguna yang telah dibuat
     * @throws NullPointerException jika {@code nilai} bernilai {@code null}
     * @throws IllegalArgumentException jika nilai kosong atau memiliki
     *         format UUID yang tidak valid
     */
    public static IdPengguna dari(String nilai) {
        Objects.requireNonNull(
                nilai, "Id pengguna tidak boleh kosong");
        String dinormalisasi = nilai.trim();

        if (dinormalisasi.isBlank()) {
            throw new IllegalArgumentException(
                    "Id pengguna tidak boleh kosong");
        }

        try {
            return new IdPengguna(UUID.fromString(dinormalisasi));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(
                    "Format id pengguna tidak valid", e);
        }
    }
}