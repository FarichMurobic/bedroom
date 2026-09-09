package com.bedroom.infrastructure.persistence.repository;

import com.bedroom.domain.identitas.valueobject.IdPengguna;
import com.bedroom.domain.karya.model.Bab;
import com.bedroom.domain.karya.model.Karya;
import com.bedroom.domain.karya.valueobject.IdBab;
import com.bedroom.domain.karya.valueobject.IsiBab;
import com.bedroom.domain.karya.valueobject.JudulBab;
import com.bedroom.domain.karya.valueobject.JudulKarya;
import com.bedroom.domain.karya.valueobject.Sinopsis;
import com.bedroom.infrastructure.persistence.mapper.KaryaMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Menguji perilaku persistence {@code RepositoriKaryaImpl} secara langsung
 * terhadap basis data H2, khususnya penanganan cascade dan merge Hibernate
 * terhadap koleksi {@code Bab} yang bersarang di dalam {@code Karya}.
 */
@DataJpaTest
@ActiveProfiles("test")
@Import({RepositoriKaryaImpl.class, KaryaMapper.class})
@DisplayName("RepositoriKaryaImpl")
class RepositoriKaryaImplTest {

    @Autowired
    private RepositoriKaryaImpl repositoriKarya;

    private static final IdPengguna ID_PENULIS = IdPengguna.baru();
    private static final JudulKarya JUDUL = new JudulKarya("Rumah di Ujung Senja");
    private static final Sinopsis SINOPSIS = new Sinopsis("Sebuah kisah tentang keluarga yang tinggal di rumah tua di ujung desa.");

    @Test
    @DisplayName("simpan() dan cariBerdasarkanId() berhasil menyimpan karya beserta bab-babnya")
    void simpanDanCari_berhasilMenyimpanKaryaBesertaBab() {
        Karya karya = Karya.mulaiTulis(ID_PENULIS, JUDUL, SINOPSIS);
        karya.tambahBab(new JudulBab("Bab Satu"), new IsiBab("Ini adalah isi bab pertama yang cukup panjang untuk lolos validasi."));

        Karya karyaTersimpan = repositoriKarya.simpan(karya);
        Optional<Karya> hasilPencarian = repositoriKarya.cariBerdasarkanId(karyaTersimpan.id());

        assertThat(hasilPencarian).isPresent();
        assertThat(hasilPencarian.get().babList()).hasSize(1);
        assertThat(hasilPencarian.get().babList().get(0).judul().nilai()).isEqualTo("Bab Satu");
    }

    @Test
    @DisplayName("simpan() berhasil meng-update isi bab yang sudah ada tanpa duplikasi baris")
    void simpan_berhasilMengupdateIsiBab_tanpaDuplikasiBaris() {
        Karya karya = Karya.mulaiTulis(ID_PENULIS, JUDUL, SINOPSIS);
        Bab bab = karya.tambahBab(new JudulBab("Bab Satu"), new IsiBab("Ini adalah isi bab pertama yang cukup panjang untuk lolos validasi."));
        Karya karyaTersimpan = repositoriKarya.simpan(karya);

        Karya karyaUntukDiubah = repositoriKarya.cariBerdasarkanId(karyaTersimpan.id()).orElseThrow();
        IdBab idBab = karyaUntukDiubah.babList().get(0).id();
        karyaUntukDiubah.ubahIsiBab(idBab, new IsiBab("Ini adalah isi bab yang sudah diperbarui dan tetap melewati validasi panjang minimal."));
        repositoriKarya.simpan(karyaUntukDiubah);

        Karya karyaSetelahUpdate = repositoriKarya.cariBerdasarkanId(karyaTersimpan.id()).orElseThrow();

        assertThat(karyaSetelahUpdate.babList()).hasSize(1);
        assertThat(karyaSetelahUpdate.babList().get(0).isi().nilai())
                .isEqualTo("Ini adalah isi bab yang sudah diperbarui dan tetap melewati validasi panjang minimal.");
    }

    @Test
    @DisplayName("simpan() berhasil menghapus bab dari database saat dihapus dari daftar (orphan removal)")
    void simpan_berhasilMenghapusBab_dariDatabase() {
        Karya karya = Karya.mulaiTulis(ID_PENULIS, JUDUL, SINOPSIS);
        karya.tambahBab(new JudulBab("Bab Satu"), new IsiBab("Ini adalah isi bab pertama yang cukup panjang untuk lolos validasi."));
        Bab babKedua = karya.tambahBab(new JudulBab("Bab Dua"), new IsiBab("Ini adalah isi bab kedua yang juga cukup panjang untuk lolos validasi."));
        Karya karyaTersimpan = repositoriKarya.simpan(karya);

        Karya karyaUntukDiubah = repositoriKarya.cariBerdasarkanId(karyaTersimpan.id()).orElseThrow();
        IdBab idBabKedua = karyaUntukDiubah.babList().stream()
                .filter(bab -> bab.judul().nilai().equals("Bab Dua"))
                .findFirst().orElseThrow().id();
        karyaUntukDiubah.hapusBab(idBabKedua);
        repositoriKarya.simpan(karyaUntukDiubah);

        Karya karyaSetelahHapus = repositoriKarya.cariBerdasarkanId(karyaTersimpan.id()).orElseThrow();

        assertThat(karyaSetelahHapus.babList()).hasSize(1);
        assertThat(karyaSetelahHapus.babList().get(0).judul().nilai()).isEqualTo("Bab Satu");
    }
}