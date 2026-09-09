/*
 * Copyright (c) 2026 Farich Murobic
 *
 * This project is licensed under the MIT License.
 * See the LICENSE file in the project root for more information.
 */

package com.bedroom.infrastructure.persistence.repository;

import com.bedroom.domain.identitas.model.Pengguna;
import com.bedroom.domain.identitas.repository.RepositoriPengguna;
import com.bedroom.domain.identitas.valueobject.IdPengguna;
import com.bedroom.domain.identitas.valueobject.NamaPengguna;
import com.bedroom.infrastructure.persistence.entity.PenggunaEntity;
import com.bedroom.infrastructure.persistence.mapper.PenggunaMapper;
import org.springframework.stereotype.Repository;

import java.util.Objects;
import java.util.Optional;

/**
 * Implementasi {@code RepositoriPengguna} yang menyimpan data
 * menggunakan Spring Data JPA.
 */
@Repository
public class RepositoriPenggunaImpl implements RepositoriPengguna {

    private final JpaPenggunaRepository jpaPenggunaRepository;
    private final PenggunaMapper penggunaMapper;

    public RepositoriPenggunaImpl(
            JpaPenggunaRepository jpaPenggunaRepository,
            PenggunaMapper penggunaMapper
    ) {
        this.jpaPenggunaRepository = Objects.requireNonNull(
                jpaPenggunaRepository, "Jpa pengguna repository tidak boleh kosong");
        this.penggunaMapper = Objects.requireNonNull(
                penggunaMapper, "Pengguna mapper tidak boleh kosong");
    }

    @Override
    public Pengguna simpan(Pengguna pengguna) {
        PenggunaEntity entity = penggunaMapper.keEntity(pengguna);
        PenggunaEntity entityTersimpan = jpaPenggunaRepository.save(entity);

        return penggunaMapper.keDomain(entityTersimpan);
    }

    @Override
    public Optional<Pengguna> cariBerdasarkanId(IdPengguna id) {
        return jpaPenggunaRepository.findById(id.nilai())
                .map(penggunaMapper::keDomain);
    }

    @Override
    public Optional<Pengguna> cariBerdasarkanNamaPengguna(NamaPengguna namaPengguna) {
        return jpaPenggunaRepository.findByNamaPengguna(namaPengguna.nilai())
                .map(penggunaMapper::keDomain);
    }

    @Override
    public boolean adaBerdasarkanNamaPengguna(NamaPengguna namaPengguna) {
        return jpaPenggunaRepository.existsByNamaPengguna(namaPengguna.nilai());
    }

    @Override
    public void hapusBerdasarkanId(IdPengguna id) {
        jpaPenggunaRepository.deleteById(id.nilai());
    }
}