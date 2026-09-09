/*
 * Copyright (c) 2026 Farich Murobic
 *
 * This project is licensed under the MIT License.
 * See the LICENSE file in the project root for more information.
 */

package com.bedroom.domain.identitas.valueobject;

import java.util.Objects;
import java.util.regex.Pattern;

/**
 * Value object yang merepresentasikan nomor telepon seorang {@code Pengguna}.
 *
 * <p>Nomor telepon dinormalisasi dengan menghapus spasi di awal dan akhir
 * serta wajib mengikuti format internasional {@code E.164}. Format ini
 * digunakan agar nomor telepon memiliki representasi yang konsisten
 * dan dapat digunakan oleh layanan pengiriman OTP atau SMS gateway.</p>
 *
 * @param nilai nomor telepon yang akan direpresentasikan
 * @throws NullPointerException jika {@code nilai} bernilai {@code null}
 * @throws IllegalArgumentException jika nomor telepon kosong atau tidak
 *         mengikuti format internasional {@code E.164}
 */
public record NomorTelepon(String nilai) {

    private static final Pattern POLA_E164 = Pattern.compile("^\\+[1-9]\\d{7,14}$");

    public NomorTelepon {
        Objects.requireNonNull(
                nilai, "Nomor telepon tidak boleh kosong");
        nilai = nilai.trim();

        if (nilai.isBlank()) {
            throw new IllegalArgumentException(
                    "Nomor telepon tidak boleh kosong");
        }

        if (!POLA_E164.matcher(nilai).matches()) {
            throw new IllegalArgumentException(
                    "Nomor telepon harus dalam format internasional E.164 (contoh: +6281234567890)"
            );
        }
    }
}