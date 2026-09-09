/*
 * Copyright (c) 2026 Farich Murobic
 *
 * This project is licensed under the MIT License.
 * See the LICENSE file in the project root for more information.
 */

package com.bedroom.application.security;

import com.bedroom.domain.identitas.valueobject.IdPengguna;

import java.util.Objects;

/**
 * Merepresentasikan pengguna yang sedang terautentikasi pada permintaan
 * (request) yang sedang berjalan.
 */
public record PenggunaTerautentikasi(
        IdPengguna idPengguna
) {
    public PenggunaTerautentikasi {
        Objects.requireNonNull(
                idPengguna, "Id pengguna tidak boleh kosong"
        );
    }
}