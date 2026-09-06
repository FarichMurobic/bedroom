package com.bedroom.application.identitas.port;

/**
 * Port untuk mengelola siklus hidup kode OTP: pembuatan, pengiriman
 * melalui SMS, dan verifikasi terhadap kode yang dimasukkan pengguna.
 */
public interface PengelolaKodeOtp {

    void buatDanKirim(String nomorTelepon);

    boolean verifikasi(String nomorTelepon, String kodeOtp);
}