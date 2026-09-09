/*
 * Copyright (c) 2026 Farich Murobic
 *
 * This project is licensed under the MIT License.
 * See the LICENSE file in the project root for more information.
 */

package com.bedroom.application.karya.command;

/**
 * Perintah untuk mengubah judul sebuah bab.
 */
public record UbahJudulBabCommand(
        String idKarya,
        String idBab,
        String judulBaru
) {
}