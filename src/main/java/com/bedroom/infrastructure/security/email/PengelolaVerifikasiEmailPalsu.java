/*
 * Copyright (c) 2026 Farich Murobic
 *
 * This project is licensed under the MIT License.
 * See the LICENSE file in the project root for more information.
 */

package com.bedroom.infrastructure.security.email;

import com.bedroom.application.identitas.port.PengelolaVerifikasiEmail;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Implementasi palsu {@code PengelolaVerifikasiEmail} untuk kebutuhan pengujian.
 * Tidak mengirim email sungguhan, token disimpan di memory dengan nilai tetap.
 */
public final class PengelolaVerifikasiEmailPalsu implements PengelolaVerifikasiEmail {

    public static final String TOKEN_TETAP = "token-uji-coba";

    private final Map<String, String> tokenTersimpan = new ConcurrentHashMap<>();

    @Override
    public void buatDanKirim(String email) {
        tokenTersimpan.put(email, TOKEN_TETAP);
    }

    @Override
    public boolean verifikasi(String email, String token) {
        String tersimpan = tokenTersimpan.get(email);
        if (tersimpan == null || !tersimpan.equals(token)) {
            return false;
        }
        tokenTersimpan.remove(email);
        return true;
    }
}