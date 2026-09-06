/*
 * Copyright (c) 2026 Farich Murobic
 * Licensed under the MIT License.
 */
package com.bedroom.application.identitas.command;

/**
 * Perintah untuk melakukan autentikasi menggunakan nomor telepon dan kata sandi.
 */
public record LoginTeleponCommand(
        String nomorTelepon,
        String kataSandi
) {
}