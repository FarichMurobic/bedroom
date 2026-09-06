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

@DisplayName("NomorTelepon")
class NomorTeleponTest {

    @Nested
    @DisplayName("saat nilai valid")
    class SaatNilaiValid {

        @ParameterizedTest
        @DisplayName("menerima berbagai format E.164 yang valid")
        @ValueSource(strings = {
                "+6281234567890",
                "+12025550123",
                "+447911123456",
                "+862345678"
        })
        void menerimaFormatE164Valid(String nomorValid) {
            NomorTelepon nomorTelepon = new NomorTelepon(nomorValid);

            assertThat(nomorTelepon.nilai()).isEqualTo(nomorValid);
        }

        @Test
        @DisplayName("memangkas spasi di awal dan akhir")
        void memangkasSpasiDiAwalDanAkhir() {
            NomorTelepon nomorTelepon = new NomorTelepon("  +6281234567890  ");

            assertThat(nomorTelepon.nilai()).isEqualTo("+6281234567890");
        }
    }

    @Nested
    @DisplayName("saat nilai tidak valid")
    class SaatNilaiTidakValid {

        @Test
        @DisplayName("melempar exception jika null")
        void melemparException_jikaNull() {
            assertThatThrownBy(() -> new NomorTelepon(null))
                    .isInstanceOf(NullPointerException.class);
        }

        @Test
        @DisplayName("melempar exception jika kosong setelah dipangkas")
        void melemparException_jikaKosongSetelahDipangkas() {
            assertThatThrownBy(() -> new NomorTelepon("   "))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("tidak boleh kosong");
        }

        @ParameterizedTest
        @DisplayName("melempar exception untuk format yang tidak valid")
        @ValueSource(strings = {
                "081234567890",       // tanpa tanda +
                "+081234567890",      // diawali 0 setelah +
                "+62",                // terlalu pendek
                "+628123456789012345",// terlalu panjang
                "6281234567890",      // tanpa tanda +
                "+62 812 3456 7890",  // ada spasi di tengah
                "+62-812-3456-7890"   // ada tanda hubung
        })
        void melemparException_untukFormatTidakValid(String nomorTidakValid) {
            assertThatThrownBy(() -> new NomorTelepon(nomorTidakValid))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("format internasional E.164");
        }
    }
}