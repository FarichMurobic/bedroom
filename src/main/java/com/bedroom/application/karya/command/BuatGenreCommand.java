/*
 * Copyright (c) 2026 Farich Murobic
 *
 * This project is licensed under the MIT License.
 * See the LICENSE file in the project root for more information.
 */

package com.bedroom.application.karya.command;

/**
 * Perintah untuk membuat genre baru. Hanya dapat dieksekusi
 * oleh pengguna berperan ADMIN.
 */
public record BuatGenreCommand(
        String nama
) {
}