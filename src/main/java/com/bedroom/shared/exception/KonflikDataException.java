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