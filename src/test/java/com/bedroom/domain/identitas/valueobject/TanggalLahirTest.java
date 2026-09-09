package com.bedroom.domain.identitas.valueobject;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("TanggalLahir")
class TanggalLahirTest {

    @Nested
    @DisplayName("saat nilai valid")
    class SaatNilaiValid {

        @Test
        @DisplayName("menerima tanggal lahir yang membuat usia tepat 13 tahun")
        void menerimaTanggalLahir_usiaTepat13Tahun() {
            LocalDate tanggal = LocalDate.now().minusYears(13);
            assertThat(new TanggalLahir(tanggal).nilai()).isEqualTo(tanggal);
        }

        @Test
        @DisplayName("menerima tanggal lahir yang jauh di masa lalu")
        void menerimaTanggalLahirJauhDiMasaLalu() {
            LocalDate tanggal = LocalDate.of(1990, 5, 20);
            assertThat(new TanggalLahir(tanggal).nilai()).isEqualTo(tanggal);
        }
    }

    @Nested
    @DisplayName("saat nilai tidak valid")
    class SaatNilaiTidakValid {

        @Test
        @DisplayName("melempar exception jika null")
        void melemparException_jikaNull() {
            assertThatThrownBy(() -> new TanggalLahir(null)).isInstanceOf(NullPointerException.class);
        }

        @Test
        @DisplayName("melempar exception jika tanggal di masa depan")
        void melemparException_jikaDiMasaDepan() {
            LocalDate besok = LocalDate.now().plusDays(1);
            assertThatThrownBy(() -> new TanggalLahir(besok))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("tidak boleh di masa depan");
        }

        @Test
        @DisplayName("melempar exception jika usia kurang dari 13 tahun")
        void melemparException_jikaUsiaKurangDari13Tahun() {
            LocalDate tanggal = LocalDate.now().minusYears(12);
            assertThatThrownBy(() -> new TanggalLahir(tanggal))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("Usia minimal 13 tahun");
        }
    }
}