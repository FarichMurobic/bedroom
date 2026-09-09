/*
 * Copyright (c) 2026 Farich Murobiq
 *
 * This project is licensed under the MIT License.
 * See the LICENSE file in the project root for more information.
 */

package com.bedroom.domain.karya.model;

import com.bedroom.domain.identitas.valueobject.IdPengguna;
import com.bedroom.domain.karya.enums.StatusKarya;
import com.bedroom.domain.karya.exception.BukanPemilikKaryaException;
import com.bedroom.domain.karya.valueobject.IdBab;
import com.bedroom.domain.karya.valueobject.IdGenre;
import com.bedroom.domain.karya.valueobject.IdKarya;
import com.bedroom.domain.karya.valueobject.IsiBab;
import com.bedroom.domain.karya.valueobject.JudulBab;
import com.bedroom.domain.karya.valueobject.JudulKarya;
import com.bedroom.domain.karya.valueobject.Sinopsis;
import com.bedroom.domain.karya.valueobject.UrutanBab;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * Merepresentasikan sebuah karya tulis, seperti artikel maupun cerita
 * bersambung, di platform Bedroom.
 *
 * <p>{@code Karya} merupakan aggregate root yang memiliki kendali penuh
 * terhadap entity {@code Bab} yang berada di dalamnya. Seluruh manipulasi
 * terhadap bab dilakukan melalui aggregate ini agar aturan dan konsistensi
 * domain tetap terjaga.</p>
 *
 * <p>Entity ini memiliki identitas unik melalui {@code IdKarya}, terikat
 * pada seorang penulis, serta mengelola judul, sinopsis, status penerbitan,
 * genre, bab, dan informasi waktu perubahan.</p>
 *
 * <p>Siklus hidup karya terdiri dari status {@code DRAFT},
 * {@code TERBIT}, dan {@code DIARSIPKAN}. Setiap perubahan status
 * mengikuti aturan transisi yang ditentukan oleh domain.</p>
 */
public final class Karya {

    private final IdKarya id;
    private final IdPengguna idPenulis;
    private JudulKarya judul;
    private Sinopsis sinopsis;
    private StatusKarya status;
    private final Set<IdGenre> idGenre;
    private final List<Bab> babList;
    private final Instant dibuatPada;
    private Instant diperbaruiPada;

    private Karya(
            IdKarya id, IdPengguna idPenulis, JudulKarya judul, Sinopsis sinopsis,
            StatusKarya status, Set<IdGenre> idGenre, List<Bab> babList,
            Instant dibuatPada, Instant diperbaruiPada
    ) {
        this.id = Objects.requireNonNull(id, "Id karya tidak boleh kosong");
        this.idPenulis = Objects.requireNonNull(idPenulis, "Id penulis tidak boleh kosong");
        this.judul = Objects.requireNonNull(judul, "Judul karya tidak boleh kosong");
        this.sinopsis = Objects.requireNonNull(sinopsis, "Sinopsis tidak boleh kosong");
        this.status = Objects.requireNonNull(status, "Status karya tidak boleh kosong");
        this.idGenre = new HashSet<>(Objects.requireNonNull(idGenre, "Daftar genre tidak boleh kosong"));
        this.babList = new ArrayList<>(Objects.requireNonNull(babList, "Daftar bab tidak boleh kosong"));
        this.dibuatPada = Objects.requireNonNull(dibuatPada, "Waktu pembuatan tidak boleh kosong");
        this.diperbaruiPada = Objects.requireNonNull(diperbaruiPada, "Waktu pembaruan tidak boleh kosong");
    }

    /**
     * Memulai penulisan karya baru.
     *
     * <p>Karya baru selalu dimulai dengan status {@code DRAFT},
     * tanpa genre dan tanpa bab. Genre serta bab dapat ditambahkan
     * setelah karya berhasil dibuat.</p>
     *
     * @param idPenulis ID pengguna yang menjadi penulis karya
     * @param judul judul awal karya
     * @param sinopsis sinopsis awal karya
     * @return entity {@code Karya} baru dengan status {@code DRAFT}
     * @throws NullPointerException jika salah satu parameter bernilai {@code null}
     */
    public static Karya mulaiTulis(IdPengguna idPenulis, JudulKarya judul, Sinopsis sinopsis) {
        Instant sekarang = Instant.now();
        return new Karya(
                IdKarya.baru(), idPenulis, judul, sinopsis,
                StatusKarya.DRAFT, new HashSet<>(), new ArrayList<>(),
                sekarang, sekarang
        );
    }

