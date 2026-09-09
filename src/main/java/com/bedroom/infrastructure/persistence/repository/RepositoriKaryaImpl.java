/*
 * Copyright (c) 2026 Farich Murobic
 *
 * This project is licensed under the MIT License.
 * See the LICENSE file in the project root for more information.
 */

package com.bedroom.infrastructure.persistence.repository;

import com.bedroom.domain.identitas.valueobject.IdPengguna;
import com.bedroom.domain.karya.enums.StatusKarya;
import com.bedroom.domain.karya.model.Karya;
import com.bedroom.domain.karya.repository.RepositoriKarya;
import com.bedroom.domain.karya.valueobject.IdKarya;
import com.bedroom.infrastructure.persistence.entity.KaryaEntity;
import com.bedroom.infrastructure.persistence.mapper.KaryaMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Implementasi repository {@code RepositoriKarya} menggunakan JPA.
 */
@Repository
public class RepositoriKaryaImpl implements RepositoriKarya {

    private final JpaKaryaRepository jpaKaryaRepository;
    private final KaryaMapper karyaMapper;

    public RepositoriKaryaImpl(JpaKaryaRepository jpaKaryaRepository, KaryaMapper karyaMapper) {
        this.jpaKaryaRepository = Objects.requireNonNull(jpaKaryaRepository, "Jpa karya repository tidak boleh kosong");
        this.karyaMapper = Objects.requireNonNull(karyaMapper, "Karya mapper tidak boleh kosong");
    }

    @Override
    public Karya simpan(Karya karya) {
        KaryaEntity entity = karyaMapper.keEntity(karya);
        KaryaEntity entityTersimpan = jpaKaryaRepository.save(entity);
        return karyaMapper.keDomain(entityTersimpan);
    }

    @Override
    public Optional<Karya> cariBerdasarkanId(IdKarya id) {
        return jpaKaryaRepository.findById(id.nilai())
                .map(karyaMapper::keDomain);
    }

    @Override
    public List<Karya> cariBerdasarkanPenulis(IdPengguna idPenulis) {
        return jpaKaryaRepository.findByPenulisId(idPenulis.nilai()).stream()
                .map(karyaMapper::keDomain)
                .toList();
    }

    @Override
    public List<Karya> cariBerdasarkanStatus(StatusKarya status) {
        return jpaKaryaRepository.findByStatus(status).stream()
                .map(karyaMapper::keDomain)
                .toList();
    }

    @Override
    public void hapusBerdasarkanId(IdKarya id) {
        jpaKaryaRepository.deleteById(id.nilai());
    }
}