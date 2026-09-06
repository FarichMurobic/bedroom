package com.bedroom.infrastructure.persistence.mapper;

import com.bedroom.domain.identitas.model.IdentitasAutentikasi;
import com.bedroom.domain.identitas.valueobject.*;
import com.bedroom.infrastructure.persistence.entity.IdentitasAutentikasiEntity;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * Menerjemahkan entity domain {@code IdentitasAutentikasi} ke representasi
 * persistence {@code IdentitasAutentikasiEntity}, dan sebaliknya.
 */
@Component
public class IdentitasAutentikasiMapper {

    public IdentitasAutentikasiEntity keEntity(IdentitasAutentikasi identitasAutentikasi) {
        Objects.requireNonNull(
                identitasAutentikasi, "Identitas autentikasi tidak boleh kosong"
        );

        return new IdentitasAutentikasiEntity(
                identitasAutentikasi.id().nilai(),
                identitasAutentikasi.idPengguna().nilai(),
                identitasAutentikasi.penyediaAutentikasi(),
                nilaiAtauNull(identitasAutentikasi.email(), Email::nilai),
                nilaiAtauNull(identitasAutentikasi.nomorTelepon(), NomorTelepon::nilai),
                nilaiAtauNull(identitasAutentikasi.pengenalEksternal(), PengenalEksternal::nilai),
                nilaiAtauNull(identitasAutentikasi.hashKataSandi(), HashKataSandi::nilai),
                identitasAutentikasi.dibuatPada(),
                identitasAutentikasi.diperbaruiPada()
        );
    }

    public IdentitasAutentikasi keDomain(IdentitasAutentikasiEntity entity) {
        Objects.requireNonNull(
                entity, "Entity identitas autentikasi tidak boleh kosong"
        );

        return IdentitasAutentikasi.rekonstruksi(
                IdIdentitasAutentikasi.dari(entity.getId()),
                IdPengguna.dari(entity.getPenggunaId()),
                entity.getPenyediaAutentikasi(),
                entity.getEmail() != null ? new Email(entity.getEmail()) : null,
                entity.getNomorTelepon() != null ? new NomorTelepon(entity.getNomorTelepon()) : null,
                entity.getPengenalEksternal() != null ? new PengenalEksternal(entity.getPengenalEksternal()) : null,
                entity.getHashKataSandi() != null ? new HashKataSandi(entity.getHashKataSandi()) : null,
                entity.getDibuatPada(),
                entity.getDiperbaruiPada()
        );
    }

    private <T> String nilaiAtauNull(
            T valueObject,
            java.util.function.Function<T, String> ekstrak

    ) {
        return valueObject != null ? ekstrak.apply(valueObject) : null;
    }
}
