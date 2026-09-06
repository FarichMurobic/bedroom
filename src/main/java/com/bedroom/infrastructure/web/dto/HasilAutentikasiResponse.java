package com.bedroom.infrastructure.web.dto;

/**
 * Data yang dikembalikan ke klien setelah proses autentikasi berhasil,
 * baik melalui login maupun verifikasi (auto-login).
 */
public record HasilAutentikasiResponse(
        String idPengguna,
        String tokenAkses,
        boolean apakahPenggunaBaru
) {
}