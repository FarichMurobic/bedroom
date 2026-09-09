/*
 * Copyright (c) 2026 Farich Murobic
 *
 * This project is licensed under the MIT License.
 * See the LICENSE file in the project root for more information.
 */

package com.bedroom.infrastructure.persistence.repository;

import com.bedroom.domain.identitas.valueobject.IdPengguna;
import com.bedroom.domain.interaksi.model.Suka;
import com.bedroom.domain.interaksi.repository.RepositoriSuka;
import com.bedroom.domain.karya.valueobject.IdKarya;
import com.bedroom.infrastructure.persistence.entity.SukaEntity;
import com.bedroom.infrastructure.persistence.mapper.SukaMapper;
import org.springframework.stereotype.Repository;

import java.util.Objects;
import java.util.Optional;

/**
 * Implementasi repository {@code RepositoriSuka} menggunakan JPA.
 */
@Repository
public class RepositoriSukaImpl implements RepositoriSuka {

    private final JpaSukaRepository jpaSukaRepository;
    private final SukaMapper sukaMapper;

    public RepositoriSukaImpl(JpaSukaRepository jpaSukaRepository, SukaMapper sukaMapper) {
        this.jpaSukaRepository = Objects.requireNonNull(jpaSukaRepository, "Jpa suka repository tidak boleh kosong");
        this.sukaMapper = Objects.requireNonNull(sukaMapper, "Suka mapper tidak boleh kosong");
    }

    @Override
    public Suka simpan(Suka suka) {
        SukaEntity entity = sukaMapper.keEntity(suka);
        SukaEntity entityTersimpan = jpaSukaRepository.save(entity);
        return sukaMapper.keDomain(entityTersimpan);
    }

    @Override
    public Optional<Suka> cariBerdasarkanPenggunaDanKarya(IdPengguna idPengguna, IdKarya idKarya) {
        return jpaSukaRepository.findByPenggunaIdAndKaryaId(idPengguna.nilai(), idKarya.nilai())
                .map(sukaMapper::keDomain);
    }

    @Override
    public long hitungBerdasarkanKarya(IdKarya idKarya) {
        return jpaSukaRepository.countByKaryaId(idKarya.nilai());
    }

    @Override
    public void hapus(Suka suka) {
        jpaSukaRepository.deleteById(suka.id().nilai());
    }
}