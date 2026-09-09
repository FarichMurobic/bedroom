/*
 * Copyright (c) 2026 Farich Murobic
 *
 * This project is licensed under the MIT License.
 * See the LICENSE file in the project root for more information.
 */

package com.bedroom.infrastructure.web.dto;

import com.bedroom.application.identitas.result.ProfilResult;

import java.time.Instant;
import java.time.LocalDate;

/**
 * Response DTO untuk data profil pengguna.
 */
public record ProfilResponse(
        String idPengguna,
        String namaLengkap,
        LocalDate tanggalLahir,
        String jenisKelamin,
        String urlAvatar,
        Instant diperbaruiPada
) {
    public static ProfilResponse dari(ProfilResult hasil) {
        return new ProfilResponse(
                hasil.idPengguna(), hasil.namaLengkap(), hasil.tanggalLahir(),
                hasil.jenisKelamin(), hasil.urlAvatar(), hasil.diperbaruiPada()
        );
    }
}