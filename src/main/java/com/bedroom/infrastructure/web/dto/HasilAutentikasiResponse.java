/*
 * Copyright (c) 2026 Farich Murobic
 *
 * This project is licensed under the MIT License.
 * See the LICENSE file in the project root for more information.
 */

package com.bedroom.infrastructure.web.dto;

/**
 * Data yang dikembalikan ke klien setelah proses autentikasi berhasil,
 * baik melalui login maupun verifikasi (auto-login).
 */
public record HasilAutentikasiResponse(
        String idPengguna,
        String tokenAkses,
        boolean apakahPenggunaBaru
) {
}