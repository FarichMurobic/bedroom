/*
 * Copyright (c) 2026 Farich Murobic
 * Licensed under the MIT License.
 */
package com.bedroom.infrastructure.web.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * Data yang diterima dari klien untuk login menggunakan nomor telepon dan kata sandi.
 */
public record LoginTeleponRequest(
        @NotBlank(message = "Nomor telepon tidak boleh kosong")
        String nomorTelepon,

        @NotBlank(message = "Kata sandi tidak boleh kosong")
        String kataSandi
) {
}