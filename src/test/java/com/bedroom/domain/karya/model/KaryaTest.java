package com.bedroom.domain.karya.model;

import com.bedroom.domain.identitas.valueobject.IdPengguna;
import com.bedroom.domain.karya.enums.StatusKarya;
import com.bedroom.domain.karya.exception.BukanPemilikKaryaException;
import com.bedroom.domain.karya.valueobject.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

@DisplayName("Karya")
class KaryaTest {

    private static final IdPengguna ID_PENULIS = IdPengguna.baru();
    private static final JudulKarya JUDUL = new JudulKarya("Rumah di Ujung Senja");
    private static final Sinopsis SINOPSIS = new Sinopsis("Sebuah kisah tentang keluarga yang tinggal di rumah tua di ujung desa.");
    private static final JudulBab JUDUL_BAB = new JudulBab("Bab Satu");
    private static final IsiBab ISI_BAB = new IsiBab("Ini adalah isi bab yang cukup panjang untuk melewati validasi panjang minimal lima puluh karakter.");

    @Nested
    @DisplayName("saat mulai ditulis")
    class SaatMulaiDitulis {

        @Test
        @DisplayName("mulaiTulis() menghasilkan karya berstatus DRAFT tanpa bab dan genre")
        void mulaiTulis_menghasilkanKaryaBerstatusDraft() {
            Karya karya = Karya.mulaiTulis(ID_PENULIS, JUDUL, SINOPSIS);

            assertThat(karya.status()).isEqualTo(StatusKarya.DRAFT);
            assertThat(karya.judul()).isEqualTo(JUDUL);
            assertThat(karya.sinopsis()).isEqualTo(SINOPSIS);
            assertThat(karya.idPenulis()).isEqualTo(ID_PENULIS);
            assertThat(karya.babList()).isEmpty();
            assertThat(karya.idGenre()).isEmpty();
            assertThat(karya.id()).isNotNull();
        }
    }

    @Nested
    @DisplayName("saat memastikan kepemilikan")
    class SaatMemastikanKepemilikan {

        @Test
        @DisplayName("pastikanPemilik() tidak melempar exception jika pemilik yang benar")
        void pastikanPemilik_tidakMelemparException_jikaPemilikBenar() {
            Karya karya = Karya.mulaiTulis(ID_PENULIS, JUDUL, SINOPSIS);

            assertThatCode(() -> karya.pastikanPemilik(ID_PENULIS)).doesNotThrowAnyException();
        }

        @Test
        @DisplayName("pastikanPemilik() melempar exception jika bukan pemilik")
        void pastikanPemilik_melemparException_jikaBukanPemilik() {
            Karya karya = Karya.mulaiTulis(ID_PENULIS, JUDUL, SINOPSIS);
            IdPengguna penggunaLain = IdPengguna.baru();

            assertThatThrownBy(() -> karya.pastikanPemilik(penggunaLain))
                    .isInstanceOf(BukanPemilikKaryaException.class)
                    .hasMessageContaining("bukan penulis karya ini");
        }
    }

    @Nested
    @DisplayName("saat mengedit judul dan sinopsis")
    class SaatMengeditJudulDanSinopsis {

        @Test
        @DisplayName("gantiJudul() mengubah judul karya")
        void gantiJudul_mengubahJudulKarya() {
            Karya karya = Karya.mulaiTulis(ID_PENULIS, JUDUL, SINOPSIS);
            JudulKarya judulBaru = new JudulKarya("Rumah di Ujung Senja (Revisi)");

            karya.gantiJudul(judulBaru);

            assertThat(karya.judul()).isEqualTo(judulBaru);
        }

        @Test
        @DisplayName("gantiSinopsis() mengubah sinopsis karya")
        void gantiSinopsis_mengubahSinopsisKarya() {
            Karya karya = Karya.mulaiTulis(ID_PENULIS, JUDUL, SINOPSIS);
            Sinopsis sinopsisBaru = new Sinopsis("Sinopsis baru yang menceritakan kisah berbeda tentang keluarga tersebut.");

            karya.gantiSinopsis(sinopsisBaru);

            assertThat(karya.sinopsis()).isEqualTo(sinopsisBaru);
        }
    }

