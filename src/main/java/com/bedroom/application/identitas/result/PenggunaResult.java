/*
 * Copyright (c) 2026 Farich Murobic
 *
 * This project is licensed under the MIT License.
 * See the LICENSE file in the project root for more information.
 */

package com.bedroom.application.identitas.result;

/**
 * Representasi data {@code Pengguna} yang dikembalikan dari application service.
 */
public record PenggunaResult(
        String id,
        String namaPengguna,
        String status
) {
}