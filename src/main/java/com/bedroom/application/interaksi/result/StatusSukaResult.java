/*
 * Copyright (c) 2026 Farich Murobic
 *
 * This project is licensed under the MIT License.
 * See the LICENSE file in the project root for more information.
 */

package com.bedroom.application.interaksi.result;

/**
 * Hasil query status like pada sebuah karya.
 */
public record StatusSukaResult(
        boolean disukai,
        long jumlahSuka
) {
}