/*
 * Copyright (c) 2026 Farich Murobic
 *
 * This project is licensed under the MIT License.
 * See the LICENSE file in the project root for more information.
 */

package com.bedroom.infrastructure.persistence.mapper;

import com.bedroom.domain.identitas.model.Pengguna;
import com.bedroom.domain.identitas.valueobject.IdPengguna;
import com.bedroom.domain.identitas.valueobject.NamaPengguna;
import com.bedroom.infrastructure.persistence.entity.PenggunaEntity;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * Menerjemahkan entity domain {@code Pengguna} ke representasi persistence
 * {@code PenggunaEntity}, dan sebaliknya.
 */
@Component
public final class PenggunaMapper {

    public PenggunaEntity keEntity(Pengguna pengguna) {
        Objects.requireNonNull(pengguna, "Pengguna tidak boleh kosong");
        return new PenggunaEntity(
                pengguna.id().nilai(),
                pengguna.namaPengguna().nilai(),
                pengguna.status(),
                pengguna.perans(),
                pengguna.dibuatPada(),
                pengguna.diperbaruiPada(),
                pengguna.loginTerakhir()
        );
    }

    public Pengguna keDomain(PenggunaEntity entity) {
        Objects.requireNonNull(entity, "Entity pengguna tidak boleh kosong");
        return Pengguna.rekonstruksi(
                IdPengguna.dari(entity.getId()),
                new NamaPengguna(entity.getNamaPengguna()),
                entity.getStatus(),
                entity.getPerans(),
                entity.getDibuatPada(),
                entity.getDiperbaruiPada(),
                entity.getLoginTerakhir()
        );
    }
}