    /**
     * Membangun kembali entity {@code Karya} dari data yang telah tersimpan.
     *
     * <p>Factory method ini digunakan ketika aggregate perlu direkonstruksi
     * dari sumber penyimpanan, seperti basis data, tanpa membuat ID atau
     * waktu baru.</p>
     *
     * @param id ID unik karya
     * @param idPenulis ID pengguna yang menjadi penulis karya
     * @param judul judul karya
     * @param sinopsis sinopsis karya
     * @param status status penerbitan karya
     * @param idGenre kumpulan ID genre yang terkait dengan karya
     * @param babList daftar bab yang dimiliki karya
     * @param dibuatPada waktu ketika karya dibuat
     * @param diperbaruiPada waktu terakhir karya diperbarui
     * @return entity {@code Karya} yang telah direkonstruksi
     * @throws NullPointerException jika salah satu parameter bernilai {@code null}
     */
    public static Karya rekonstruksi(
            IdKarya id, IdPengguna idPenulis, JudulKarya judul, Sinopsis sinopsis,
            StatusKarya status, Set<IdGenre> idGenre, List<Bab> babList,
            Instant dibuatPada, Instant diperbaruiPada
    ) {
        return new Karya(id, idPenulis, judul, sinopsis, status, idGenre, babList, dibuatPada, diperbaruiPada);
    }

    /**
     * Memastikan bahwa pengguna yang diberikan merupakan penulis
     * dari karya ini.
     *
     * <p>Validasi ini digunakan untuk memastikan bahwa tindakan yang
     * membutuhkan hak kepemilikan hanya dilakukan oleh penulis karya.</p>
     *
     * @param idPengguna ID pengguna yang akan diperiksa
     * @throws NullPointerException jika {@code idPengguna} bernilai {@code null}
     * @throws BukanPemilikKaryaException jika pengguna bukan penulis karya
     */
    public void pastikanPemilik(IdPengguna idPengguna) {
        if (!this.idPenulis.equals(idPengguna)) {
            throw new BukanPemilikKaryaException("Anda bukan penulis karya ini");
        }
    }

    /**
     * Mengganti judul karya dan memperbarui waktu perubahan terakhir.
     *
     * @param judulBaru judul baru untuk karya
     * @throws NullPointerException jika {@code judulBaru} bernilai {@code null}
     */
    public void gantiJudul(JudulKarya judulBaru) {
        this.judul = Objects.requireNonNull(judulBaru, "Judul karya tidak boleh kosong");
        sentuh();
    }

    /**
     * Mengganti sinopsis karya dan memperbarui waktu perubahan terakhir.
     *
     * @param sinopsisBaru sinopsis baru untuk karya
     * @throws NullPointerException jika {@code sinopsisBaru} bernilai {@code null}
     */
    public void gantiSinopsis(Sinopsis sinopsisBaru) {
        this.sinopsis = Objects.requireNonNull(sinopsisBaru, "Sinopsis tidak boleh kosong");
        sentuh();
    }

    /**
     * Menambahkan genre ke dalam karya.
     *
     * <p>Penambahan tidak mengubah koleksi apabila genre tersebut
     * sudah terdapat dalam karya.</p>
     *
     * @param idGenreBaru ID genre yang akan ditambahkan
     * @throws NullPointerException jika {@code idGenreBaru} bernilai {@code null}
     */
    public void tambahGenre(IdGenre idGenreBaru) {
        Objects.requireNonNull(idGenreBaru, "Id genre tidak boleh kosong");
        this.idGenre.add(idGenreBaru);
        sentuh();
    }

    /**
     * Menghapus genre dari karya.
     *
     * <p>Jika genre tidak terdapat dalam karya, tidak ada perubahan
     * terhadap koleksi genre.</p>
     *
     * @param idGenreDicabut ID genre yang akan dihapus
     * @throws NullPointerException jika {@code idGenreDicabut} bernilai {@code null}
     */
    public void hapusGenre(IdGenre idGenreDicabut) {
        Objects.requireNonNull(idGenreDicabut, "Id genre tidak boleh kosong");
        this.idGenre.remove(idGenreDicabut);
        sentuh();
    }

