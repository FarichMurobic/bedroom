/*
 * Copyright (c) 2026 Farich Murobic
 *
 * This project is licensed under the MIT License.
 * See the LICENSE file in the project root for more information.
 */

package com.bedroom.application.security;

/**
 * Port untuk mengambil data pengguna yang sedang terautentikasi
 * dari konteks permintaan yang sedang berjalan.
 */
public interface PenyediaPenggunaTerautentikasi {

    /**
     * Mengambil data pengguna yang sedang terautentikasi.
     *
     * @return data pengguna terautentikasi
     */
    PenggunaTerautentikasi ambilPenggunaTerautentikasi();
}