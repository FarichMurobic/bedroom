/*
 * Copyright (c) 2026 Farich Murobic
 *
 * This project is licensed under the MIT License.
 * See the LICENSE file in the project root for more information.
 */

package com.bedroom.infrastructure.web.dto.karya;

import jakarta.validation.constraints.NotBlank;

/**
 * Request DTO untuk mengubah judul bab.
 */
public record UbahJudulBabRequest(
        @NotBlank(message = "Judul baru tidak boleh kosong")
        String judulBaru
) {
}