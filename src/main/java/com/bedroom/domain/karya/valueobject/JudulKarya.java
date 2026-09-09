/*
 * Copyright (c) 2026 Farich Murobic
 *
 * This project is licensed under the MIT License.
 * See the LICENSE file in the project root for more information.
 */

package com.bedroom.domain.karya.valueobject;

import java.util.Objects;

/**
 * Value object yang merepresentasikan judul sebuah {@code Karya}.
 *
 * <p>Judul karya dinormalisasi dengan menghapus spasi di awal dan akhir.
 * Nilainya tidak boleh kosong dan harus berada dalam rentang panjang
 * yang ditentukan oleh aturan domain.</p>
 *
 * @param nilai judul karya
 * @throws NullPointerException jika {@code nilai} bernilai {@code null}
 * @throws IllegalArgumentException jika judul kosong atau panjangnya
 *         berada di luar batas yang ditentukan
 */
public record JudulKarya(String nilai) {

    private static final int PANJANG_MINIMAL = 3;
    private static final int PANJANG_MAKSIMAL = 150;

    public JudulKarya {
        Objects.requireNonNull(nilai, "Judul karya tidak boleh kosong");
        nilai = nilai.trim();

        if (nilai.isBlank()) {
            throw new IllegalArgumentException("Judul karya tidak boleh kosong");
        }
        if (nilai.length() < PANJANG_MINIMAL || nilai.length() > PANJANG_MAKSIMAL) {
            throw new IllegalArgumentException(
                    "Judul karya harus antara %d sampai %d karakter"
                            .formatted(PANJANG_MINIMAL, PANJANG_MAKSIMAL)
            );
        }
    }
}