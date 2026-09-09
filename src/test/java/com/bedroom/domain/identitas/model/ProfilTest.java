package com.bedroom.domain.identitas.model;

import com.bedroom.domain.identitas.enums.JenisKelamin;
import com.bedroom.domain.identitas.valueobject.IdPengguna;
import com.bedroom.domain.identitas.valueobject.NamaLengkap;
import com.bedroom.domain.identitas.valueobject.TanggalLahir;
import com.bedroom.domain.identitas.valueobject.UrlAvatar;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Profil")
class ProfilTest {

    private static final IdPengguna ID_PENGGUNA = IdPengguna.baru();

    @Nested
    @DisplayName("saat dibuat")
    class SaatDibuat {

        @Test
        @DisplayName("buatKosong() menghasilkan profil dengan seluruh field kosong")
        void buatKosong_menghasilkanProfilDenganFieldKosong() {
            Profil profil = Profil.buatKosong(ID_PENGGUNA);

            assertThat(profil.idPengguna()).isEqualTo(ID_PENGGUNA);
            assertThat(profil.namaLengkap()).isNull();
            assertThat(profil.tanggalLahir()).isNull();
            assertThat(profil.jenisKelamin()).isNull();
            assertThat(profil.urlAvatar()).isNull();
            assertThat(profil.diperbaruiPada()).isNotNull();
        }
    }

    @Nested
    @DisplayName("saat melengkapi data")
    class SaatMelengkapiData {

        @Test
        @DisplayName("gantiNamaLengkap() mengisi nama lengkap")
        void gantiNamaLengkap_mengisiNamaLengkap() {
            Profil profil = Profil.buatKosong(ID_PENGGUNA);
            NamaLengkap namaLengkap = new NamaLengkap("Budi Santoso");

            profil.gantiNamaLengkap(namaLengkap);

            assertThat(profil.namaLengkap()).isEqualTo(namaLengkap);
        }

        @Test
        @DisplayName("gantiTanggalLahir() mengisi tanggal lahir")
        void gantiTanggalLahir_mengisiTanggalLahir() {
            Profil profil = Profil.buatKosong(ID_PENGGUNA);
            TanggalLahir tanggalLahir = new TanggalLahir(LocalDate.of(2000, 1, 1));

            profil.gantiTanggalLahir(tanggalLahir);

            assertThat(profil.tanggalLahir()).isEqualTo(tanggalLahir);
        }

        @Test
        @DisplayName("gantiJenisKelamin() mengisi jenis kelamin")
        void gantiJenisKelamin_mengisiJenisKelamin() {
            Profil profil = Profil.buatKosong(ID_PENGGUNA);

            profil.gantiJenisKelamin(JenisKelamin.LAKI_LAKI);

            assertThat(profil.jenisKelamin()).isEqualTo(JenisKelamin.LAKI_LAKI);
        }

        @Test
        @DisplayName("gantiUrlAvatar() mengisi url avatar")
        void gantiUrlAvatar_mengisiUrlAvatar() {
            Profil profil = Profil.buatKosong(ID_PENGGUNA);
            UrlAvatar urlAvatar = new UrlAvatar("https://contoh.com/avatar.jpg");

            profil.gantiUrlAvatar(urlAvatar);

            assertThat(profil.urlAvatar()).isEqualTo(urlAvatar);
        }

        @Test
        @DisplayName("gantiNamaLengkap() dengan null mengosongkan kembali nama lengkap")
        void gantiNamaLengkap_denganNull_mengosongkanKembali() {
            Profil profil = Profil.buatKosong(ID_PENGGUNA);
            profil.gantiNamaLengkap(new NamaLengkap("Budi Santoso"));

            profil.gantiNamaLengkap(null);

            assertThat(profil.namaLengkap()).isNull();
        }
    }
}