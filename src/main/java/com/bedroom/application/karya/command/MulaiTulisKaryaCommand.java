/*
 * Copyright (c) 2026 Farich Murobic
 *
 * This project is licensed under the MIT License.
 * See the LICENSE file in the project root for more information.
 */

package com.bedroom.application.karya.command;

/**
 * Perintah untuk memulai penulisan karya baru dengan status awal draft.
 */
public record MulaiTulisKaryaCommand(
        String judul,
        String sinopsis
) {
}