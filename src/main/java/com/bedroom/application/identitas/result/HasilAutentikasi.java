/*
 * Copyright (c) 2026 Farich Murobic
 *
 * This project is licensed under the MIT License.
 * See the LICENSE file in the project root for more information.
 */

package com.bedroom.application.identitas.result;

import com.bedroom.domain.identitas.valueobject.IdPengguna;

/**
 * Hasil dari proses autentikasi yang berhasil, berisi identitas pengguna
 * dan token akses yang diterbitkan. Untuk alur Google, {@code apakahPenggunaBaru}
 * menandai apakah akun dibuat secara otomatis pada proses ini.
 */
public record HasilAutentikasi(
        IdPengguna idPengguna,
        String tokenAkses,
        boolean apakahPenggunaBaru
) {
}