package com.bedroom.application.identitas.port;

/**
 * Port untuk memverifikasi keabsahan ID Token yang diterbitkan oleh Google
 * Sign-In, sekaligus mengekstrak data identitas pengguna dari dalamnya.
 * Implementasi konkret berada di lapisan infrastruktur.
 */
public interface PemverifikasiTokenGoogle {

    /**
     * Memverifikasi signature dan klaim pada ID Token.
     *
     * @throws IllegalArgumentException jika token tidak valid, kedaluwarsa,
     *         atau tidak diterbitkan untuk aplikasi Bedroom.
     */
    DataPenggunaGoogle verifikasi(String idTokenGoogle);

    /**
     * Data identitas pengguna yang diekstrak dari ID Token Google
     * setelah proses verifikasi berhasil.
     */
    record DataPenggunaGoogle(
            String email,
            String pengenalEksternal,
            String namaTampilan
    ) {
    }
}