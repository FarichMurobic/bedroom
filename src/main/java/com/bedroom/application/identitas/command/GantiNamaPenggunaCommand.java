/*
 * Copyright (c) 2026 Farich Murobic
 *
 * This project is licensed under the MIT License.
 * See the LICENSE file in the project root for more information.
 */

package com.bedroom.application.identitas.command;

/**
 * Perintah untuk mengganti nama pengguna milik pengguna yang sedang login.
 *
 * @param namaPenggunaBaru nama pengguna baru yang akan digunakan
 */
public record GantiNamaPenggunaCommand(
        String namaPenggunaBaru
) {
}