/*
 * Copyright (c) 2026 Farich Murobic
 *
 * This project is licensed under the MIT License.
 * See the LICENSE file in the project root for more information.
 */

package com.bedroom.infrastructure.persistence.mapper;

import com.bedroom.domain.identitas.valueobject.IdPengguna;
import com.bedroom.domain.karya.model.Bab;
import com.bedroom.domain.karya.model.Karya;
import com.bedroom.domain.karya.valueobject.IdBab;
import com.bedroom.domain.karya.valueobject.IdGenre;
import com.bedroom.domain.karya.valueobject.IdKarya;
import com.bedroom.domain.karya.valueobject.IsiBab;
import com.bedroom.domain.karya.valueobject.JudulBab;
import com.bedroom.domain.karya.valueobject.JudulKarya;
import com.bedroom.domain.karya.valueobject.Sinopsis;
import com.bedroom.domain.karya.valueobject.UrutanBab;
import com.bedroom.infrastructure.persistence.entity.BabEntity;
import com.bedroom.infrastructure.persistence.entity.GenreEntity;
import com.bedroom.infrastructure.persistence.entity.KaryaEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Menerjemahkan aggregate domain {@code Karya} (beserta seluruh {@code Bab}
 * dan {@code Genre} di dalamnya) ke representasi persistence {@code KaryaEntity},
 * dan sebaliknya.
 */
@Component
public class KaryaMapper {

    public KaryaEntity keEntity(Karya karya) {
        Objects.requireNonNull(karya, "Karya tidak boleh kosong");

        KaryaEntity karyaEntity = new KaryaEntity(
                karya.id().nilai(),
                karya.idPenulis().nilai(),
                karya.judul().nilai(),
                karya.sinopsis().nilai(),
                karya.status(),
                karya.idGenre().stream()
                        .map(idGenre -> genreEntityReferensi(idGenre))
                        .collect(Collectors.toSet()),
                new java.util.ArrayList<>(),
                karya.dibuatPada(),
                karya.diperbaruiPada()
        );

        List<BabEntity> babEntityList = karya.babList().stream()
                .map(bab -> keBabEntity(bab, karyaEntity))
                .toList();
        karyaEntity.getBabList().addAll(babEntityList);

        return karyaEntity;
    }

    public Karya keDomain(KaryaEntity entity) {
        Objects.requireNonNull(entity, "Entity karya tidak boleh kosong");

        List<Bab> babList = entity.getBabList().stream()
                .map(this::keBabDomain)
                .toList();

        Set<IdGenre> idGenreSet = entity.getGenreList().stream()
                .map(genreEntity -> IdGenre.dari(genreEntity.getId()))
                .collect(Collectors.toSet());

        return Karya.rekonstruksi(
                IdKarya.dari(entity.getId()),
                IdPengguna.dari(entity.getPenulisId()),
                new JudulKarya(entity.getJudul()),
                new Sinopsis(entity.getSinopsis()),
                entity.getStatus(),
                idGenreSet,
                babList,
                entity.getDibuatPada(),
                entity.getDiperbaruiPada()
        );
    }

    private BabEntity keBabEntity(Bab bab, KaryaEntity karyaEntity) {
        return new BabEntity(
                bab.id().nilai(),
                karyaEntity,
                bab.judul().nilai(),
                bab.isi().nilai(),
                bab.urutan().nilai(),
                bab.dibuatPada(),
                bab.diperbaruiPada()
        );
    }

    private Bab keBabDomain(BabEntity entity) {
        return Bab.rekonstruksi(
                IdBab.dari(entity.getId()),
                new JudulBab(entity.getJudul()),
                new IsiBab(entity.getIsi()),
                new UrutanBab(entity.getUrutan()),
                entity.getDibuatPada(),
                entity.getDiperbaruiPada()
        );
    }

    /**
     * Membuat referensi {@code GenreEntity} minimal (hanya berisi id) untuk
     * keperluan relasi many-to-many. Hibernate akan mengenali entity ini
     * sebagai referensi ke baris yang sudah ada, bukan entity baru.
     */
    private GenreEntity genreEntityReferensi(IdGenre idGenre) {
        return new GenreEntity(idGenre.nilai(), null, null);
    }
}