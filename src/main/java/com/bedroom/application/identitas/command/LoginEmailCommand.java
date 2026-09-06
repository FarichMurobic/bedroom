/*
 * Copyright (c) 2026 Farich Murobic
 * Licensed under the MIT License.
 */
package com.bedroom.application.identitas.command;

/**
 * Perintah untuk melakukan autentikasi menggunakan email dan kata sandi.
 */
public record LoginEmailCommand(
        String email,
        String kataSandi
) {
}