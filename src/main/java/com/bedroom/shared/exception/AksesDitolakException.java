/*
 * Copyright (c) 2026 Farich Murobic
 * Licensed under the MIT License.
 */
package com.bedroom.shared.exception;

/**
 * Dilempar ketika pengguna diketahui secara sah namun tidak diizinkan
 * melakukan suatu tindakan karena status akunnya (mis. ditangguhkan,
 * diblokir, atau belum diverifikasi). Dipetakan ke HTTP 403 Forbidden.
 */
public class AksesDitolakException extends RuntimeException {

    public AksesDitolakException(String pesan) {
        super(pesan);
    }
}