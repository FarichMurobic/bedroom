package com.bedroom.infrastructure.web.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * Data yang diterima dari klien untuk mendaftarkan pengguna baru melalui email.
 */
public record RegistrasiEmailRequest(
        @NotBlank(message = "Nama pengguna tidak boleh kosong")
        String namaPengguna,

        @NotBlank(message = "Email tidak boleh kosong")
        String email,

        @NotBlank(message = "Kata sandi tidak boleh kosong")
        String kataSandi
) {
}