    /**
     * Menambahkan bab baru di akhir urutan karya.
     *
     * <p>Urutan bab baru ditentukan berdasarkan bab terakhir yang
     * telah dimiliki karya. Karya yang telah diarsipkan tidak dapat
     * ditambahkan bab sebelum dipulihkan terlebih dahulu.</p>
     *
     * @param judulBab judul bab baru
     * @param isiBab isi bab baru
     * @return entity {@code Bab} yang baru ditambahkan
     * @throws NullPointerException jika {@code judulBab} atau
     *         {@code isiBab} bernilai {@code null}
     * @throws IllegalStateException jika karya berstatus {@code DIARSIPKAN}
     */
    public Bab tambahBab(JudulBab judulBab, IsiBab isiBab) {
        pastikanTidakDiarsipkan();

        UrutanBab urutanBaru = babList.isEmpty()
                ? new UrutanBab(1)
                : babList.get(babList.size() - 1).urutan().berikutnya();

        Bab babBaru = Bab.buat(judulBab, isiBab, urutanBaru);
        babList.add(babBaru);
        sentuh();
        return babBaru;
    }

    /**
     * Menghapus sebuah bab dari karya.
     *
     * <p>Karya yang telah terbit wajib mempertahankan minimal satu bab,
     * sehingga bab terakhir tidak dapat dihapus ketika karya berstatus
     * {@code TERBIT}.</p>
     *
     * @param idBab ID bab yang akan dihapus
     * @throws NullPointerException jika {@code idBab} bernilai {@code null}
     * @throws IllegalStateException jika karya berstatus {@code DIARSIPKAN},
     *         atau jika bab yang akan dihapus merupakan satu-satunya bab
     *         pada karya yang berstatus {@code TERBIT}
     * @throws IllegalArgumentException jika bab tidak ditemukan pada karya
     */
    public void hapusBab(IdBab idBab) {
        pastikanTidakDiarsipkan();
        Objects.requireNonNull(idBab, "Id bab tidak boleh kosong");

        if (status == StatusKarya.TERBIT && babList.size() == 1) {
            throw new IllegalStateException("Karya yang sudah terbit wajib memiliki minimal satu bab");
        }

        boolean terhapus = babList.removeIf(bab -> bab.id().equals(idBab));
        if (!terhapus) {
            throw new IllegalArgumentException("Bab tidak ditemukan pada karya ini");
        }
        sentuh();
    }

    /**
     * Mengganti judul sebuah bab yang berada di dalam karya.
     *
     * @param idBab ID bab yang akan diubah
     * @param judulBaru judul baru untuk bab
     * @throws NullPointerException jika {@code idBab} atau
     *         {@code judulBaru} bernilai {@code null}
     * @throws IllegalArgumentException jika bab tidak ditemukan
     * @throws IllegalStateException jika karya berstatus {@code DIARSIPKAN}
     */
    public void ubahJudulBab(IdBab idBab, JudulBab judulBaru) {
        cariBabAtauGagal(idBab).ubahJudul(judulBaru);
        sentuh();
    }

    /**
     * Mengganti isi sebuah bab yang berada di dalam karya.
     *
     * @param idBab ID bab yang akan diubah
     * @param isiBaru isi baru untuk bab
     * @throws NullPointerException jika {@code idBab} atau
     *         {@code isiBaru} bernilai {@code null}
     * @throws IllegalArgumentException jika bab tidak ditemukan
     * @throws IllegalStateException jika karya berstatus {@code DIARSIPKAN}
     */
    public void ubahIsiBab(IdBab idBab, IsiBab isiBaru) {
        cariBabAtauGagal(idBab).ubahIsi(isiBaru);
        sentuh();
    }

