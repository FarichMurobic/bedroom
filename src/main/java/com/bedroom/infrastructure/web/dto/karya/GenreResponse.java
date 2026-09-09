/*
 * Copyright (c) 2026 Farich Murobic
 *
 * This project is licensed under the MIT License.
 * See the LICENSE file in the project root for more information.
 */

package com.bedroom.infrastructure.web.dto.karya;

import com.bedroom.application.karya.result.GenreResult;

/**
 * Response DTO untuk data genre.
 */
public record GenreResponse(
        String id,
        String nama
) {
    public static GenreResponse dari(GenreResult hasil) {
        return new GenreResponse(hasil.id(), hasil.nama());
    }
}