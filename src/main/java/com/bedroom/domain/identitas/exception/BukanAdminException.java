/*
 * Copyright (c) 2026 Farich Murobic
 *
 * This project is licensed under the MIT License.
 * See the LICENSE file in the project root for more information.
 */

package com.bedroom.domain.identitas.exception;

/**
 * Dilempar ketika suatu tindakan hanya boleh dilakukan oleh pengguna
 * yang berperan sebagai {@code ADMIN}, tetapi pengguna yang bersangkutan
 * tidak memiliki peran tersebut.
 */
public class BukanAdminException extends RuntimeException {

    /**
     * Membuat exception dengan pesan yang menjelaskan alasan terjadinya kesalahan.
     *
     * @param pesan pesan yang menjelaskan alasan exception
     */
    public BukanAdminException(String pesan) {
        super(pesan);
    }
}