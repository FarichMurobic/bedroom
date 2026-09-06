/*
 * Copyright (c) 2026 Farich Murobic
 * Licensed under the MIT License.
 */
package com.bedroom.application.identitas.command;

/**
 * Perintah untuk mengganti nama pengguna milik pengguna yang sedang login.
 */
public record GantiNamaPenggunaCommand(
        String namaPenggunaBaru
) {
}