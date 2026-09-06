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

@DisplayName("NamaPengguna")
class NamaPenggunaTest {

    @Nested
    @DisplayName("saat nilai valid")
    class SaatNilaiValid {

        @Test
        @DisplayName("menerima nama pengguna dengan huruf, angka, titik, dan garis bawah")
        void menerimaNamaPenggunaValid() {
            NamaPengguna namaPengguna = new NamaPengguna("budi.santoso_92");

            assertThat(namaPengguna.nilai()).isEqualTo("budi.santoso_92");
        }

        @Test
        @DisplayName("menormalisasi huruf besar menjadi huruf kecil")
        void menormalisasiHurufBesarMenjadiKecil() {
            NamaPengguna namaPengguna = new NamaPengguna("BudiSantoso");

            assertThat(namaPengguna.nilai()).isEqualTo("budisantoso");
        }

        @Test
        @DisplayName("memangkas spasi di awal dan akhir")
        void memangkasSpasiDiAwalDanAkhir() {
            NamaPengguna namaPengguna = new NamaPengguna("  budisantoso  ");

            assertThat(namaPengguna.nilai()).isEqualTo("budisantoso");
        }

        @Test
        @DisplayName("menerima panjang tepat 3 karakter (batas minimal)")
        void menerimaPanjangTepatMinimal() {
            assertThat(new NamaPengguna("abc").nilai()).isEqualTo("abc");
        }

        @Test
        @DisplayName("menerima panjang tepat 30 karakter (batas maksimal)")
        void menerimaPanjangTepatMaksimal() {
            String namaPanjang30 = "a".repeat(30);
            assertThat(new NamaPengguna(namaPanjang30).nilai()).hasSize(30);
        }
    }

    @Nested
    @DisplayName("saat nilai tidak valid")
    class SaatNilaiTidakValid {

        @Test
        @DisplayName("melempar exception jika null")
        void melemparException_jikaNull() {
            assertThatThrownBy(() -> new NamaPengguna(null))
                    .isInstanceOf(NullPointerException.class);
        }

        @Test
        @DisplayName("melempar exception jika kosong setelah dipangkas")
        void melemparException_jikaKosongSetelahDipangkas() {
            assertThatThrownBy(() -> new NamaPengguna("   "))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("tidak boleh kosong");
        }

        @Test
        @DisplayName("melempar exception jika kurang dari 3 karakter")
        void melemparException_jikaKurangDariMinimal() {
            assertThatThrownBy(() -> new NamaPengguna("ab"))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("antara 3 sampai 30 karakter");
        }

        @Test
        @DisplayName("melempar exception jika lebih dari 30 karakter")
        void melemparException_jikaLebihDariMaksimal() {
            String namaTerlaluPanjang = "a".repeat(31);

            assertThatThrownBy(() -> new NamaPengguna(namaTerlaluPanjang))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("antara 3 sampai 30 karakter");
        }

        @ParameterizedTest
        @DisplayName("melempar exception untuk karakter yang tidak diizinkan")
        @ValueSource(strings = {"budi santoso", "budi@santoso", "budi#123", "budi!", "budi-santoso"})
        void melemparException_untukKarakterTidakDiizinkan(String namaTidakValid) {
            assertThatThrownBy(() -> new NamaPengguna(namaTidakValid))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("hanya boleh berisi huruf kecil, angka, titik, dan garis bawah");
        }

        @ParameterizedTest
        @DisplayName("melempar exception jika diawali atau diakhiri titik/garis bawah")
        @ValueSource(strings = {".budisantoso", "_budisantoso", "budisantoso.", "budisantoso_"})
        void melemparException_jikaDiawaliAtauDiakhiriTitikAtauGarisBawah(String namaTidakValid) {
            assertThatThrownBy(() -> new NamaPengguna(namaTidakValid))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("tidak boleh diawali atau diakhiri");
        }

        @ParameterizedTest
        @DisplayName("melempar exception jika ada titik/garis bawah berurutan")
        @ValueSource(strings = {"budi..santoso", "budi__santoso", "budi._santoso"})
        void melemparException_jikaAdaKarakterBerurutan(String namaTidakValid) {
            assertThatThrownBy(() -> new NamaPengguna(namaTidakValid))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("tidak boleh memiliki titik atau garis bawah yang berurutan");
        }
    }
}