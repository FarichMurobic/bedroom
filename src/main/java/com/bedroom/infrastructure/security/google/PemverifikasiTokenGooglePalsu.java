/*
 * Copyright (c) 2026 Farich Murobic
 *
 * This project is licensed under the MIT License.
 * See the LICENSE file in the project root for more information.
 */

package com.bedroom.infrastructure.security.google;

import com.bedroom.application.identitas.port.PemverifikasiTokenGoogle;

/**
 * Implementasi palsu {@code PemverifikasiTokenGoogle} untuk kebutuhan pengujian.
 * Tidak memverifikasi ke server Google sungguhan; data yang dikembalikan
 * ditentukan langsung oleh skenario uji melalui {@link #aturHasil}.
 */
public final class PemverifikasiTokenGooglePalsu implements PemverifikasiTokenGoogle {

    private DataPenggunaGoogle hasilBerikutnya;

    public void aturHasil(DataPenggunaGoogle dataPenggunaGoogle) {
        this.hasilBerikutnya = dataPenggunaGoogle;
    }

    @Override
    public DataPenggunaGoogle verifikasi(String idTokenGoogle) {
        if (hasilBerikutnya == null) {
            throw new IllegalStateException(
                    "Hasil palsu belum diatur, panggil aturHasil() terlebih dahulu di test"
            );
        }
        return hasilBerikutnya;
    }
}