/*
 * Copyright (c) 2026 Farich Murobic
 *
 * This project is licensed under the MIT License.
 * See the LICENSE file in the project root for more information.
 */

package com.bedroom.application.identitas.port;

/**
 * Port untuk memverifikasi keabsahan ID Token yang diterbitkan oleh Google
 * Sign-In sekaligus mengekstrak data identitas pengguna dari dalamnya.
 *
 * <p>Interface ini mendefinisikan kontrak yang dibutuhkan lapisan aplikasi
 * tanpa bergantung pada mekanisme atau library Google tertentu. Implementasi
 * konkret disediakan oleh lapisan infrastruktur.</p>
 */
public interface PemverifikasiTokenGoogle {

    /**
     * Memverifikasi signature dan klaim pada ID Token Google.
     *
     * @param idTokenGoogle ID Token Google yang akan diverifikasi
     * @return data identitas pengguna yang berhasil diekstrak dari token
     * @throws IllegalArgumentException jika token tidak valid, kedaluwarsa,
     *         atau tidak diterbitkan untuk aplikasi Bedroom
     */
    DataPenggunaGoogle verifikasi(String idTokenGoogle);

    /**
     * Data identitas pengguna yang diekstrak dari ID Token Google
     * setelah proses verifikasi berhasil.
     *
     * @param email alamat email pengguna yang diperoleh dari token
     * @param pengenalEksternal pengenal unik pengguna pada Google
     * @param namaTampilan nama tampilan pengguna yang diperoleh dari token
     */
    record DataPenggunaGoogle(
            String email,
            String pengenalEksternal,
            String namaTampilan
    ) {
    }
}