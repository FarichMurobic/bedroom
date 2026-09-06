package com.bedroom.domain.identitas.valueobject;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("HashKataSandi")
class HashKataSandiTest {

    @Nested
    @DisplayName("saat nilai valid")
    class SaatNilaiValid {

        @Test
        @DisplayName("menerima hash yang tidak kosong")
        void menerimaHashTidakKosong() {
            HashKataSandi hashKataSandi = new HashKataSandi("$2a$10$contohHashBcrypt");

            assertThat(hashKataSandi.nilai()).isEqualTo("$2a$10$contohHashBcrypt");
        }
    }

    @Nested
    @DisplayName("saat nilai tidak valid")
    class SaatNilaiTidakValid {

        @Test
        @DisplayName("melempar exception jika null")
        void melemparException_jikaNull() {
            assertThatThrownBy(() -> new HashKataSandi(null))
                    .isInstanceOf(NullPointerException.class);
        }

        @Test
        @DisplayName("melempar exception jika kosong")
        void melemparException_jikaKosong() {
            assertThatThrownBy(() -> new HashKataSandi(""))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("tidak boleh kosong");
        }
    }

    @Nested
    @DisplayName("saat direpresentasikan sebagai string")
    class SaatDirepresentasikanSebagaiString {

        @Test
        @DisplayName("toString() tidak membocorkan nilai hash asli")
        void toString_tidakMembocorkanNilaiHashAsli() {
            HashKataSandi hashKataSandi = new HashKataSandi("$2a$10$rahasiaBanget");

            assertThat(hashKataSandi.toString())
                    .isEqualTo("HashKataSandi[PROTECTED]")
                    .doesNotContain("rahasiaBanget");
        }
    }
}