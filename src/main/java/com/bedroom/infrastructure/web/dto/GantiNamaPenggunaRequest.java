package com.bedroom.infrastructure.web.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * Data yang diterima dari klien untuk mengganti nama pengguna.
 */
public record GantiNamaPenggunaRequest(
        @NotBlank(message = "Nama pengguna baru tidak boleh kosong")
        String namaPenggunaBaru
) {
}