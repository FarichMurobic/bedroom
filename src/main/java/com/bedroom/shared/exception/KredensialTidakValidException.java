package com.bedroom.shared.exception;

/**
 * Dilempar ketika kredensial yang diberikan (email/telepon, kata sandi,
 * kode OTP, token verifikasi) tidak valid. Dipetakan ke HTTP 401 Unauthorized.
 */
public class KredensialTidakValidException extends RuntimeException {

    public KredensialTidakValidException(String pesan) {
        super(pesan);
    }
}