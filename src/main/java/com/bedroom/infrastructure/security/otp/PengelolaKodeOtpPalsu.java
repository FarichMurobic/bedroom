/*
 * Copyright (c) 2026 Farich Murobic
 *
 * This project is licensed under the MIT License.
 * See the LICENSE file in the project root for more information.
 */

package com.bedroom.infrastructure.security.otp;

import com.bedroom.application.identitas.port.PengelolaKodeOtp;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Implementasi palsu {@code PengelolaKodeOtp} untuk kebutuhan pengujian.
 * Tidak mengirim SMS sungguhan, kode disimpan di memory dengan nilai tetap
 * agar mudah diprediksi dalam skenario uji.
 */
public final class PengelolaKodeOtpPalsu implements PengelolaKodeOtp {

    public static final String KODE_TETAP = "123456";

    private final Map<String, String> kodeTersimpan = new ConcurrentHashMap<>();

    @Override
    public void buatDanKirim(String nomorTelepon) {
        kodeTersimpan.put(nomorTelepon, KODE_TETAP);
    }

    @Override
    public boolean verifikasi(String nomorTelepon, String kodeOtp) {
        String tersimpan = kodeTersimpan.get(nomorTelepon);
        if (tersimpan == null || !tersimpan.equals(kodeOtp)) {
            return false;
        }
        kodeTersimpan.remove(nomorTelepon);
        return true;
    }
}