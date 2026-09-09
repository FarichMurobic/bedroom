/*
 * Copyright (c) 2026 Farich Murobic
 *
 * This project is licensed under the MIT License.
 * See the LICENSE file in the project root for more information.
 */

package com.bedroom.domain.identitas.valueobject;

import java.util.Objects;

/**
 * Value object yang merepresentasikan nama lengkap seorang pengguna.
 *
 * <p>Nama lengkap dinormalisasi dengan menghapus spasi di awal dan akhir.
 * Nilainya tidak boleh kosong dan harus berada dalam rentang panjang yang
 * ditentukan oleh aturan domain.</p>
 *
 * @param nilai nama lengkap pengguna
 * @throws NullPointerException jika {@code nilai} bernilai {@code null}
 * @throws IllegalArgumentException jika nama kosong atau panjangnya berada
 *         di luar batas yang ditentukan
 */
public record NamaLengkap(String nilai) {

    private static final int PANJANG_MINIMAL = 2;
    private static final int PANJANG_MAKSIMAL = 100;

    public NamaLengkap {
        Objects.requireNonNull(nilai, "Nama lengkap tidak boleh kosong");
        nilai = nilai.trim();

        if (nilai.isBlank()) {
            throw new IllegalArgumentException("Nama lengkap tidak boleh kosong");
        }

        if (nilai.length() < PANJANG_MINIMAL || nilai.length() > PANJANG_MAKSIMAL) {
            throw new IllegalArgumentException(
                    "Nama lengkap harus antara %d sampai %d karakter"
                            .formatted(PANJANG_MINIMAL, PANJANG_MAKSIMAL)
            );
        }
    }
}