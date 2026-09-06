/*
 * Copyright (c) 2026 Farich Murobic
 * Licensed under the MIT License.
 */
package com.bedroom.application.identitas.command;

/**
 * Perintah untuk memverifikasi kode OTP yang dikirim ke nomor telepon
 * pengguna, guna mengaktifkan akun yang masih berstatus {@code MENUNGGU_VERIFIKASI}.
 */
public record VerifikasiOtpCommand(
        String nomorTelepon,
        String kodeOtp
) {
}