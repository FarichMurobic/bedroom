/*
 * Copyright (c) 2026 Farich Murobic
 *
 * This project is licensed under the MIT License.
 * See the LICENSE file in the project root for more information.
 */

package com.bedroom.application.identitas.command;

/**
 * Perintah untuk mendaftarkan pengguna baru menggunakan nomor telepon
 * dan kata sandi.
 *
 * <p>Setelah perintah ini dieksekusi, pengguna berstatus
 * {@code MENUNGGU_VERIFIKASI} hingga kode OTP yang dikirim ke nomor
 * tersebut berhasil diverifikasi.</p>
 *
 * @param namaPengguna nama pengguna yang akan digunakan
 * @param nomorTelepon nomor telepon yang digunakan untuk registrasi
 * @param kataSandi kata sandi yang digunakan untuk registrasi
 */
public record RegistrasiTeleponCommand(
        String namaPengguna,
        String nomorTelepon,
        String kataSandi
) {
}