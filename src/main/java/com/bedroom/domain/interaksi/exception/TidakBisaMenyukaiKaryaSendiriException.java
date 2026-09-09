/*
 * Copyright (c) 2026 Farich Murobic
 *
 * This project is licensed under the MIT License.
 * See the LICENSE file in the project root for more information.
 */

package com.bedroom.domain.interaksi.exception;

/**
 * Dilempar ketika seorang pengguna mencoba menyukai karya miliknya sendiri.
 *
 * <p>Exception ini merepresentasikan pelanggaran aturan domain bahwa
 * pengguna tidak dapat memberikan suka kepada karya yang dimilikinya.</p>
 */
public class TidakBisaMenyukaiKaryaSendiriException extends RuntimeException {

    /**
     * Membuat exception dengan pesan yang menjelaskan alasan terjadinya
     * pelanggaran aturan domain.
     *
     * @param pesan pesan yang menjelaskan alasan exception
     */
    public TidakBisaMenyukaiKaryaSendiriException(String pesan) {
        super(pesan);
    }
}