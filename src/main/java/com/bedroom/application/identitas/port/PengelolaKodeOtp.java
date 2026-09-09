/*
 * Copyright (c) 2026 Farich Murobic
 *
 * This project is licensed under the MIT License.
 * See the LICENSE file in the project root for more information.
 */

package com.bedroom.application.identitas.port;

/**
 * Port untuk mengelola siklus hidup kode OTP, mulai dari pembuatan dan
 * pengiriman melalui SMS hingga verifikasi terhadap kode yang dimasukkan
 * oleh pengguna.
 */
public interface PengelolaKodeOtp {

    /**
     * Membuat kode OTP baru dan mengirimkannya ke nomor telepon pengguna.
     *
     * @param nomorTelepon nomor telepon tujuan pengiriman kode OTP
     */
    void buatDanKirim(String nomorTelepon);

    /**
     * Memverifikasi kode OTP yang diberikan terhadap kode yang terkait
     * dengan nomor telepon pengguna.
     *
     * @param nomorTelepon nomor telepon yang terkait dengan kode OTP
     * @param kodeOtp kode OTP yang akan diverifikasi
     * @return {@code true} jika kode OTP valid, atau {@code false} jika
     *         kode OTP tidak valid
     */
    boolean verifikasi(String nomorTelepon, String kodeOtp);
}