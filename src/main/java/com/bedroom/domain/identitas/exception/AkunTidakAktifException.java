package com.bedroom.domain.identitas.exception;

/**
 * Dilempar ketika suatu tindakan membutuhkan akun berstatus aktif,
 * namun pengguna yang bersangkutan belum atau tidak lagi berstatus aktif.
 */
public class AkunTidakAktifException extends RuntimeException {

    public AkunTidakAktifException(String pesan) {
        super(pesan);
    }
}