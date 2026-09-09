/*
 * Copyright (c) 2026 Farich Murobic
 *
 * This project is licensed under the MIT License.
 * See the LICENSE file in the project root for more information.
 */

package com.bedroom.application.karya.command;

/**
 * Perintah untuk mengganti sinopsis sebuah karya.
 */
public record GantiSinopsisKaryaCommand(
        String idKarya,
        String sinopsisBaru
) {
}