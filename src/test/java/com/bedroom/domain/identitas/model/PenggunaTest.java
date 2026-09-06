package com.bedroom.domain.identitas.model;

import com.bedroom.domain.identitas.enums.Peran;
import com.bedroom.domain.identitas.enums.StatusPengguna;
import com.bedroom.domain.identitas.exception.AkunTidakAktifException;
import com.bedroom.domain.identitas.valueobject.NamaPengguna;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("Pengguna")
class PenggunaTest {

    private static final NamaPengguna NAMA_PENGGUNA = new NamaPengguna("janera");

    @Nested
    @DisplayName("saat mendaftar")
    class SaatMendaftar {

        @Test
        @DisplayName("daftarMenungguVerifikasi() menghasilkan status MENUNGGU_VERIFIKASI dan peran PENGGUNA")
        void daftarMenungguVerifikasi_menghasilkanStatusMenungguVerifikasi() {
            Pengguna pengguna = Pengguna.daftarMenungguVerifikasi(NAMA_PENGGUNA);

            assertThat(pengguna.status()).isEqualTo(StatusPengguna.MENUNGGU_VERIFIKASI);
            assertThat(pengguna.perans()).containsExactly(Peran.PENGGUNA);
            assertThat(pengguna.namaPengguna()).isEqualTo(NAMA_PENGGUNA);
            assertThat(pengguna.id()).isNotNull();
            assertThat(pengguna.dibuatPada()).isEqualTo(pengguna.diperbaruiPada());
        }

        @Test
        @DisplayName("daftarTerverifikasi() menghasilkan status AKTIF dan peran PENGGUNA")
        void daftarTerverifikasi_menghasilkanStatusAktif() {
            Pengguna pengguna = Pengguna.daftarTerverifikasi(NAMA_PENGGUNA);

            assertThat(pengguna.status()).isEqualTo(StatusPengguna.AKTIF);
            assertThat(pengguna.perans()).containsExactly(Peran.PENGGUNA);
        }
    }

    @Nested
    @DisplayName("saat verifikasi")
    class SaatVerifikasi {

        @Test
        @DisplayName("verifikasi() mengubah status MENUNGGU_VERIFIKASI menjadi AKTIF")
        void verifikasi_mengubahStatusMenjadiAktif() {
            Pengguna pengguna = Pengguna.daftarMenungguVerifikasi(NAMA_PENGGUNA);

            pengguna.verifikasi();

            assertThat(pengguna.status()).isEqualTo(StatusPengguna.AKTIF);
        }

        @Test
        @DisplayName("verifikasi() melempar exception jika status bukan MENUNGGU_VERIFIKASI")
        void verifikasi_melemparException_jikaStatusBukanMenungguVerifikasi() {
            Pengguna pengguna = Pengguna.daftarTerverifikasi(NAMA_PENGGUNA);

            assertThatThrownBy(pengguna::verifikasi)
                    .isInstanceOf(IllegalStateException.class)
                    .hasMessageContaining("menunggu verifikasi");
        }
    }

    @Nested
    @DisplayName("saat mengganti nama pengguna")
    class SaatGantiNamaPengguna {

        @Test
        @DisplayName("gantiNamaPengguna() berhasil jika status AKTIF")
        void gantiNamaPengguna_berhasil_jikaStatusAktif() {
            Pengguna pengguna = Pengguna.daftarTerverifikasi(NAMA_PENGGUNA);
            NamaPengguna namaBaru = new NamaPengguna("janera2");

            pengguna.gantiNamaPengguna(namaBaru);

            assertThat(pengguna.namaPengguna()).isEqualTo(namaBaru);
        }

        @Test
        @DisplayName("gantiNamaPengguna() melempar exception jika status bukan AKTIF")
        void gantiNamaPengguna_melemparException_jikaStatusBukanAktif() {
            Pengguna pengguna = Pengguna.daftarMenungguVerifikasi(NAMA_PENGGUNA);
            NamaPengguna namaBaru = new NamaPengguna("budisantoso2");

            assertThatThrownBy(() -> pengguna.gantiNamaPengguna(namaBaru))
                    .isInstanceOf(AkunTidakAktifException.class);
        }
    }

    @Nested
    @DisplayName("saat mengubah status")
    class SaatMengubahStatus {

        @Test
        @DisplayName("nonaktifkan() mengubah status menjadi TIDAK_AKTIF")
        void nonaktifkan_mengubahStatusMenjadiTidakAktif() {
            Pengguna pengguna = Pengguna.daftarTerverifikasi(NAMA_PENGGUNA);
            pengguna.nonaktifkan();

            assertThat(pengguna.status()).isEqualTo(StatusPengguna.TIDAK_AKTIF);
        }

        @Test
        @DisplayName("aktifkan() dapat memulihkan status TIDAK_AKTIF menjadi AKTIF")
        void aktifkan_memulihkanStatusTidakAktif() {
            Pengguna pengguna = Pengguna.daftarTerverifikasi(NAMA_PENGGUNA);
            pengguna.nonaktifkan();
            pengguna.aktifkan();

            assertThat(pengguna.status()).isEqualTo(StatusPengguna.AKTIF);
        }