    @Nested
    @DisplayName("saat mengelola genre")
    class SaatMengelolaGenre {

        @Test
        @DisplayName("tambahGenre() menambahkan genre ke karya")
        void tambahGenre_menambahkanGenreKeKarya() {
            Karya karya = Karya.mulaiTulis(ID_PENULIS, JUDUL, SINOPSIS);
            IdGenre idGenre = IdGenre.baru();

            karya.tambahGenre(idGenre);

            assertThat(karya.idGenre()).containsExactly(idGenre);
        }

        @Test
        @DisplayName("hapusGenre() mencabut genre dari karya")
        void hapusGenre_mencabutGenreDariKarya() {
            Karya karya = Karya.mulaiTulis(ID_PENULIS, JUDUL, SINOPSIS);
            IdGenre idGenre = IdGenre.baru();
            karya.tambahGenre(idGenre);

            karya.hapusGenre(idGenre);

            assertThat(karya.idGenre()).isEmpty();
        }

        @Test
        @DisplayName("idGenre() mengembalikan set yang tidak dapat diubah dari luar")
        void idGenre_mengembalikanSetTidakDapatDiubah() {
            Karya karya = Karya.mulaiTulis(ID_PENULIS, JUDUL, SINOPSIS);

            assertThatThrownBy(() -> karya.idGenre().add(IdGenre.baru()))
                    .isInstanceOf(UnsupportedOperationException.class);
        }
    }

    @Nested
    @DisplayName("saat mengelola bab")
    class SaatMengelolaBab {

        @Test
        @DisplayName("tambahBab() menambahkan bab dengan urutan 1 saat karya masih kosong")
        void tambahBab_menambahkanBabDenganUrutanSatu_saatKaryaKosong() {
            Karya karya = Karya.mulaiTulis(ID_PENULIS, JUDUL, SINOPSIS);

            Bab bab = karya.tambahBab(JUDUL_BAB, ISI_BAB);

            assertThat(karya.babList()).hasSize(1);
            assertThat(bab.urutan().nilai()).isEqualTo(1);
        }

        @Test
        @DisplayName("tambahBab() menghasilkan urutan berikutnya secara otomatis")
        void tambahBab_menghasilkanUrutanBerikutnyaOtomatis() {
            Karya karya = Karya.mulaiTulis(ID_PENULIS, JUDUL, SINOPSIS);
            karya.tambahBab(JUDUL_BAB, ISI_BAB);

            Bab babKedua = karya.tambahBab(new JudulBab("Bab Dua"), ISI_BAB);

            assertThat(karya.babList()).hasSize(2);
            assertThat(babKedua.urutan().nilai()).isEqualTo(2);
        }

        @Test
        @DisplayName("tambahBab() melempar exception jika karya berstatus DIARSIPKAN")
        void tambahBab_melemparException_jikaDiarsipkan() {
            Karya karya = karyaTerbitDenganSatuBab();
            karya.arsipkan();

            assertThatThrownBy(() -> karya.tambahBab(new JudulBab("Bab Baru"), ISI_BAB))
                    .isInstanceOf(IllegalStateException.class)
                    .hasMessageContaining("harus dipulihkan terlebih dahulu");
        }

        @Test
        @DisplayName("hapusBab() mencabut bab dari karya berstatus DRAFT meski hanya satu bab")
        void hapusBab_mencabutBab_dariDraftMeskiSatuSatunya() {
            Karya karya = Karya.mulaiTulis(ID_PENULIS, JUDUL, SINOPSIS);
            Bab bab = karya.tambahBab(JUDUL_BAB, ISI_BAB);

            karya.hapusBab(bab.id());

            assertThat(karya.babList()).isEmpty();
        }

