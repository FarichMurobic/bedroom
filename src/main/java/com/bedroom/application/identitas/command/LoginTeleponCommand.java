/*
 * Copyright (c) 2026 Farich Murobic
 *
 * This project is licensed under the MIT License.
 * See the LICENSE file in the project root for more information.
 */

package com.bedroom.application.identitas.command;

/**
 * Perintah untuk melakukan autentikasi menggunakan nomor telepon
 * dan kata sandi.
 *
 * @param nomorTelepon nomor telepon yang digunakan untuk autentikasi
 * @param kataSandi kata sandi yang digunakan untuk autentikasi
 */
public record LoginTeleponCommand(
        String nomorTelepon,
        String kataSandi
) {
}