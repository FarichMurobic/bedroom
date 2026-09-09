/*
 * Copyright (c) 2026 Farich Murobic
 *
 * This project is licensed under the MIT License.
 * See the LICENSE file in the project root for more information.
 */

package com.bedroom.infrastructure.web.dto;

import com.bedroom.application.identitas.result.PenggunaResult;

/**
 * Response DTO untuk data pengguna.
 */
public record PenggunaResponse(
        String id,
        String namaPengguna,
        String status
) {
    public static PenggunaResponse dari(PenggunaResult hasil) {
        return new PenggunaResponse(hasil.id(), hasil.namaPengguna(), hasil.status());
    }
}