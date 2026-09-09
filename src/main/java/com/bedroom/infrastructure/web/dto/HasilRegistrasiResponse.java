/*
 * Copyright (c) 2026 Farich Murobic
 *
 * This project is licensed under the MIT License.
 * See the LICENSE file in the project root for more information.
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