/*
 * Copyright (c) 2026 Farich Murobic
 *
 * This project is licensed under the MIT License.
 * See the LICENSE file in the project root for more information.
 */

package com.bedroom.infrastructure.persistence.repository;

import com.bedroom.domain.karya.model.Genre;
import com.bedroom.domain.karya.repository.RepositoriGenre;
import com.bedroom.domain.karya.valueobject.IdGenre;
import com.bedroom.domain.karya.valueobject.NamaGenre;
import com.bedroom.infrastructure.persistence.entity.GenreEntity;
import com.bedroom.infrastructure.persistence.mapper.GenreMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Implementasi repository {@code RepositoriGenre} menggunakan JPA.
 */
@Repository
public class RepositoriGenreImpl implements RepositoriGenre {

    private final JpaGenreRepository jpaGenreRepository;
    private final GenreMapper genreMapper;

    public RepositoriGenreImpl(JpaGenreRepository jpaGenreRepository, GenreMapper genreMapper) {
        this.jpaGenreRepository = Objects.requireNonNull(jpaGenreRepository, "Jpa genre repository tidak boleh kosong");
        this.genreMapper = Objects.requireNonNull(genreMapper, "Genre mapper tidak boleh kosong");
    }

    @Override
    public Genre simpan(Genre genre) {
        GenreEntity entity = genreMapper.keEntity(genre);
        GenreEntity entityTersimpan = jpaGenreRepository.save(entity);
        return genreMapper.keDomain(entityTersimpan);
    }

    @Override
    public Optional<Genre> cariBerdasarkanId(IdGenre id) {
        return jpaGenreRepository.findById(id.nilai())
                .map(genreMapper::keDomain);
    }

    @Override
    public List<Genre> cariSemua() {
        return jpaGenreRepository.findAll().stream()
                .map(genreMapper::keDomain)
                .toList();
    }

    @Override
    public boolean adaBerdasarkanNama(NamaGenre nama) {
        return jpaGenreRepository.existsByNama(nama.nilai());
    }

    @Override
    public void hapusBerdasarkanId(IdGenre id) {
        jpaGenreRepository.deleteById(id.nilai());
    }
}