package com.bedroom.application.identitas.command;

/**
 * Perintah untuk melakukan autentikasi menggunakan nomor telepon dan kata sandi.
 */
public record LoginTeleponCommand(
        String nomorTelepon,
        String kataSandi
) {
}