/*
 * Copyright (c) 2026 Farich Murobic
 *
 * This project is licensed under the MIT License.
 * See the LICENSE file in the project root for more information.
 */

package com.bedroom.shared.exception;

/**
 * Dilempar ketika permintaan bertentangan dengan data yang sudah ada
 * (mis. email atau nama pengguna sudah digunakan). Dipetakan ke HTTP 409 Conflict.
 */
public class KonflikDataException extends RuntimeException {

    public KonflikDataException(String pesan) {
        super(pesan);
    }
}