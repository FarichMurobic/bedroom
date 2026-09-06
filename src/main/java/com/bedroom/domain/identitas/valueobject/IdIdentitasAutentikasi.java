/*
 * Copyright (c) 2026 Farich Murobic
 * Licensed under the MIT License.
 */
package com.bedroom.domain.identitas.valueobject;

import java.util.Objects;
import java.util.UUID;

/**
 * Value object yang merepresentasikan pengenal unik sebuah
 * {@code IdentitasAutentikasi}. Menggunakan UUID untuk menjamin
 * keunikan tanpa bergantung pada mekanisme auto-increment basis data.
 */
public record IdIdentitasAutentikasi(UUID nilai) {

    public IdIdentitasAutentikasi {
        Objects.requireNonNull(
                nilai, "Id identitas autentikasi tidak boleh kosong");
    }

    public static IdIdentitasAutentikasi baru() {
        return new IdIdentitasAutentikasi(UUID.randomUUID());
    }

    public static IdIdentitasAutentikasi dari(UUID nilai) {
        return new IdIdentitasAutentikasi(nilai);
    }

    public static IdIdentitasAutentikasi dari(String nilai) {
        Objects.requireNonNull(
                nilai, "Id identitas autentikasi tidak boleh kosong");
        String dinormalisasi = nilai.trim();

        if (dinormalisasi.isBlank()) {
            throw new IllegalArgumentException(
                    "Id identitas autentikasi tidak boleh kosong");
        }

        try {
            return new IdIdentitasAutentikasi(UUID.fromString(dinormalisasi));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(
                    "Format id identitas autentikasi tidak valid", e);
        }
    }
}
