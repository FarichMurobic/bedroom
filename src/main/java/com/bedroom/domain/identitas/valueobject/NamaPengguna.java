/*
 * Copyright (c) 2026 Farich Murobic
 * Licensed under the MIT License.
 */
package com.bedroom.domain.identitas.valueobject;

import java.util.Locale;
import java.util.Objects;
import java.util.regex.Pattern;

/**
 * Value object yang merepresentasikan nama tampilan (display name)
 * seorang {@code Pengguna} di platform Bedroom, sekaligus dipakai
 * sebagai pengenal pada URL profil publik (mis. bedroom.com/@namapengguna).
 */
public record NamaPengguna(String nilai) {

    private static final int PANJANG_MINIMAL = 3;
    private static final int PANJANG_MAKSIMAL = 30;

    private static final Pattern KARAKTER_DIIZINKAN =
            Pattern.compile("^[a-z0-9._]+$");
    private static final Pattern KARAKTER_BERURUTAN_TERLARANG =
            Pattern.compile("[._]{2,}");

    public NamaPengguna {
        Objects.requireNonNull(
                nilai, "Nama pengguna tidak boleh kosong");
        nilai = nilai.trim().toLowerCase(Locale.ROOT);

        if (nilai.isBlank()) {
            throw new IllegalArgumentException(
                    "Nama pengguna tidak boleh kosong");
        }

        if (nilai.length() < PANJANG_MINIMAL || nilai.length() > PANJANG_MAKSIMAL) {
            throw new IllegalArgumentException(
                    "Nama pengguna harus antara %d sampai %d karakter"
                            .formatted(PANJANG_MINIMAL, PANJANG_MAKSIMAL)
            );
        }

        if (!KARAKTER_DIIZINKAN.matcher(nilai).matches()) {
            throw new IllegalArgumentException(
                    "Nama pengguna hanya boleh berisi huruf kecil, angka, titik, dan garis bawah"
            );
        }

        if (nilai.startsWith(".") || nilai.startsWith("_") || nilai.endsWith(".") || nilai.endsWith("_")) {
            throw new IllegalArgumentException(
                    "Nama pengguna tidak boleh diawali atau diakhiri dengan titik atau garis bawah"
            );
        }

        if (KARAKTER_BERURUTAN_TERLARANG.matcher(nilai).find()) {
            throw new IllegalArgumentException(
                    "Nama pengguna tidak boleh memiliki titik atau garis bawah yang berurutan"
            );
        }
    }
}
