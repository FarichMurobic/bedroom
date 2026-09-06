/*
 * Copyright (c) 2026 Farich Murobic
 * Licensed under the MIT License.
 */
package com.bedroom.domain.identitas.valueobject;

import java.util.Objects;
import java.util.regex.Pattern;

/**
 * Value object yang merepresentasikan nomor telepon seorang {@code Pengguna}.
 * Wajib mengikuti format internasional E.164 (mis. +6281234567890)
 * agar kompatibel dengan layanan pengiriman OTP/SMS gateway.
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
