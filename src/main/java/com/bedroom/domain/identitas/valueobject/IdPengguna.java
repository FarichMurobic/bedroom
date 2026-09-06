/*
 * Copyright (c) 2026 Farich Murobic
 * Licensed under the MIT License.
 */
package com.bedroom.domain.identitas.valueobject;

import java.util.Objects;
import java.util.UUID;

/**
 * Value object yang merepresentasikan pengenal unik seorang {@code Pengguna}.
 * Menggunakan UUID untuk menjamin keunikan tanpa bergantung pada
 * mekanisme auto-increment basis data.
 */
public record IdPengguna(UUID nilai) {

    public IdPengguna {
        Objects.requireNonNull(
                nilai, "Id pengguna tidak boleh kosong");
    }

    public static IdPengguna baru() {
        return new IdPengguna(UUID.randomUUID());
    }

    public static IdPengguna dari(UUID nilai) {
        return new IdPengguna(nilai);
    }

    public static IdPengguna dari(String nilai) {
        Objects.requireNonNull(
                nilai, "Id pengguna tidak boleh kosong");
        String dinormalisasi = nilai.trim();

        if (dinormalisasi.isBlank()) {
            throw new IllegalArgumentException(
                    "Id pengguna tidak boleh kosong");
        }

        try {
            return new IdPengguna(UUID.fromString(dinormalisasi));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(
                    "Format id pengguna tidak valid", e);
        }
    }
}
