/*
 * Copyright (c) 2026 Farich Murobic
 *
 * This project is licensed under the MIT License.
 * See the LICENSE file in the project root for more information.
 */

package com.bedroom.infrastructure.web.dto;

import java.time.LocalDate;

/**
 * Data yang diterima dari klien untuk melengkapi profil. Seluruh field
 * bersifat opsional — field yang tidak dikirim (null) tidak akan diubah.
 */
public record LengkapiProfilRequest(
        String namaLengkap,
        LocalDate tanggalLahir,
        String jenisKelamin,
        String urlAvatar
) {
}