/*
 * Copyright (c) 2026 Farich Murobic
 *
 * This project is licensed under the MIT License.
 * See the LICENSE file in the project root for more information.
 */

package com.bedroom.infrastructure.persistence.mapper;

import com.bedroom.domain.identitas.model.Profil;
import com.bedroom.domain.identitas.valueobject.IdPengguna;
import com.bedroom.domain.identitas.valueobject.NamaLengkap;
import com.bedroom.domain.identitas.valueobject.TanggalLahir;
import com.bedroom.domain.identitas.valueobject.UrlAvatar;
import com.bedroom.infrastructure.persistence.entity.ProfilEntity;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * Menerjemahkan entity domain {@code Profil} ke representasi persistence
 * {@code ProfilEntity}, dan sebaliknya.
 */
@Component
public class ProfilMapper {

    public ProfilEntity keEntity(Profil profil) {
        Objects.requireNonNull(profil, "Profil tidak boleh kosong");
        return new ProfilEntity(
                profil.idPengguna().nilai(),
                profil.namaLengkap() != null ? profil.namaLengkap().nilai() : null,
                profil.tanggalLahir() != null ? profil.tanggalLahir().nilai() : null,
                profil.jenisKelamin(),
                profil.urlAvatar() != null ? profil.urlAvatar().nilai() : null,
                profil.diperbaruiPada()
        );
    }

    public Profil keDomain(ProfilEntity entity) {
        Objects.requireNonNull(entity, "Entity profil tidak boleh kosong");
        return Profil.rekonstruksi(
                IdPengguna.dari(entity.getPenggunaId()),
                entity.getNamaLengkap() != null ? new NamaLengkap(entity.getNamaLengkap()) : null,
                entity.getTanggalLahir() != null ? new TanggalLahir(entity.getTanggalLahir()) : null,
                entity.getJenisKelamin(),
                entity.getUrlAvatar() != null ? new UrlAvatar(entity.getUrlAvatar()) : null,
                entity.getDiperbaruiPada()
        );
    }
}