        @Test
        @DisplayName("hapusBab() melempar exception jika itu satu-satunya bab pada karya TERBIT")
        void hapusBab_melemparException_jikaSatuSatunyaBabPadaKaryaTerbit() {
            Karya karya = karyaTerbitDenganSatuBab();
            IdBab satuSatunyaBab = karya.babList().get(0).id();

            assertThatThrownBy(() -> karya.hapusBab(satuSatunyaBab))
                    .isInstanceOf(IllegalStateException.class)
                    .hasMessageContaining("wajib memiliki minimal satu bab");
        }

        @Test
        @DisplayName("hapusBab() berhasil pada karya TERBIT jika masih ada bab lain tersisa")
        void hapusBab_berhasil_jikaMasihAdaBabLainTersisa() {
            Karya karya = karyaTerbitDenganSatuBab();
            Bab babKedua = karya.tambahBab(new JudulBab("Bab Dua"), ISI_BAB);

            karya.hapusBab(babKedua.id());

            assertThat(karya.babList()).hasSize(1);
        }

        @Test
        @DisplayName("hapusBab() melempar exception jika bab tidak ditemukan")
        void hapusBab_melemparException_jikaBabTidakDitemukan() {
            Karya karya = Karya.mulaiTulis(ID_PENULIS, JUDUL, SINOPSIS);
            karya.tambahBab(JUDUL_BAB, ISI_BAB);

            assertThatThrownBy(() -> karya.hapusBab(IdBab.baru()))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("Bab tidak ditemukan");
        }

        @Test
        @DisplayName("ubahJudulBab() mengganti judul bab tertentu")
        void ubahJudulBab_menggantiJudulBabTertentu() {
            Karya karya = Karya.mulaiTulis(ID_PENULIS, JUDUL, SINOPSIS);
            Bab bab = karya.tambahBab(JUDUL_BAB, ISI_BAB);
            JudulBab judulBaru = new JudulBab("Bab Satu (Revisi)");

            karya.ubahJudulBab(bab.id(), judulBaru);

            assertThat(karya.babList().get(0).judul()).isEqualTo(judulBaru);
        }

        @Test
        @DisplayName("ubahIsiBab() mengganti isi bab tertentu")
        void ubahIsiBab_menggantiIsiBabTertentu() {
            Karya karya = Karya.mulaiTulis(ID_PENULIS, JUDUL, SINOPSIS);
            Bab bab = karya.tambahBab(JUDUL_BAB, ISI_BAB);
            IsiBab isiBaru = new IsiBab("Ini adalah isi bab yang sudah direvisi dan tetap melewati validasi panjang minimal.");

            karya.ubahIsiBab(bab.id(), isiBaru);

            assertThat(karya.babList().get(0).isi()).isEqualTo(isiBaru);
        }
    }

    /**
     * Helper: membuat karya yang sudah TERBIT dengan satu bab dan satu genre,
     * memenuhi seluruh syarat penerbitan.
     */
    private static Karya karyaTerbitDenganSatuBab() {
        Karya karya = Karya.mulaiTulis(ID_PENULIS, JUDUL, SINOPSIS);
        karya.tambahBab(JUDUL_BAB, ISI_BAB);
        karya.tambahGenre(IdGenre.baru());
        karya.terbitkan();
        return karya;
    }

    @Nested
    @DisplayName("saat mengubah status penerbitan")
    class SaatMengubahStatusPenerbitan {

        @Test
        @DisplayName("terbitkan() berhasil jika memiliki minimal satu bab dan satu genre")
        void terbitkan_berhasil_jikaMemilikiBabDanGenre() {
            Karya karya = Karya.mulaiTulis(ID_PENULIS, JUDUL, SINOPSIS);
            karya.tambahBab(JUDUL_BAB, ISI_BAB);
            karya.tambahGenre(IdGenre.baru());

            karya.terbitkan();

            assertThat(karya.status()).isEqualTo(StatusKarya.TERBIT);
        }

