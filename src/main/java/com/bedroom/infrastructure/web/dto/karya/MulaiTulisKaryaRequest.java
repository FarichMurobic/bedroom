/*
 * Copyright (c) 2026 Farich Murobic
 *
 * This project is licensed under the MIT License.
 * See the LICENSE file in the project root for more information.
 */

package com.bedroom.infrastructure.web.dto.karya;

import jakarta.validation.constraints.NotBlank;

/**
 * Request DTO untuk memulai penulisan karya baru.
 */
public record MulaiTulisKaryaRequest(
        @NotBlank(message = "Judul tidak boleh kosong")
        String judul,

        @NotBlank(message = "Sinopsis tidak boleh kosong")
        String sinopsis
) {
}