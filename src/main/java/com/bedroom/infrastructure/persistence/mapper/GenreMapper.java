/*
 * Copyright (c) 2026 Farich Murobic
 *
 * This project is licensed under the MIT License.
 * See the LICENSE file in the project root for more information.
 */

package com.bedroom.infrastructure.persistence.mapper;

import com.bedroom.domain.karya.model.Genre;
import com.bedroom.domain.karya.valueobject.IdGenre;
import com.bedroom.domain.karya.valueobject.NamaGenre;
import com.bedroom.infrastructure.persistence.entity.GenreEntity;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * Menerjemahkan entity domain {@code Genre} ke representasi persistence
 * {@code GenreEntity}, dan sebaliknya.
 */
@Component
public class GenreMapper {

    public GenreEntity keEntity(Genre genre) {
        Objects.requireNonNull(genre, "Genre tidak boleh kosong");
        return new GenreEntity(
                genre.id().nilai(),
                genre.nama().nilai(),
                genre.dibuatPada()
        );
    }

    public Genre keDomain(GenreEntity entity) {
        Objects.requireNonNull(entity, "Entity genre tidak boleh kosong");
        return Genre.rekonstruksi(
                IdGenre.dari(entity.getId()),
                new NamaGenre(entity.getNama()),
                entity.getDibuatPada()
        );
    }
}