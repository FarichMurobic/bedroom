package com.bedroom.application.security;

/**
 * Port untuk mengambil data pengguna yang sedang terautentikasi
 * dari konteks permintaan yang sedang berjalan.
 */
public interface PenyediaPenggunaTerautentikasi {

    PenggunaTerautentikasi ambilPenggunaTerautentikasi();
}