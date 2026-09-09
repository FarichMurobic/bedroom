/*
 * Copyright (c) 2026 Farich Murobic
 *
 * This project is licensed under the MIT License.
 * See the LICENSE file in the project root for more information.
 */

package com.bedroom.infrastructure.web.dto.karya;

import jakarta.validation.constraints.NotBlank;

/**
 * Request DTO untuk menambahkan bab baru ke karya.
 */
public record TambahBabRequest(
        @NotBlank(message = "Judul bab tidak boleh kosong")
        String judulBab,

        @NotBlank(message = "Isi bab tidak boleh kosong")
        String isiBab
) {
}