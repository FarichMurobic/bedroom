/*
 * Copyright (c) 2026 Farich Murobic
 * Licensed under the MIT License.
 */
package com.bedroom.application.identitas.command;

/**
 * Perintah untuk memverifikasi token konfirmasi yang dikirim ke email
 * pengguna, guna mengaktifkan akun yang masih berstatus {@code MENUNGGU_VERIFIKASI}.
 */
public record VerifikasiEmailCommand(
        String email,
        String token
) {
}