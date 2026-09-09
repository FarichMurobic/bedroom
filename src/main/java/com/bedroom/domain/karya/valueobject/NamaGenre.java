/*
 * Copyright (c) 2026 Farich Murobic
 *
 * This project is licensed under the MIT License.
 * See the LICENSE file in the project root for more information.
 */

package com.bedroom.domain.karya.valueobject;

import java.util.Locale;
import java.util.Objects;

/**
 * Value object yang merepresentasikan nama sebuah {@code Genre}.
 *
 * <p>Nama genre dinormalisasi dengan menghapus spasi di awal dan akhir,
 * kemudian mengubah huruf pertama menjadi huruf kapital dan seluruh huruf
 * berikutnya menjadi huruf kecil. Nilainya tidak boleh kosong dan harus
 * berada dalam rentang panjang yang ditentukan oleh aturan domain.</p>
 *
 * @param nilai nama genre
 * @throws NullPointerException jika {@code nilai} bernilai {@code null}
 * @throws IllegalArgumentException jika nama genre kosong atau panjangnya
 *         berada di luar batas yang ditentukan
 */
public record NamaGenre(String nilai) {

    private static final int PANJANG_MINIMAL = 2;
    private static final int PANJANG_MAKSIMAL = 30;

    public NamaGenre {
        Objects.requireNonNull(nilai, "Nama genre tidak boleh kosong");
        nilai = kapitalisasiAwal(nilai.trim());

        if (nilai.isBlank()) {
            throw new IllegalArgumentException("Nama genre tidak boleh kosong");
        }
        if (nilai.length() < PANJANG_MINIMAL || nilai.length() > PANJANG_MAKSIMAL) {
            throw new IllegalArgumentException(
                    "Nama genre harus antara %d sampai %d karakter"
                            .formatted(PANJANG_MINIMAL, PANJANG_MAKSIMAL)
            );
        }
    }

    /**
     * Menormalisasi nama genre dengan membuat huruf pertama menjadi kapital
     * dan seluruh huruf berikutnya menjadi huruf kecil.
     *
     * @param nilai nama genre yang akan dinormalisasi
     * @return nama genre yang telah dinormalisasi
     */
    private static String kapitalisasiAwal(String nilai) {
        if (nilai.isBlank()) {
            return nilai;
        }

        return nilai.substring(0, 1).toUpperCase(Locale.ROOT) +
                nilai.substring(1).toLowerCase(Locale.ROOT);
    }
}