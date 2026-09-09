/*
 * Copyright (c) 2026 Farich Murobic
 *
 * This project is licensed under the MIT License.
 * See the LICENSE file in the project root for more information.
 */

package com.bedroom.infrastructure.web.dto.karya;

import com.bedroom.application.interaksi.result.StatusSukaResult;

/**
 * Response DTO untuk status like pada sebuah karya.
 */
public record StatusSukaResponse(
        boolean disukai,
        long jumlahSuka
) {
    public static StatusSukaResponse dari(StatusSukaResult hasil) {
        return new StatusSukaResponse(hasil.disukai(), hasil.jumlahSuka());
    }
}