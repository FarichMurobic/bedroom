package com.bedroom.infrastructure.persistence.repository;

import com.bedroom.domain.identitas.enums.PenyediaAutentikasi;
import com.bedroom.domain.identitas.model.IdentitasAutentikasi;
import com.bedroom.domain.identitas.repository.RepositoriIdentitasAutentikasi;
import com.bedroom.domain.identitas.valueobject.Email;
import com.bedroom.domain.identitas.valueobject.IdIdentitasAutentikasi;
import com.bedroom.domain.identitas.valueobject.NomorTelepon;
import com.bedroom.domain.identitas.valueobject.PengenalEksternal;
import com.bedroom.infrastructure.persistence.entity.IdentitasAutentikasiEntity;
import com.bedroom.infrastructure.persistence.mapper.IdentitasAutentikasiMapper;
import org.springframework.stereotype.Repository;

import java.util.Objects;
import java.util.Optional;

/**
 * Implementasi {@code RepositoriIdentitasAutentikasi} yang menyimpan data
 * menggunakan Spring Data JPA.
 */
@Repository
public class RepositoriIdentitasAutentikasiImpl implements RepositoriIdentitasAutentikasi {

    private final JpaIdentitasAutentikasiRepository jpaIdentitasAutentikasiRepository;
    private final IdentitasAutentikasiMapper identitasAutentikasiMapper;

    public RepositoriIdentitasAutentikasiImpl(
            JpaIdentitasAutentikasiRepository jpaIdentitasAutentikasiRepository,
            IdentitasAutentikasiMapper identitasAutentikasiMapper
    ) {
        this.jpaIdentitasAutentikasiRepository = Objects.requireNonNull(
                jpaIdentitasAutentikasiRepository,
                "Jpa identitas autentikasi repository tidak boleh kosong");
        this.identitasAutentikasiMapper = Objects.requireNonNull(
                identitasAutentikasiMapper,
                "Identitas autentikasi mapper tidak boleh kosong");
    }

    @Override
    public IdentitasAutentikasi simpan(IdentitasAutentikasi identitasAutentikasi) {
        IdentitasAutentikasiEntity entity = identitasAutentikasiMapper.keEntity(identitasAutentikasi);
        IdentitasAutentikasiEntity entityTersimpan = jpaIdentitasAutentikasiRepository.save(entity);

        return identitasAutentikasiMapper.keDomain(entityTersimpan);
    }

    @Override
    public Optional<IdentitasAutentikasi> cariBerdasarkanId(IdIdentitasAutentikasi id) {
        return jpaIdentitasAutentikasiRepository.findById(id.nilai())
                .map(identitasAutentikasiMapper::keDomain);
    }

    @Override
    public Optional<IdentitasAutentikasi> cariBerdasarkanEmail(Email email) {
        return jpaIdentitasAutentikasiRepository.findByEmail(email.nilai())
                .map(identitasAutentikasiMapper::keDomain);
    }

    @Override
    public Optional<IdentitasAutentikasi> cariBerdasarkanNomorTelepon(NomorTelepon nomorTelepon) {
        return jpaIdentitasAutentikasiRepository.findByNomorTelepon(nomorTelepon.nilai())
                .map(identitasAutentikasiMapper::keDomain);
    }

    @Override
    public Optional<IdentitasAutentikasi> cariBerdasarkanPenyediaDanPengenalEksternal(
            PenyediaAutentikasi penyediaAutentikasi,
            PengenalEksternal pengenalEksternal
    ) {
        return jpaIdentitasAutentikasiRepository
                .findByPenyediaAutentikasiAndPengenalEksternal(
                        penyediaAutentikasi,
                        pengenalEksternal.nilai())
                .map(identitasAutentikasiMapper::keDomain);
    }

    @Override
    public boolean adaBerdasarkanEmail(Email email) {
        return jpaIdentitasAutentikasiRepository.existsByEmail(email.nilai());
    }

    @Override
    public boolean adaBerdasarkanNomorTelepon(NomorTelepon nomorTelepon) {
        return jpaIdentitasAutentikasiRepository.existsByNomorTelepon(nomorTelepon.nilai());
    }

    @Override
    public boolean adaBerdasarkanPenyediaDanPengenalEksternal(
            PenyediaAutentikasi penyediaAutentikasi,
            PengenalEksternal pengenalEksternal
    ) {
        return jpaIdentitasAutentikasiRepository
                .existsByPenyediaAutentikasiAndPengenalEksternal(
                        penyediaAutentikasi,
                        pengenalEksternal.nilai());
    }

    @Override
    public void hapusBerdasarkanId(IdIdentitasAutentikasi id) {
        jpaIdentitasAutentikasiRepository.deleteById(id.nilai());
    }
}