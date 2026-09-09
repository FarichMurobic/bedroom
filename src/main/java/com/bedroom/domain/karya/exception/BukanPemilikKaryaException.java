/*
 * Copyright (c) 2026 Farich Murobic
 *
 * This project is licensed under the MIT License.
 * See the LICENSE file in the project root for more information.
 */

package com.bedroom.domain.karya.exception;

/**
 * Dilempar ketika seorang pengguna mencoba mengubah karya yang bukan miliknya.
 *
 * <p>Exception ini merepresentasikan pelanggaran aturan domain bahwa
 * hanya pemilik karya yang berhak melakukan tindakan tertentu terhadap
 * karya tersebut.</p>
 */
public class BukanPemilikKaryaException extends RuntimeException {

    /**
     * Membuat exception dengan pesan yang menjelaskan alasan terjadinya
     * pelanggaran aturan kepemilikan karya.
     *
     * @param pesan pesan yang menjelaskan alasan exception
     */
    public BukanPemilikKaryaException(String pesan) {
        super(pesan);
    }
}