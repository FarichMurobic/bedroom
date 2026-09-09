/*
 * Copyright (c) 2026 Farich Murobic
 *
 * This project is licensed under the MIT License.
 * See the LICENSE file in the project root for more information.
 */

package com.bedroom.infrastructure.web.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * Data yang diterima dari klien untuk memverifikasi kode OTP telepon.
 */
public record VerifikasiOtpRequest(
        @NotBlank(message = "Nomor telepon tidak boleh kosong")
        String nomorTelepon,

        @NotBlank(message = "Kode OTP tidak boleh kosong")
        String kodeOtp
) {
}