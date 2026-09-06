/*
 * Copyright (c) 2026 Farich Murobic
 * Licensed under the MIT License.
 */
package com.bedroom.infrastructure.security.jwt;

import com.bedroom.application.security.PenggunaTerautentikasi;
import com.bedroom.domain.identitas.valueobject.IdPengguna;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.Objects;

/**
 * Menerjemahkan klaim {@code sub} dari sebuah {@code Jwt} menjadi
 * {@code PenggunaTerautentikasi}.
 */
public final class JwtPenggunaTerautentikasiConverter implements
        Converter<Jwt, PenggunaTerautentikasi> {

    @Override
    public PenggunaTerautentikasi convert(Jwt jwt) {
        Objects.requireNonNull(
                jwt, "Jwt tidak boleh kosong"
        );
        String subjek = jwt.getSubject();
        if (subjek == null || subjek.isBlank()) {
            throw new IllegalArgumentException(
                    "Subjek JWT tidak boleh kosong"
            );
        }

        return new PenggunaTerautentikasi(IdPengguna.dari(subjek));
    }
}
