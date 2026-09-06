/*
 * Copyright (c) 2026 Farich Murobic
 * Licensed under the MIT License.
 */
package com.bedroom.domain.identitas.valueobject;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("Email")
class EmailTest {

    @Nested
    @DisplayName("saat nilai valid")
    class SaatNilaiValid {

        @ParameterizedTest
        @DisplayName("menerima berbagai format email yang valid")
        @ValueSource(strings = {
                "budi@contoh.com",
                "budi.santoso@contoh.co.id",
                "budi+tag@contoh.com",
                "budi_santoso@sub.contoh.com",
                "b@contoh.io"
        })
        void menerimaFormatEmailValid(String emailValid) {
            Email email = new Email(emailValid);

            assertThat(email.nilai()).isEqualTo(emailValid.toLowerCase());
        }

        @Test
        @DisplayName("menormalisasi huruf besar menjadi huruf kecil")
        void menormalisasiHurufBesarMenjadiKecil() {
            Email email = new Email("Budi.Santoso@Contoh.COM");

            assertThat(email.nilai()).isEqualTo("budi.santoso@contoh.com");
        }

        @Test
        @DisplayName("memangkas spasi di awal dan akhir")
        void memangkasSpasiDiAwalDanAkhir() {
            Email email = new Email("  budi@contoh.com  ");

            assertThat(email.nilai()).isEqualTo("budi@contoh.com");
        }

        @Test
        @DisplayName("dua email dengan casing berbeda dianggap sama (equals)")
        void duaEmailDenganCasingBerbeda_dianggapSama() {
            Email email1 = new Email("Budi@Contoh.com");
            Email email2 = new Email("budi@contoh.com");

            assertThat(email1).isEqualTo(email2);
        }
    }

    @Nested
    @DisplayName("saat nilai tidak valid")
    class SaatNilaiTidakValid {

        @Test
        @DisplayName("melempar exception jika null")
        void melemparException_jikaNull() {
            assertThatThrownBy(() -> new Email(null))
                    .isInstanceOf(NullPointerException.class);
        }

        @Test
        @DisplayName("melempar exception jika kosong setelah dipangkas")
        void melemparException_jikaKosongSetelahDipangkas() {
            assertThatThrownBy(() -> new Email("   "))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("tidak boleh kosong");
        }

        @ParameterizedTest
        @DisplayName("melempar exception untuk format yang tidak valid")
        @ValueSource(strings = {
                "budisantoso",
                "budi@",
                "@contoh.com",
                "budi@contoh",
                "budi @contoh.com",
                "budi@@contoh.com",
                "budi@contoh..com"
        })
        void melemparException_untukFormatTidakValid(String emailTidakValid) {
            assertThatThrownBy(() -> new Email(emailTidakValid))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("Format email tidak valid");
        }

        @Test
        @DisplayName("melempar exception jika lebih dari 100 karakter")
        void melemparException_jikaLebihDari100Karakter() {
            String localPart = "a".repeat(90);
            String emailTerlaluPanjang = localPart + "@contoh.com";

            assertThatThrownBy(() -> new Email(emailTerlaluPanjang))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("tidak boleh lebih dari 100 karakter");
        }
    }
}