/*
 * Copyright (c) 2026 Farich Murobic
 * Licensed under the MIT License.
 */
package com.bedroom.application.identitas.command;

/**
 * Perintah untuk melakukan autentikasi (atau registrasi otomatis jika belum
 * terdaftar) menggunakan ID Token yang diterbitkan oleh Google Sign-In.
 */
public record LoginGoogleCommand(
        String idTokenGoogle
) {
}