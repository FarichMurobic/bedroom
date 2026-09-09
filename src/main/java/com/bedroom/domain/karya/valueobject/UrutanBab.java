/*
 * Copyright (c) 2026 Farich Murobic
 *
 * This project is licensed under the MIT License.
 * See the LICENSE file in the project root for more information.
 */

package com.bedroom.domain.karya.valueobject;

/**
 * Value object yang merepresentasikan urutan tampil sebuah {@code Bab}
 * di dalam sebuah {@code Karya}.
 *
 * <p>Urutan bab dimulai dari angka 1 dan tidak boleh bernilai kurang dari
 * angka tersebut. Value object ini juga menyediakan behavior untuk
 * mendapatkan urutan bab berikutnya.</p>
 *
 * @param nilai urutan bab dalam sebuah karya
 * @throws IllegalArgumentException jika {@code nilai} kurang dari 1
 */
public record UrutanBab(int nilai) {

    public UrutanBab {
        if (nilai < 1) {
            throw new IllegalArgumentException(
                    "Urutan bab harus dimulai dari 1"
            );
        }
    }

    /**
     * Membuat urutan bab berikutnya berdasarkan urutan saat ini.
     *
     * @return value object {@code UrutanBab} dengan nilai satu lebih besar
     *         dari urutan saat ini
     */
    public UrutanBab berikutnya() {
        return new UrutanBab(nilai + 1);
    }
}