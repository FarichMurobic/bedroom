/*
 * Copyright (c) 2026 Farich Murobic
 *
 * This project is licensed under the MIT License.
 * See the LICENSE file in the project root for more information.
 */

package com.bedroom.infrastructure.persistence.mapper;

import com.bedroom.domain.identitas.valueobject.IdPengguna;
import com.bedroom.domain.interaksi.model.Suka;
import com.bedroom.domain.interaksi.valueobject.IdSuka;
import com.bedroom.domain.karya.valueobject.IdKarya;
import com.bedroom.infrastructure.persistence.entity.SukaEntity;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * Menerjemahkan entity domain {@code Suka} ke representasi persistence
 * {@code SukaEntity}, dan sebaliknya.
 */
@Component
public class SukaMapper {

    public SukaEntity keEntity(Suka suka) {
        Objects.requireNonNull(suka, "Suka tidak boleh kosong");
        return new SukaEntity(
                suka.id().nilai(),
                suka.idPengguna().nilai(),
                suka.idKarya().nilai(),
                suka.disukaiPada()
        );
    }

    public Suka keDomain(SukaEntity entity) {
        Objects.requireNonNull(entity, "Entity suka tidak boleh kosong");
        return Suka.rekonstruksi(
                IdSuka.dari(entity.getId()),
                IdPengguna.dari(entity.getPenggunaId()),
                IdKarya.dari(entity.getKaryaId()),
                entity.getDisukaiPada()
        );
    }
}