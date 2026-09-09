/*
 * Copyright (c) 2026 Farich Murobic
 *
 * This project is licensed under the MIT License.
 * See the LICENSE file in the project root for more information.
 */

package com.bedroom.domain.identitas.valueobject;

import java.util.Locale;
import java.util.Objects;
import java.util.regex.Pattern;

/**
 * Value object yang merepresentasikan alamat email seorang {@code Pengguna}.
 *
 * <p>Email dinormalisasi dengan menghapus spasi di awal dan akhir serta
 * mengubah seluruh karakter menjadi huruf kecil. Nilai email juga harus
 * memenuhi format yang valid dan tidak melebihi panjang maksimal yang
 * ditentukan oleh domain.</p>
 *
 * <p>Value object ini digunakan sebagai salah satu metode autentikasi
 * ({@code PenyediaAutentikasi.EMAIL}) maupun sebagai atribut identitas
 * yang diperoleh dari penyedia eksternal seperti Google.</p>
 *
 * @param nilai alamat email yang akan direpresentasikan
 * @throws NullPointerException jika {@code nilai} bernilai {@code null}
 * @throws IllegalArgumentException jika email kosong, melebihi panjang
 *         maksimal, atau memiliki format yang tidak valid
 */
public record Email(String nilai) {

    private static final Pattern POLA_EMAIL = Pattern.compile(
            "^[A-Za-z0-9.!#$%&'*+/=?^_`{|}~-]+@"
                    + "[A-Za-z0-9](?:[A-Za-z0-9-]{0,61}[A-Za-z0-9])?"
                    + "(?:\\.[A-Za-z0-9](?:[A-Za-z0-9-]{0,61}[A-Za-z0-9])?)+$"
    );
    private static final int PANJANG_MAKSIMAL = 100;

    public Email {
        Objects.requireNonNull(
                nilai, "Email tidak boleh kosong");
        nilai = nilai.trim().toLowerCase(Locale.ROOT);

        if (nilai.isBlank()) {
            throw new IllegalArgumentException(
                    "Email tidak boleh kosong");
        }

        if (nilai.length() > PANJANG_MAKSIMAL) {
            throw new IllegalArgumentException(
                    "Email tidak boleh lebih dari %d karakter".formatted(PANJANG_MAKSIMAL)
            );
        }

        if (!POLA_EMAIL.matcher(nilai).matches()) {
            throw new IllegalArgumentException(
                    "Format email tidak valid");
        }
    }
}