        @Test
        @DisplayName("aktifkan() dapat memulihkan status DITANGGUHKAN menjadi AKTIF")
        void aktifkan_memulihkanStatusDitangguhkan() {
            Pengguna pengguna = Pengguna.daftarTerverifikasi(NAMA_PENGGUNA);
            pengguna.tangguhkan();
            pengguna.aktifkan();

            assertThat(pengguna.status()).isEqualTo(StatusPengguna.AKTIF);
        }

        @Test
        @DisplayName("aktifkan() melempar exception jika status DIBLOKIR")
        void aktifkan_melemparException_jikaStatusDiblokir() {
            Pengguna pengguna = Pengguna.daftarTerverifikasi(NAMA_PENGGUNA);
            pengguna.blokir();

            assertThatThrownBy(pengguna::aktifkan)
                    .isInstanceOf(IllegalStateException.class)
                    .hasMessageContaining("diblokir");

            assertThat(pengguna.status()).isEqualTo(StatusPengguna.DIBLOKIR);
        }

        @Test
        @DisplayName("tangguhkan() mengubah status menjadi DITANGGUHKAN")
        void tangguhkan_mengubahStatusMenjadiDitangguhkan() {
            Pengguna pengguna = Pengguna.daftarTerverifikasi(NAMA_PENGGUNA);
            pengguna.tangguhkan();

            assertThat(pengguna.status()).isEqualTo(StatusPengguna.DITANGGUHKAN);
        }

        @Test
        @DisplayName("blokir() mengubah status menjadi DIBLOKIR")
        void blokir_mengubahStatusMenjadiDiblokir() {
            Pengguna pengguna = Pengguna.daftarTerverifikasi(NAMA_PENGGUNA);
            pengguna.blokir();

            assertThat(pengguna.status()).isEqualTo(StatusPengguna.DIBLOKIR);
        }

        @Test
        @DisplayName("mengubah ke status yang sama tidak memperbarui diperbaruiPada")
        void ubahStatus_keStatusYangSama_tidakMemperbaruiTimestamp() {
            Pengguna pengguna = Pengguna.daftarTerverifikasi(NAMA_PENGGUNA);
            var diperbaruiPadaSebelum = pengguna.diperbaruiPada();
            pengguna.aktifkan(); // sudah AKTIF, jadi ini no-op

            assertThat(pengguna.diperbaruiPada()).isEqualTo(diperbaruiPadaSebelum);
        }
    }

    @Nested
    @DisplayName("saat mengelola peran")
    class SaatMengelolaPeran {

        @Test
        @DisplayName("tambahPeran() menambahkan peran baru")
        void tambahPeran_menambahkanPeranBaru() {
            Pengguna pengguna = Pengguna.daftarTerverifikasi(NAMA_PENGGUNA);
            pengguna.tambahPeran(Peran.MODERATOR);

            assertThat(pengguna.perans()).containsExactlyInAnyOrder(Peran.PENGGUNA, Peran.MODERATOR);
        }

        @Test
        @DisplayName("hapusPeran() mencabut peran yang bukan satu-satunya")
        void hapusPeran_mencabutPeranYangBukanSatuSatunya() {
            Pengguna pengguna = Pengguna.daftarTerverifikasi(NAMA_PENGGUNA);
            pengguna.tambahPeran(Peran.MODERATOR);
            pengguna.hapusPeran(Peran.MODERATOR);

            assertThat(pengguna.perans()).containsExactly(Peran.PENGGUNA);
        }

        @Test
        @DisplayName("hapusPeran() melempar exception jika itu satu-satunya")
        void hapusPeran_melemparException_jikaSatuSatunya() {
            Pengguna pengguna = Pengguna.daftarTerverifikasi(NAMA_PENGGUNA);

            assertThatThrownBy(() -> pengguna.hapusPeran(Peran.PENGGUNA))
                    .isInstanceOf(IllegalStateException.class)
                    .hasMessageContaining("minimal satu peran");

            assertThat(pengguna.perans()).containsExactly(Peran.PENGGUNA);
        }

        @Test
        @DisplayName("perans() mengembalikan set yang tidak dapat diubah dari luar")
        void perans_mengembalikanSetTidakDapatDiubah() {
            Pengguna pengguna = Pengguna.daftarTerverifikasi(NAMA_PENGGUNA);

            assertThatThrownBy(() -> pengguna.perans().add(Peran.ADMIN))
                    .isInstanceOf(UnsupportedOperationException.class);
        }
    }

    @Nested
    @DisplayName("saat memastikan status aktif")
    class SaatMemastikanAktif {

        @Test
        @DisplayName("pastikanAktif() tidak melempar exception jika status AKTIF")
        void pastikanAktif_tidakMelemparException_jikaAktif() {
            Pengguna pengguna = Pengguna.daftarTerverifikasi(NAMA_PENGGUNA);

            assertThat(pengguna).satisfies(p -> pengguna.pastikanAktif());
        }

        @Test
        @DisplayName("pastikanAktif() melempar exception jika status bukan AKTIF")
        void pastikanAktif_melemparException_jikaBukanAktif() {
            Pengguna pengguna = Pengguna.daftarMenungguVerifikasi(NAMA_PENGGUNA);

            assertThatThrownBy(pengguna::pastikanAktif)
                    .isInstanceOf(AkunTidakAktifException.class);
        }
    }
}