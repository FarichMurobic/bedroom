package com.bedroom.infrastructure.web.dto;

/**
 * Data yang dikembalikan ke klien setelah registrasi berhasil.
 */
public record HasilRegistrasiResponse(
        String idPengguna,
        String namaPengguna
) {
}