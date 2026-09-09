/*
 * Copyright (c) 2026 Farich Murobic
 *
 * This project is licensed under the MIT License.
 * See the LICENSE file in the project root for more information.
 */

package com.bedroom.application.identitas.port;

/**
 * Port untuk mengelola siklus hidup token verifikasi email: pembuatan,
 * pengiriman tautan konfirmasi, dan verifikasi terhadap token yang diklik pengguna.
 */
public interface PengelolaVerifikasiEmail {

    /**
     * Membuat token verifikasi dan mengirim tautan konfirmasi ke email.
     *
     * @param email alamat email penerima
     */
    void buatDanKirim(String email);

    /**
     * Memverifikasi token untuk alamat email tertentu.
     *
     * @param email alamat email yang diverifikasi
     * @param token token verifikasi
     * @return true jika token valid, false jika tidak
     */
    boolean verifikasi(String email, String token);
}