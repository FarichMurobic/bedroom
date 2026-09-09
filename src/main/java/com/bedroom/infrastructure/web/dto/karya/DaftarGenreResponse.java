/*
 * Copyright (c) 2026 Farich Murobic
 *
 * This project is licensed under the MIT License.
 * See the LICENSE file in the project root for more information.
 */

package com.bedroom.infrastructure.web.dto.karya;

import java.util.List;

/**
 * Response DTO untuk daftar genre.
 */
public record DaftarGenreResponse(
        List<GenreResponse> daftar
) {
    public static DaftarGenreResponse dari(List<com.bedroom.application.karya.result.GenreResult> hasilList) {
        return new DaftarGenreResponse(hasilList.stream().map(GenreResponse::dari).toList());
    }
}