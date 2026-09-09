package com.bedroom.domain.identitas.valueobject;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("NamaLengkap")
class NamaLengkapTest {

    @Nested
    @DisplayName("saat nilai valid")
    class SaatNilaiValid {

        @Test
        @DisplayName("menerima nama lengkap yang valid")
        void menerimaNamaLengkapValid() {
            assertThat(new NamaLengkap("Budi Santoso").nilai()).isEqualTo("Budi Santoso");
        }

        @Test
        @DisplayName("memangkas spasi di awal dan akhir")
        void memangkasSpasiDiAwalDanAkhir() {
            assertThat(new NamaLengkap("  Budi Santoso  ").nilai()).isEqualTo("Budi Santoso");
        }
    }

    @Nested
    @DisplayName("saat nilai tidak valid")
    class SaatNilaiTidakValid {

        @Test
        @DisplayName("melempar exception jika null")
        void melemparException_jikaNull() {
            assertThatThrownBy(() -> new NamaLengkap(null)).isInstanceOf(NullPointerException.class);
        }

        @Test
        @DisplayName("melempar exception jika kurang dari 2 karakter")
        void melemparException_jikaKurangDariMinimal() {
            assertThatThrownBy(() -> new NamaLengkap("A"))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("antara 2 sampai 100 karakter");
        }

        @Test
        @DisplayName("melempar exception jika lebih dari 100 karakter")
        void melemparException_jikaLebihDariMaksimal() {
            String terlaluPanjang = "a".repeat(101);
            assertThatThrownBy(() -> new NamaLengkap(terlaluPanjang))
                    .isInstanceOf(IllegalArgumentException.class);
        }
    }
}