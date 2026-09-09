/*
 * Copyright (c) 2026 Farich Murobic
 *
 * This project is licensed under the MIT License.
 * See the LICENSE file in the project root for more information.
 */

package com.bedroom.domain.identitas.valueobject;

import java.util.Objects;

/**
 * Value object yang merepresentasikan pengenal unik seorang {@code Pengguna}
 * pada penyedia autentikasi eksternal.
 *
 * <p>Nilai pengenal bersifat opak sehingga domain tidak bergantung pada
 * format atau struktur tertentu yang ditentukan oleh penyedia eksternal,
 * seperti klaim {@code sub} dari Google OAuth.</p>
 *
 * <p>Nilai dinormalisasi dengan menghapus spasi di awal dan akhir,
 * tidak boleh kosong, dan dibatasi pada panjang maksimal yang ditentukan
 * oleh domain.</p>
 *
 * @param nilai pengenal pengguna pada penyedia eksternal
 * @throws NullPointerException jika {@code nilai} bernilai {@code null}
 * @throws IllegalArgumentException jika nilai kosong atau melebihi
 *         panjang maksimal yang ditentukan
 */
public record PengenalEksternal(String nilai) {

    private static final int PANJANG_MAKSIMAL = 255;

    public PengenalEksternal {
        Objects.requireNonNull(
                nilai, "Pengenal eksternal tidak boleh kosong");
        nilai = nilai.trim();

        if (nilai.isBlank()) {
            throw new IllegalArgumentException(
                    "Pengenal eksternal tidak boleh kosong");
        }

        if (nilai.length() > PANJANG_MAKSIMAL) {
            throw new IllegalArgumentException(
                    "Pengenal eksternal tidak boleh lebih dari %d karakter".formatted(PANJANG_MAKSIMAL)
            );
        }
    }
}