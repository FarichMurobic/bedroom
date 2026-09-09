/*
 * Copyright (c) 2026 Farich Murobic
 *
 * This project is licensed under the MIT License.
 * See the LICENSE file in the project root for more information.
 */

package com.bedroom.application.identitas.command;

/**
 * Perintah untuk melakukan autentikasi atau registrasi otomatis jika pengguna
 * belum terdaftar menggunakan ID Token yang diterbitkan oleh Google Sign-In.
 *
 * @param idTokenGoogle ID Token Google yang digunakan untuk proses autentikasi
 *                      atau registrasi pengguna
 */
public record LoginGoogleCommand(
        String idTokenGoogle
) {
}