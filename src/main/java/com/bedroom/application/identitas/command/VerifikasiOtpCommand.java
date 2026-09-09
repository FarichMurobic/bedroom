/*
 * Copyright (c) 2026 Farich Murobic
 *
 * This project is licensed under the MIT License.
 * See the LICENSE file in the project root for more information.
 */

package com.bedroom.application.identitas.command;

/**
 * Perintah untuk memverifikasi kode OTP yang dikirim ke nomor telepon
 * pengguna guna mengaktifkan akun yang masih berstatus
 * {@code MENUNGGU_VERIFIKASI}.
 *
 * @param nomorTelepon nomor telepon pengguna yang akan diverifikasi
 * @param kodeOtp kode OTP yang dikirim ke nomor telepon pengguna
 */
public record VerifikasiOtpCommand(
        String nomorTelepon,
        String kodeOtp
) {
}