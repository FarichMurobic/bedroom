/*
 * Copyright (c) 2026 Farich Murobic
 *
 * This project is licensed under the MIT License.
 * See the LICENSE file in the project root for more information.
 */

package com.bedroom.domain.identitas.exception;

/**
 * Dilempar ketika suatu tindakan membutuhkan akun berstatus aktif,
 * tetapi akun pengguna yang bersangkutan belum atau tidak lagi berstatus aktif.
 */
public class AkunTidakAktifException extends RuntimeException {

    /**
     * Membuat exception dengan pesan yang menjelaskan alasan terjadinya kesalahan.
     *
     * @param pesan pesan yang menjelaskan alasan exception
     */
    public AkunTidakAktifException(String pesan) {
        super(pesan);
    }
}