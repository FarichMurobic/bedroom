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
 * Value object yang merepresentasikan pengenal unik sebuah
 * {@code IdentitasAutentikasi}.
 *
 * <p>Nilai identitas menggunakan {@code UUID} sehingga keunikannya
 * tidak bergantung pada mekanisme auto-increment basis data.</p>
 *
 * @param nilai nilai UUID yang menjadi identitas unik
 * @throws NullPointerException jika {@code nilai} bernilai {@code null}
 */
public record IdIdentitasAutentikasi(UUID nilai) {

    public IdIdentitasAutentikasi {
        Objects.requireNonNull(
                nilai, "Id identitas autentikasi tidak boleh kosong");
    }

    /**
     * Membuat ID identitas autentikasi baru menggunakan UUID yang
     * dihasilkan secara acak.
     *
     * @return ID identitas autentikasi baru
     */
    public static IdIdentitasAutentikasi baru() {
        return new IdIdentitasAutentikasi(UUID.randomUUID());
    }

    /**
     * Membuat ID identitas autentikasi dari objek {@code UUID}.
     *
     * @param nilai UUID yang akan digunakan sebagai ID
     * @return ID identitas autentikasi dengan nilai yang diberikan
     * @throws NullPointerException jika {@code nilai} bernilai {@code null}
     */
    public static IdIdentitasAutentikasi dari(UUID nilai) {
        return new IdIdentitasAutentikasi(nilai);
    }

    /**
     * Membuat ID identitas autentikasi dari representasi string UUID.
     *
     * <p>Nilai akan dinormalisasi dengan menghapus spasi di awal dan
     * akhir sebelum dikonversi menjadi {@code UUID}.</p>
     *
     * @param nilai representasi string UUID
     * @return ID identitas autentikasi yang telah dibuat
     * @throws NullPointerException jika {@code nilai} bernilai {@code null}
     * @throws IllegalArgumentException jika nilai kosong atau memiliki
     *         format UUID yang tidak valid
     */
    public static IdIdentitasAutentikasi dari(String nilai) {
        Objects.requireNonNull(
                nilai, "Id identitas autentikasi tidak boleh kosong");
        String dinormalisasi = nilai.trim();

        if (dinormalisasi.isBlank()) {
            throw new IllegalArgumentException(
                    "Id identitas autentikasi tidak boleh kosong");
        }

        try {
            return new IdIdentitasAutentikasi(UUID.fromString(dinormalisasi));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(
                    "Format id identitas autentikasi tidak valid", e);
        }
    }
}