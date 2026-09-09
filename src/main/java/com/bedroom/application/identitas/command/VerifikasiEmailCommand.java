/*
 * Copyright (c) 2026 Farich Murobic
 *
 * This project is licensed under the MIT License.
 * See the LICENSE file in the project root for more information.
 */

package com.bedroom.application.identitas.command;

/**
 * Perintah untuk memverifikasi token konfirmasi yang dikirim ke email
 * pengguna guna mengaktifkan akun yang masih berstatus
 * {@code MENUNGGU_VERIFIKASI}.
 *
 * @param email alamat email pengguna yang akan diverifikasi
 * @param token token konfirmasi yang dikirim ke email pengguna
 */
public record VerifikasiEmailCommand(
        String email,
        String token
) {
}