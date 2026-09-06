/*
 * Copyright (c) 2026 Farich Murobic
 * Licensed under the MIT License.
 */
package com.bedroom.domain.identitas.valueobject;

import java.util.Objects;

/**
 * Value object yang membungkus hasil hash dari kata sandi seorang {@code Pengguna}.
 * Kelas ini tidak menyimpan maupun mengetahui proses hashing itu sendiri —
 * tanggung jawab tersebut didelegasikan ke port {@code PenghasilHash}
 * pada lapisan aplikasi, agar domain tetap bebas dari dependensi library kriptografi.
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

    @Override
    public String toString() {
        return "HashKataSandi[PROTECTED]";
    }
}
