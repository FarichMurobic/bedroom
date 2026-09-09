/*
 * Copyright (c) 2026 Farich Murobic
 *
 * This project is licensed under the MIT License.
 * See the LICENSE file in the project root for more information.
 */

package com.bedroom.shared.exception;

/**
 * Dilempar ketika suatu sumber daya (entity) yang diharapkan ada
 * ternyata tidak ditemukan. Dipetakan ke HTTP 404 Not Found.
 */
public class SumberDayaTidakDitemukanException extends RuntimeException {

    public SumberDayaTidakDitemukanException(String pesan) {
        super(pesan);
    }
}