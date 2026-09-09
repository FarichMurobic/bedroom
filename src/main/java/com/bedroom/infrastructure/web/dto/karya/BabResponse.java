/*
 * Copyright (c) 2026 Farich Murobic
 *
 * This project is licensed under the MIT License.
 * See the LICENSE file in the project root for more information.
 */

package com.bedroom.infrastructure.web.dto.karya;

import com.bedroom.application.karya.result.BabResult;

/**
 * Response DTO untuk data bab.
 */
public record BabResponse(
        String id,
        String judul,
        String isi,
        int urutan
) {
    public static BabResponse dari(BabResult hasil) {
        return new BabResponse(hasil.id(), hasil.judul(), hasil.isi(), hasil.urutan());
    }
}