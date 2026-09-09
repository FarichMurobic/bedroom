/*
 * Copyright (c) 2026 Farich Murobic
 *
 * This project is licensed under the MIT License.
 * See the LICENSE file in the project root for more information.
 */

package com.bedroom.application.karya.command;

/**
 * Perintah untuk mengubah isi dari sebuah bab.
 */
public record UbahIsiBabCommand(
        String idKarya,
        String idBab,
        String isiBaru
) {
}