/*
 * Copyright (c) 2026 Farich Murobic
 *
 * This project is licensed under the MIT License.
 * See the LICENSE file in the project root for more information.
 */

package com.bedroom.application.karya.service;

import com.bedroom.application.karya.result.GenreResult;
import com.bedroom.application.karya.result.KaryaResult;
import com.bedroom.domain.karya.enums.StatusKarya;
import com.bedroom.domain.karya.model.Genre;
import com.bedroom.domain.karya.model.Karya;
import com.bedroom.domain.karya.repository.RepositoriGenre;
import com.bedroom.domain.karya.repository.RepositoriKarya;
import com.bedroom.domain.karya.valueobject.IdKarya;
import com.bedroom.shared.exception.SumberDayaTidakDitemukanException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

/**
 * Mengorkestrasi use case penjelajahan karya dan genre oleh publik.
 * Hanya karya berstatus {@code TERBIT} yang dapat diakses melalui service ini.
 */
public class PenjelajahanKaryaApplicationService {

    private final RepositoriKarya repositoriKarya;
    private final RepositoriGenre repositoriGenre;

    public PenjelajahanKaryaApplicationService(RepositoriKarya repositoriKarya, RepositoriGenre repositoriGenre) {
        this.repositoriKarya = Objects.requireNonNull(repositoriKarya, "Repositori karya tidak boleh kosong");
        this.repositoriGenre = Objects.requireNonNull(repositoriGenre, "Repositori genre tidak boleh kosong");
    }

    /**
     * Mendapatkan daftar semua karya yang telah diterbitkan.
     *
     * @return daftar karya terbit
     */
    @Transactional(readOnly = true)
    public List<KaryaResult> daftarKaryaTerbit() {
        return repositoriKarya.cariBerdasarkanStatus(StatusKarya.TERBIT).stream()
                .map(KaryaMapper::keResult)
                .toList();
    }

    /**
     * Mendapatkan detail sebuah karya berdasarkan ID.
     *
     * @param idKaryaString ID karya
     * @return data detail karya
     * @throws SumberDayaTidakDitemukanException jika karya tidak ditemukan atau tidak terbit
     */
    @Transactional(readOnly = true)
    public KaryaResult detailKarya(String idKaryaString) {
        Objects.requireNonNull(idKaryaString, "Id karya tidak boleh kosong");

        IdKarya idKarya = IdKarya.dari(idKaryaString);
        Karya karya = repositoriKarya.cariBerdasarkanId(idKarya)
                .filter(k -> k.status() == StatusKarya.TERBIT)
                .orElseThrow(() -> new SumberDayaTidakDitemukanException("Karya tidak ditemukan"));

        return KaryaMapper.keResult(karya);
    }

    /**
     * Mendapatkan daftar semua genre yang tersedia.
     *
     * @return daftar genre
     */
    @Transactional(readOnly = true)
    public List<GenreResult> daftarGenre() {
        return repositoriGenre.cariSemua().stream()
                .map(this::keGenreResult)
                .toList();
    }

    private GenreResult keGenreResult(Genre genre) {
        return new GenreResult(genre.id().nilai().toString(), genre.nama().nilai());
    }
}