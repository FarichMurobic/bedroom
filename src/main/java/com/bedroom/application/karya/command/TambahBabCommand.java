/*
 * Copyright (c) 2026 Farich Murobic
 *
 * This project is licensed under the MIT License.
 * See the LICENSE file in the project root for more information.
 */

package com.bedroom.application.karya.command;

/**
 * Perintah untuk menambahkan bab baru ke dalam karya.
 */
public record TambahBabCommand(
        String idKarya,
        String judulBab,
        String isiBab
) {
}