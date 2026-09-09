/*
 * Copyright (c) 2026 Farich Murobic
 *
 * This project is licensed under the MIT License.
 * See the LICENSE file in the project root for more information.
 */

package com.bedroom.domain.identitas.valueobject;

import java.util.Objects;

/**
 * Value object yang merepresentasikan hasil hash dari kata sandi seorang
 * {@code Pengguna}.
 *
 * <p>Value object ini hanya menyimpan hasil hash dan tidak mengetahui
 * maupun menangani proses hashing. Proses pembuatan hash didelegasikan
 * kepada {@code PenghasilHashKataSandi} pada lapisan aplikasi atau
 * implementasinya di lapisan infrastruktur, sehingga domain tetap
 * bebas dari dependensi library kriptografi.</p>
 *
 * <p>Nilai hash tidak ditampilkan secara langsung melalui
 * {@code toString()} untuk mencegah hash kata sandi terekspos melalui
 * logging atau representasi objek.</p>
 *
 * @param nilai hasil hash dari kata sandi
 * @throws NullPointerException jika {@code nilai} bernilai {@code null}
 * @throws IllegalArgumentException jika {@code nilai} kosong atau hanya
 *         terdiri dari whitespace
 */
public record HashKataSandi(String nilai) {

    public HashKataSandi {
        Objects.requireNonNull(
                nilai, "Hash kata sandi tidak boleh kosong");

        if (nilai.isBlank()) {
            throw new IllegalArgumentException(
                    "Hash kata sandi tidak boleh kosong");
        }
    }

    /**
     * Mengembalikan representasi aman dari value object tanpa mengekspos
     * nilai hash yang sebenarnya.
     *
     * @return representasi hash yang dilindungi
     */
    @Override
    public String toString() {
        return "HashKataSandi[PROTECTED]";
    }
}