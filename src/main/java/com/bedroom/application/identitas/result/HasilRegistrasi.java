/*
 * Copyright (c) 2026 Farich Murobic
 *
 * This project is licensed under the MIT License.
 * See the LICENSE file in the project root for more information.
 */

package com.bedroom.application.identitas.result;

import com.bedroom.domain.identitas.valueobject.IdPengguna;

/**
 * Hasil dari proses registrasi yang berhasil. Tidak menyertakan token akses
 * karena akun yang baru dibuat melalui email atau telepon masih memerlukan
 * verifikasi lebih lanjut sebelum dapat digunakan sepenuhnya.
 */
public record HasilRegistrasi(
        IdPengguna idPengguna,
        String namaPengguna
) {
}