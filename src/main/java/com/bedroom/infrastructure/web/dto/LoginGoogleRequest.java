/*
 * Copyright (c) 2026 Farich Murobic
 * Licensed under the MIT License.
 */
package com.bedroom.infrastructure.web.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * Data yang diterima dari klien untuk login/registrasi otomatis menggunakan
 * ID Token yang diterbitkan oleh Google Sign-In.
 */
public record LoginGoogleRequest(
        @NotBlank(message = "ID token Google tidak boleh kosong")
        String idTokenGoogle
) {
}