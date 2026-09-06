/*
 * Copyright (c) 2026 Farich Murobic
 * Licensed under the MIT License.
 */
package com.bedroom.infrastructure.security.jwt;

import com.bedroom.application.identitas.port.PenerbitTokenAutentikasi;
import com.bedroom.domain.identitas.valueobject.IdPengguna;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.time.Duration;
import java.time.Instant;
import java.util.Objects;

/**
 * Implementasi {@code PenerbitTokenAutentikasi} menggunakan JWT bertanda
 * tangan RSA (algoritma RS256).
 */
public final class JwtPenerbitTokenAutentikasi implements PenerbitTokenAutentikasi {

    private static final Duration MASA_BERLAKU_TOKEN = Duration.ofHours(1);
    private final JwtEncoder jwtEncoder;

    public JwtPenerbitTokenAutentikasi(
            RSAPublicKey kunciPublik,
            RSAPrivateKey kunciPrivate
    ) {
        Objects.requireNonNull(
                kunciPublik, "Kunci publik tidak boleh kosong"
        );
        Objects.requireNonNull(
                kunciPrivate, "Kunci privat tidak boleh kosong"
        );

        this.jwtEncoder = NimbusJwtEncoder
                .withKeyPair(kunciPublik, kunciPrivate)
                .build();
    }

    @Override
    public String terbitkan(IdPengguna idPengguna) {
        Objects.requireNonNull(
                idPengguna, "Id pengguna tidak boleh kosong"
        );
        Instant diterbitkanPada = Instant.now();
        Instant kadaluwarsaPada = diterbitkanPada.plus(MASA_BERLAKU_TOKEN);

        JwtClaimsSet klaim = JwtClaimsSet
                .builder()
                .subject(idPengguna.nilai().toString())
                .issuedAt(diterbitkanPada)
                .expiresAt(kadaluwarsaPada)
                .build();

        return jwtEncoder.encode(JwtEncoderParameters.from(klaim)).getTokenValue();
    }
}
