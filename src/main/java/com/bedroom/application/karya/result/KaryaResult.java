/*
 * Copyright (c) 2026 Farich Murobic
 *
 * This project is licensed under the MIT License.
 * See the LICENSE file in the project root for more information.
 */

package com.bedroom.application.karya.result;

import java.time.Instant;
import java.util.List;
import java.util.Set;

/**
 * Representasi data karya yang dikembalikan dari application service.
 */
public record KaryaResult(
        String id,
        String idPenulis,
        String judul,
        String sinopsis,
        String status,
        Set<String> idGenre,
        List<BabResult> babList,
        Instant dibuatPada,
        Instant diperbaruiPada
) {
}