/*
 * Copyright (c) 2026 Farich Murobic
 *
 * This project is licensed under the MIT License.
 * See the LICENSE file in the project root for more information.
 */

package com.bedroom.application.identitas.command;

/**
 * Perintah untuk mendaftarkan pengguna baru menggunakan email
 * dan kata sandi.
 *
 * @param namaPengguna nama pengguna yang akan digunakan
 * @param email alamat email yang digunakan untuk registrasi
 * @param kataSandi kata sandi yang digunakan untuk registrasi
 */
public record RegistrasiEmailCommand(
        String namaPengguna,
        String email,
        String kataSandi
) {
}