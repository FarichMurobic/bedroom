/*
 * Copyright (c) 2026 Farich Murobic
 * Licensed under the MIT License.
 */
package com.bedroom.application.identitas.command;

/**
 * Perintah untuk mendaftarkan pengguna baru menggunakan nomor telepon dan kata sandi.
 * Setelah perintah ini dieksekusi, pengguna berstatus {@code MENUNGGU_VERIFIKASI}
 * hingga kode OTP yang dikirim ke nomor tersebut berhasil diverifikasi.
 */
public record RegistrasiTeleponCommand(
        String namaPengguna,
        String nomorTelepon,
        String kataSandi
) {
}