    /**
     * Menerbitkan karya sehingga dapat dibaca secara publik.
     *
     * <p>Hanya karya berstatus {@code DRAFT} yang dapat diterbitkan.
     * Sebelum diterbitkan, karya wajib memiliki minimal satu bab dan
     * satu genre.</p>
     *
     * @throws IllegalStateException jika karya tidak berstatus {@code DRAFT},
     *         belum memiliki bab, atau belum memiliki genre
     */
    public void terbitkan() {
        if (status != StatusKarya.DRAFT) {
            throw new IllegalStateException("Hanya karya berstatus draft yang dapat diterbitkan");
        }
        if (babList.isEmpty()) {
            throw new IllegalStateException("Karya harus memiliki minimal satu bab sebelum diterbitkan");
        }
        if (idGenre.isEmpty()) {
            throw new IllegalStateException("Karya harus memiliki minimal satu genre sebelum diterbitkan");
        }
        ubahStatus(StatusKarya.TERBIT);
    }

    /**
     * Menarik karya yang telah terbit kembali menjadi draft.
     *
     * @throws IllegalStateException jika karya tidak berstatus {@code TERBIT}
     */
    public void tarikKeDraft() {
        if (status != StatusKarya.TERBIT) {
            throw new IllegalStateException("Hanya karya yang sudah terbit yang dapat ditarik ke draft");
        }
        ubahStatus(StatusKarya.DRAFT);
    }

    /**
     * Mengarsipkan karya yang telah terbit.
     *
     * <p>Pengarsipan menyembunyikan karya dari pembaca publik tanpa
     * menghapus data karya secara permanen.</p>
     *
     * @throws IllegalStateException jika karya tidak berstatus {@code TERBIT}
     */
    public void arsipkan() {
        if (status != StatusKarya.TERBIT) {
            throw new IllegalStateException("Hanya karya yang sudah terbit yang dapat diarsipkan");
        }
        ubahStatus(StatusKarya.DIARSIPKAN);
    }

    /**
     * Memulihkan karya yang diarsipkan kembali menjadi terbit.
     *
     * @throws IllegalStateException jika karya tidak berstatus {@code DIARSIPKAN}
     */
    public void pulihkanDariArsip() {
        if (status != StatusKarya.DIARSIPKAN) {
            throw new IllegalStateException("Hanya karya yang diarsipkan yang dapat dipulihkan");
        }
        ubahStatus(StatusKarya.TERBIT);
    }

    /**
     * Memastikan karya tidak sedang berada dalam status {@code DIARSIPKAN}
     * sebelum dilakukan perubahan terhadap data karya.
     *
     * @throws IllegalStateException jika karya berstatus {@code DIARSIPKAN}
     */
    private void pastikanTidakDiarsipkan() {
        if (status == StatusKarya.DIARSIPKAN) {
            throw new IllegalStateException("Karya yang diarsipkan harus dipulihkan terlebih dahulu sebelum diubah");
        }
    }

    /**
     * Mencari bab berdasarkan ID atau melempar exception apabila
     * bab tidak ditemukan.
     *
     * @param idBab ID bab yang akan dicari
     * @return bab yang ditemukan
     * @throws NullPointerException jika {@code idBab} bernilai {@code null}
     * @throws IllegalStateException jika karya berstatus {@code DIARSIPKAN}
     * @throws IllegalArgumentException jika bab tidak ditemukan
     */
    private Bab cariBabAtauGagal(IdBab idBab) {
        pastikanTidakDiarsipkan();
        return babList.stream()
                .filter(bab -> bab.id().equals(idBab))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Bab tidak ditemukan pada karya ini"));
    }

    /**
     * Mengubah status karya dan memperbarui waktu perubahan terakhir.
     *
     * @param statusBaru status baru karya
     */
    private void ubahStatus(StatusKarya statusBaru) {
        this.status = statusBaru;
        sentuh();
    }

    /**
     * Memperbarui waktu perubahan terakhir karya.
     */
    private void sentuh() {
        this.diperbaruiPada = Instant.now();
    }

    public IdKarya id() {
        return id;
    }

    public IdPengguna idPenulis() {
        return idPenulis;
    }

    public JudulKarya judul() {
        return judul;
    }

    public Sinopsis sinopsis() {
        return sinopsis;
    }

    public StatusKarya status() {
        return status;
    }

    public Set<IdGenre> idGenre() {
        return Collections.unmodifiableSet(idGenre);
    }

    public List<Bab> babList() {
        return Collections.unmodifiableList(babList);
    }

    public Instant dibuatPada() {
        return dibuatPada;
    }

    public Instant diperbaruiPada() {
        return diperbaruiPada;
    }
}