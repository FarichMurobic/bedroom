/*
 * Copyright (c) 2026 Farich Murobic
 *
 * This project is licensed under the MIT License.
 * See the LICENSE file in the project root for more information.
 */

package com.bedroom.domain.karya.valueobject;

import java.util.Objects;

/**
 * Value object yang merepresentasikan sinopsis atau ringkasan singkat
 * sebuah {@code Karya}.
 *
 * <p>Sinopsis dinormalisasi dengan menghapus spasi di awal dan akhir.
 * Nilainya tidak boleh kosong dan harus berada dalam rentang panjang
 * yang ditentukan oleh aturan domain.</p>
 *
 * @param nilai sinopsis atau ringkasan singkat karya
 * @throws NullPointerException jika {@code nilai} bernilai {@code null}
 * @throws IllegalArgumentException jika sinopsis kosong atau panjangnya
 *         berada di luar batas yang ditentukan
 */
public record Sinopsis(String nilai) {

    private static final int PANJANG_MINIMAL = 20;
    private static final int PANJANG_MAKSIMAL = 500;

    public Sinopsis {
        Objects.requireNonNull(nilai, "Sinopsis tidak boleh kosong");
        nilai = nilai.trim();

        if (nilai.isBlank()) {
            throw new IllegalArgumentException("Sinopsis tidak boleh kosong");
        }

        if (nilai.length() < PANJANG_MINIMAL || nilai.length() > PANJANG_MAKSIMAL) {
            throw new IllegalArgumentException(
                    "Sinopsis harus antara %d sampai %d"
                            .formatted(PANJANG_MINIMAL, PANJANG_MAKSIMAL)
            );
        }
    }
}