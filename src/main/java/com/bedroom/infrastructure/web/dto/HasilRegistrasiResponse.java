/*
 * Copyright (c) 2026 Farich Murobic
 * Licensed under the MIT License.
 */
package com.bedroom.infrastructure.web.dto;

/**
 * Data yang dikembalikan ke klien setelah registrasi berhasil.
 */
public record HasilRegistrasiResponse(
        String idPengguna,
        String namaPengguna
) {
}