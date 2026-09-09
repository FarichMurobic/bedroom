package com.bedroom.domain.karya.model;

import com.bedroom.domain.karya.valueobject.IsiBab;
import com.bedroom.domain.karya.valueobject.JudulBab;
import com.bedroom.domain.karya.valueobject.UrutanBab;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Bab")
class BabTest {

    private static final JudulBab JUDUL_BAB = new JudulBab("Bab Satu");
    private static final IsiBab ISI_BAB = new IsiBab("Ini adalah isi bab yang cukup panjang untuk melewati validasi panjang minimal lima puluh karakter.");
    private static final UrutanBab URUTAN_PERTAMA = new UrutanBab(1);

    @Nested
    @DisplayName("saat dibuat")
    class SaatDibuat {

        @Test
        @DisplayName("buat() menghasilkan bab dengan data yang sesuai")
        void buat_menghasilkanBabDenganDataSesuai() {
            Bab bab = Bab.buat(JUDUL_BAB, ISI_BAB, URUTAN_PERTAMA);

            assertThat(bab.judul()).isEqualTo(JUDUL_BAB);
            assertThat(bab.isi()).isEqualTo(ISI_BAB);
            assertThat(bab.urutan()).isEqualTo(URUTAN_PERTAMA);
            assertThat(bab.id()).isNotNull();
            assertThat(bab.dibuatPada()).isEqualTo(bab.diperbaruiPada());
        }
    }

    @Nested
    @DisplayName("saat diubah")
    class SaatDiubah {

        @Test
        @DisplayName("ubahJudul() mengganti judul bab")
        void ubahJudul_menggantiJudulBab() {
            Bab bab = Bab.buat(JUDUL_BAB, ISI_BAB, URUTAN_PERTAMA);
            JudulBab judulBaru = new JudulBab("Bab Satu (Revisi)");

            bab.ubahJudul(judulBaru);

            assertThat(bab.judul()).isEqualTo(judulBaru);
        }

        @Test
        @DisplayName("ubahIsi() mengganti isi bab")
        void ubahIsi_menggantiIsiBab() {
            Bab bab = Bab.buat(JUDUL_BAB, ISI_BAB, URUTAN_PERTAMA);
            IsiBab isiBaru = new IsiBab("Ini adalah isi bab baru yang juga cukup panjang untuk melewati validasi minimal.");

            bab.ubahIsi(isiBaru);

            assertThat(bab.isi()).isEqualTo(isiBaru);
        }

        @Test
        @DisplayName("ubahUrutan() mengganti urutan bab")
        void ubahUrutan_menggantiUrutanBab() {
            Bab bab = Bab.buat(JUDUL_BAB, ISI_BAB, URUTAN_PERTAMA);
            UrutanBab urutanBaru = new UrutanBab(2);

            bab.ubahUrutan(urutanBaru);

            assertThat(bab.urutan()).isEqualTo(urutanBaru);
        }
    }
}