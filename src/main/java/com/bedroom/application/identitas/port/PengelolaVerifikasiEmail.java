package com.bedroom.application.identitas.port;

/**
 * Port untuk mengelola siklus hidup token verifikasi email: pembuatan,
 * pengiriman tautan konfirmasi, dan verifikasi terhadap token yang diklik pengguna.
 */
public interface PengelolaVerifikasiEmail {

    void buatDanKirim(String email);

    boolean verifikasi(String email, String token);
}