/*
 * Copyright (c) 2026 Farich Murobic
 *
 * This project is licensed under the MIT License.
 * See the LICENSE file in the project root for more information.
 */

package com.bedroom.infrastructure.web.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * Data yang diterima dari klien untuk mendaftarkan pengguna baru melalui nomor telepon.
 */
public record RegistrasiTeleponRequest(
        @NotBlank(message = "Nama pengguna tidak boleh kosong")
        String namaPengguna,

        @NotBlank(message = "Nomor telepon tidak boleh kosong")
        String nomorTelepon,

        @NotBlank(message = "Kata sandi tidak boleh kosong")
        String kataSandi
) {
}