/*
 * Copyright (c) 2026 Farich Murobic
 * Licensed under the MIT License.
 */
package com.bedroom.infrastructure.web.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * Data yang diterima dari klien untuk login menggunakan email dan kata sandi.
 */
public record LoginEmailRequest(
        @NotBlank(message = "Email tidak boleh kosong")
        String email,

        @NotBlank(message = "Kata sandi tidak boleh kosong")
        String kataSandi
) {
}