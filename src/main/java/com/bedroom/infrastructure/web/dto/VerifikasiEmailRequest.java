/*
 * Copyright (c) 2026 Farich Murobic
 * Licensed under the MIT License.
 */
package com.bedroom.infrastructure.web.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * Data yang diterima dari klien untuk memverifikasi tautan konfirmasi email.
 */
public record VerifikasiEmailRequest(
        @NotBlank(message = "Email tidak boleh kosong")
        String email,

        @NotBlank(message = "Token tidak boleh kosong")
        String token
) {
}