/*
 * Copyright (c) 2026 Farich Murobic
 *
 * This project is licensed under the MIT License.
 * See the LICENSE file in the project root for more information.
 */

package com.bedroom.infrastructure.web.dto.karya;

import java.util.List;

/**
 * Response DTO untuk daftar karya.
 */
public record DaftarKaryaResponse(
        List<KaryaResponse> daftar
) {
    public static DaftarKaryaResponse dari(List<com.bedroom.application.karya.result.KaryaResult> hasilList) {
        return new DaftarKaryaResponse(hasilList.stream().map(KaryaResponse::dari).toList());
    }
}