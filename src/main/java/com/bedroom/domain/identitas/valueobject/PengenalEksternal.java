/*
 * Copyright (c) 2026 Farich Murobic
 * Licensed under the MIT License.
 */
package com.bedroom.domain.identitas.valueobject;

import java.util.Objects;

/**
 * Value object yang merepresentasikan pengenal unik seorang {@code Pengguna}
 * pada penyedia autentikasi eksternal, seperti klaim {@code sub} dari Google OAuth.
 * Nilainya bersifat opak — formatnya sepenuhnya ditentukan oleh penyedia eksternal
 * sehingga tidak divalidasi secara ketat oleh domain.
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
