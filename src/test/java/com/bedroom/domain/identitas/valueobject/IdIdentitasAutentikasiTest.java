/*
 * Copyright (c) 2026 Farich Murobic
 * Licensed under the MIT License.
 */
package com.bedroom.domain.identitas.valueobject;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("IdIdentitasAutentikasi")
class IdIdentitasAutentikasiTest {

    @Nested
    @DisplayName("saat dibuat")
    class SaatDibuat {

        @Test
        @DisplayName("baru() menghasilkan id unik setiap kali dipanggil")
        void baru_menghasilkanIdUnikSetiapKaliDipanggil() {
            IdIdentitasAutentikasi id1 = IdIdentitasAutentikasi.baru();
            IdIdentitasAutentikasi id2 = IdIdentitasAutentikasi.baru();

            assertThat(id1).isNotEqualTo(id2);
        }

        @Test
        @DisplayName("dari(UUID) membungkus UUID yang sudah ada")
        void dariUuid_membungkusUuidYangSudahAda() {
            UUID uuid = UUID.randomUUID();

            IdIdentitasAutentikasi id = IdIdentitasAutentikasi.dari(uuid);

            assertThat(id.nilai()).isEqualTo(uuid);
        }

        @Test
        @DisplayName("dari(String) mem-parsing string UUID yang valid")
        void dariString_memParsingStringUuidValid() {
            UUID uuid = UUID.randomUUID();

            IdIdentitasAutentikasi id = IdIdentitasAutentikasi.dari(uuid.toString());

            assertThat(id.nilai()).isEqualTo(uuid);
        }
    }

    @Nested
    @DisplayName("saat nilai tidak valid")
    class SaatNilaiTidakValid {

        @Test
        @DisplayName("konstruktor melempar exception jika UUID null")
        void konstruktor_melemparException_jikaUuidNull() {
            assertThatThrownBy(() -> new IdIdentitasAutentikasi(null))
                    .isInstanceOf(NullPointerException.class);
        }

        @Test
        @DisplayName("dari(String) melempar exception jika string kosong")
        void dariString_melemparException_jikaKosong() {
            assertThatThrownBy(() -> IdIdentitasAutentikasi.dari("   "))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("tidak boleh kosong");
        }

        @Test
        @DisplayName("dari(String) melempar exception jika format bukan UUID valid")
        void dariString_melemparException_jikaFormatTidakValid() {
            assertThatThrownBy(() -> IdIdentitasAutentikasi.dari("bukan-uuid-yang-valid"))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("Format id identitas autentikasi tidak valid");
        }
    }
}