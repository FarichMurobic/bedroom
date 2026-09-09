package com.bedroom.domain.karya.model;

import com.bedroom.domain.karya.valueobject.NamaGenre;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Genre")
class GenreTest {

    @Nested
    @DisplayName("saat dibuat")
    class SaatDibuat {

        @Test
        @DisplayName("buat() menghasilkan genre dengan nama yang sesuai")
        void buat_menghasilkanGenreDenganNamaSesuai() {
            NamaGenre namaGenre = new NamaGenre("Romance");

            Genre genre = Genre.buat(namaGenre);

            assertThat(genre.nama()).isEqualTo(namaGenre);
            assertThat(genre.id()).isNotNull();
            assertThat(genre.dibuatPada()).isNotNull();
        }
    }

    @Nested
    @DisplayName("saat mengganti nama")
    class SaatGantiNama {

        @Test
        @DisplayName("gantiNama() mengubah nama genre")
        void gantiNama_mengubahNamaGenre() {
            Genre genre = Genre.buat(new NamaGenre("Romance"));
            NamaGenre namaBaru = new NamaGenre("Horror");

            genre.gantiNama(namaBaru);

            assertThat(genre.nama()).isEqualTo(namaBaru);
        }
    }
}