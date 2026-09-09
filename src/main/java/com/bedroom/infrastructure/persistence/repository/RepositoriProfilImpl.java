/*
 * Copyright (c) 2026 Farich Murobic
 *
 * This project is licensed under the MIT License.
 * See the LICENSE file in the project root for more information.
 */

package com.bedroom.infrastructure.persistence.repository;

import com.bedroom.domain.identitas.model.Profil;
import com.bedroom.domain.identitas.repository.RepositoriProfil;
import com.bedroom.domain.identitas.valueobject.IdPengguna;
import com.bedroom.infrastructure.persistence.entity.ProfilEntity;
import com.bedroom.infrastructure.persistence.mapper.ProfilMapper;
import org.springframework.stereotype.Repository;

import java.util.Objects;
import java.util.Optional;

/**
 * Implementasi repository {@code RepositoriProfil} menggunakan JPA.
 */
@Repository
public class RepositoriProfilImpl implements RepositoriProfil {

    private final JpaProfilRepository jpaProfilRepository;
    private final ProfilMapper profilMapper;

    public RepositoriProfilImpl(JpaProfilRepository jpaProfilRepository, ProfilMapper profilMapper) {
        this.jpaProfilRepository = Objects.requireNonNull(jpaProfilRepository, "Jpa profil repository tidak boleh kosong");
        this.profilMapper = Objects.requireNonNull(profilMapper, "Profil mapper tidak boleh kosong");
    }

    @Override
    public Profil simpan(Profil profil) {
        ProfilEntity entity = profilMapper.keEntity(profil);
        ProfilEntity entityTersimpan = jpaProfilRepository.save(entity);
        return profilMapper.keDomain(entityTersimpan);
    }

    @Override
    public Optional<Profil> cariBerdasarkanIdPengguna(IdPengguna idPengguna) {
        return jpaProfilRepository.findById(idPengguna.nilai())
                .map(profilMapper::keDomain);
    }
}