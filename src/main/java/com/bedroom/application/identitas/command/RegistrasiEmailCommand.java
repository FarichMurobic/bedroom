/*
 * Copyright (c) 2026 Farich Murobic
 * Licensed under the MIT License.
 */
package com.bedroom.application.identitas.command;

/**
 * Perintah untuk mendaftarkan pengguna baru menggunakan email dan kata sandi.
 */
public record RegistrasiEmailCommand(
        String namaPengguna,
        String email,
        String kataSandi
) {
}