package com.bedroom.application.identitas.command;

/**
 * Perintah untuk melakukan autentikasi menggunakan email dan kata sandi.
 */
public record LoginEmailCommand(
        String email,
        String kataSandi
) {
}