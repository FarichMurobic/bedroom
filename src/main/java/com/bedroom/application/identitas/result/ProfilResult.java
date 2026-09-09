/*
 * Copyright (c) 2026 Farich Murobic
 *
 * This project is licensed under the MIT License.
 * See the LICENSE file in the project root for more information.
 */

package com.bedroom.application.identitas.result;

import java.time.Instant;
import java.time.LocalDate;

/**
 * Representasi data profil pengguna yang dikembalikan dari application service.
 */
public record ProfilResult(
        String idPengguna,
        String namaLengkap,
        LocalDate tanggalLahir,
        String jenisKelamin,
        String urlAvatar,
        Instant diperbaruiPada
) {
}