        @Test
        @DisplayName("terbitkan() melempar exception jika status bukan DRAFT")
        void terbitkan_melemparException_jikaStatusBukanDraft() {
            Karya karya = karyaTerbitDenganSatuBab();

            assertThatThrownBy(karya::terbitkan)
                    .isInstanceOf(IllegalStateException.class)
                    .hasMessageContaining("Hanya karya berstatus draft");
        }

        @Test
        @DisplayName("terbitkan() melempar exception jika belum memiliki bab")
        void terbitkan_melemparException_jikaBelumMemilikiBab() {
            Karya karya = Karya.mulaiTulis(ID_PENULIS, JUDUL, SINOPSIS);
            karya.tambahGenre(IdGenre.baru());

            assertThatThrownBy(karya::terbitkan)
                    .isInstanceOf(IllegalStateException.class)
                    .hasMessageContaining("minimal satu bab");
        }

        @Test
        @DisplayName("terbitkan() melempar exception jika belum memiliki genre")
        void terbitkan_melemparException_jikaBelumMemilikiGenre() {
            Karya karya = Karya.mulaiTulis(ID_PENULIS, JUDUL, SINOPSIS);
            karya.tambahBab(JUDUL_BAB, ISI_BAB);

            assertThatThrownBy(karya::terbitkan)
                    .isInstanceOf(IllegalStateException.class)
                    .hasMessageContaining("minimal satu genre");
        }

        @Test
        @DisplayName("tarikKeDraft() mengubah status TERBIT menjadi DRAFT")
        void tarikKeDraft_mengubahStatusMenjadiDraft() {
            Karya karya = karyaTerbitDenganSatuBab();

            karya.tarikKeDraft();

            assertThat(karya.status()).isEqualTo(StatusKarya.DRAFT);
        }

        @Test
        @DisplayName("tarikKeDraft() melempar exception jika status bukan TERBIT")
        void tarikKeDraft_melemparException_jikaStatusBukanTerbit() {
            Karya karya = Karya.mulaiTulis(ID_PENULIS, JUDUL, SINOPSIS);

            assertThatThrownBy(karya::tarikKeDraft)
                    .isInstanceOf(IllegalStateException.class)
                    .hasMessageContaining("sudah terbit");
        }

        @Test
        @DisplayName("arsipkan() mengubah status TERBIT menjadi DIARSIPKAN")
        void arsipkan_mengubahStatusMenjadiDiarsipkan() {
            Karya karya = karyaTerbitDenganSatuBab();

            karya.arsipkan();

            assertThat(karya.status()).isEqualTo(StatusKarya.DIARSIPKAN);
        }

        @Test
        @DisplayName("arsipkan() melempar exception jika status bukan TERBIT")
        void arsipkan_melemparException_jikaStatusBukanTerbit() {
            Karya karya = Karya.mulaiTulis(ID_PENULIS, JUDUL, SINOPSIS);

            assertThatThrownBy(karya::arsipkan)
                    .isInstanceOf(IllegalStateException.class)
                    .hasMessageContaining("sudah terbit");
        }

        @Test
        @DisplayName("pulihkanDariArsip() mengubah status DIARSIPKAN menjadi TERBIT")
        void pulihkanDariArsip_mengubahStatusMenjadiTerbit() {
            Karya karya = karyaTerbitDenganSatuBab();
            karya.arsipkan();

            karya.pulihkanDariArsip();

            assertThat(karya.status()).isEqualTo(StatusKarya.TERBIT);
        }

        @Test
        @DisplayName("pulihkanDariArsip() melempar exception jika status bukan DIARSIPKAN")
        void pulihkanDariArsip_melemparException_jikaStatusBukanDiarsipkan() {
            Karya karya = Karya.mulaiTulis(ID_PENULIS, JUDUL, SINOPSIS);

            assertThatThrownBy(karya::pulihkanDariArsip)
                    .isInstanceOf(IllegalStateException.class)
                    .hasMessageContaining("yang diarsipkan");
        }
    }
}
