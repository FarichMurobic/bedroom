/*
 * Copyright (c) 2026 Farich Murobic
 * Licensed under the MIT License.
 */
package com.bedroom.application.security;

/**
 * Port untuk mengambil data pengguna yang sedang terautentikasi
 * dari konteks permintaan yang sedang berjalan.
 */
public interface PenyediaPenggunaTerautentikasi {

    PenggunaTerautentikasi ambilPenggunaTerautentikasi();
}