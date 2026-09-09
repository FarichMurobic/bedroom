/*
 * Copyright (c) 2026 Farich Murobic
 *
 * This project is licensed under the MIT License.
 * See the LICENSE file in the project root for more information.
 */

package com.bedroom.domain.identitas.valueobject;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;

/**
 * Value object yang merepresentasikan URL foto profil (avatar) seorang
 * {@code Pengguna}.
 *
 * <p>URL avatar dinormalisasi dengan menghapus spasi di awal dan akhir.
 * Nilainya tidak boleh kosong, memiliki panjang maksimal yang ditentukan
 * oleh domain, serta harus berupa URL absolut dengan skema {@code http}
 * atau {@code https}.</p>
 *
 * @param nilai URL foto profil yang akan direpresentasikan
 * @throws NullPointerException jika {@code nilai} bernilai {@code null}
 * @throws IllegalArgumentException jika URL kosong, melebihi panjang
 *         maksimal, atau memiliki format yang tidak valid
 */
public record UrlAvatar(String nilai) {

    private static final int PANJANG_MAKSIMAL = 500;

    public UrlAvatar {
        Objects.requireNonNull(nilai, "URL avatar tidak boleh kosong");
        nilai = nilai.trim();

        if (nilai.isBlank()) {
            throw new IllegalArgumentException("URL avatar tidak boleh kosong");
        }
        if (nilai.length() > PANJANG_MAKSIMAL) {
            throw new IllegalArgumentException(
                    "URL avatar tidak boleh lebih dari %d karakter".formatted(PANJANG_MAKSIMAL)
            );
        }
        if (!isUrlValid(nilai)) {
            throw new IllegalArgumentException("Format URL avatar tidak valid");
        }
    }

    /**
     * Memeriksa apakah nilai merupakan URL absolut dengan skema
     * {@code http} atau {@code https}.
     *
     * @param nilai URL yang akan diperiksa
     * @return {@code true} jika URL valid sesuai aturan domain,
     *         atau {@code false} jika tidak valid
     */
    private static boolean isUrlValid(String nilai) {
        try {
            URI uri = new URI(nilai);
            return uri.isAbsolute() && (uri.getScheme().equals("http") || uri.getScheme().equals("https"));
        } catch (URISyntaxException e) {
            return false;
        }
    }
}