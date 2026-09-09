/*
 * Copyright (c) 2026 Farich Murobic
 *
 * This project is licensed under the MIT License.
 * See the LICENSE file in the project root for more information.
 */

package com.bedroom.domain.identitas.valueobject;

import java.time.LocalDate;
import java.time.Period;
import java.util.Objects;

/**
 * Value object yang merepresentasikan tanggal lahir seorang pengguna.
 *
 * <p>Tanggal lahir tidak boleh berada di masa depan dan pengguna harus
 * memenuhi batas usia minimal yang ditentukan oleh domain Bedroom.</p>
 *
 * @param nilai tanggal lahir pengguna
 * @throws NullPointerException jika {@code nilai} bernilai {@code null}
 * @throws IllegalArgumentException jika tanggal lahir berada di masa depan
 *         atau usia pengguna belum mencapai batas minimal
 */
public record TanggalLahir(LocalDate nilai) {

    private static final int USIA_MINIMAL = 13;

    public TanggalLahir {
        Objects.requireNonNull(nilai, "Tanggal lahir tidak boleh kosong");

        if (nilai.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Tanggal lahir tidak boleh di masa depan");
        }

        int usia = Period.between(nilai, LocalDate.now()).getYears();

        if (usia < USIA_MINIMAL) {
            throw new IllegalArgumentException(
                    "Usia minimal %d tahun untuk menggunakan Bedroom".formatted(USIA_MINIMAL)
            );
        }
    }
}