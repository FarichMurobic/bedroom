package com.bedroom.shared.exception;

/**
 * Dilempar ketika suatu sumber daya (entity) yang diharapkan ada
 * ternyata tidak ditemukan. Dipetakan ke HTTP 404 Not Found.
 */
public class SumberDayaTidakDitemukanException extends RuntimeException {

    public SumberDayaTidakDitemukanException(String pesan) {
        super(pesan);
    }
}