/*
 * Copyright (c) 2026 Farich Murobic
 * Licensed under the MIT License.
 */
package com.bedroom.domain.identitas.valueobject;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("PengenalEksternal")
class PengenalEksternalTest {

    @Nested
    @DisplayName("saat nilai valid")
    class SaatNilaiValid {

        @Test
        @DisplayName("menerima pengenal yang tidak kosong")
        void menerimaPengenalTidakKosong() {
            PengenalEksternal pengenal = new PengenalEksternal("google-sub-1234567890");

            assertThat(pengenal.nilai()).isEqualTo("google-sub-1234567890");
        }

        @Test
        @DisplayName("memangkas spasi di awal dan akhir")
        void memangkasSpasiDiAwalDanAkhir() {
            PengenalEksternal pengenal = new PengenalEksternal("  google-sub-123  ");

            assertThat(pengenal.nilai()).isEqualTo("google-sub-123");
        }

        @Test
        @DisplayName("menerima panjang tepat 255 karakter (batas maksimal)")
        void menerimaPanjangTepatMaksimal() {
            String pengenalPanjang255 = "a".repeat(255);

            assertThat(new PengenalEksternal(pengenalPanjang255).nilai()).hasSize(255);
        }
    }

    @Nested
    @DisplayName("saat nilai tidak valid")
    class SaatNilaiTidakValid {

        @Test
        @DisplayName("melempar exception jika null")
        void melemparException_jikaNull() {
            assertThatThrownBy(() -> new PengenalEksternal(null))
                    .isInstanceOf(NullPointerException.class);
        }

        @Test
        @DisplayName("melempar exception jika kosong setelah dipangkas")
        void melemparException_jikaKosongSetelahDipangkas() {
            assertThatThrownBy(() -> new PengenalEksternal("   "))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("tidak boleh kosong");
        }

        @Test
        @DisplayName("melempar exception jika lebih dari 255 karakter")
        void melemparException_jikaLebihDari255Karakter() {
            String pengenalTerlaluPanjang = "a".repeat(256);

            assertThatThrownBy(() -> new PengenalEksternal(pengenalTerlaluPanjang))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("tidak boleh lebih dari 255 karakter");
        }
    }
}