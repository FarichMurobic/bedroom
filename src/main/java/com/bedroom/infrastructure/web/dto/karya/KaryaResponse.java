/*
 * Copyright (c) 2026 Farich Murobic
 *
 * This project is licensed under the MIT License.
 * See the LICENSE file in the project root for more information.
 */

package com.bedroom.infrastructure.web.dto.karya;

import com.bedroom.application.karya.result.KaryaResult;

import java.time.Instant;
import java.util.List;
import java.util.Set;

/**
 * Response DTO untuk data karya.
 */
public record KaryaResponse(
        String id,
        String idPenulis,
        String judul,
        String sinopsis,
        String status,
        Set<String> idGenre,
        List<BabResponse> babList,
        Instant dibuatPada,
        Instant diperbaruiPada
) {
    public static KaryaResponse dari(KaryaResult hasil) {
        List<BabResponse> babResponseList = hasil.babList().stream()
                .map(BabResponse::dari)
                .toList();

        return new KaryaResponse(
                hasil.id(), hasil.idPenulis(), hasil.judul(), hasil.sinopsis(),
                hasil.status(), hasil.idGenre(), babResponseList,
                hasil.dibuatPada(), hasil.diperbaruiPada()
        );
    }
}