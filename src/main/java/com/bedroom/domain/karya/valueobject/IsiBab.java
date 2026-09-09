/*
 * Copyright (c) 2026 Farich Murobic
 *
 * This project is licensed under the MIT License.
 * See the LICENSE file in the project root for more information.
 */

package com.bedroom.domain.karya.valueobject;

import java.util.Objects;

/**
 * Value object yang merepresentasikan konten atau isi tulisan sebuah {@code Bab}.
 *
 * <p>Isi bab dinormalisasi dengan menghapus whitespace di awal dan akhir.
 * Nilainya tidak boleh kosong dan harus memenuhi panjang minimal yang
 * ditentukan oleh aturan domain.</p>
 *
 * @param nilai isi tulisan bab
 * @throws NullPointerException jika {@code nilai} bernilai {@code null}
 * @throws IllegalArgumentException jika isi bab kosong atau panjangnya
 *         kurang dari batas minimal yang ditentukan
 */
public record IsiBab(String nilai) {

    private static final int PANJANG_MINIMAL = 50;

    public IsiBab {
        Objects.requireNonNull(nilai, "Isi bab tidak boleh kosong");
        nilai = nilai.strip();

        if (nilai.isBlank()) {
            throw new IllegalArgumentException("Isi bab tidak boleh kosong");
        }
        if (nilai.length() < PANJANG_MINIMAL) {
            throw new IllegalArgumentException(
                    "Isi bab minimal %d karakter".formatted(PANJANG_MINIMAL)
            );
        }
    }
}