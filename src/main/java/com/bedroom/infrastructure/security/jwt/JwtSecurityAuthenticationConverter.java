package com.bedroom.infrastructure.security.jwt;

import com.bedroom.application.security.PenggunaTerautentikasi;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.Collections;
import java.util.Objects;

/**
 * Menerjemahkan {@code Jwt} yang telah diverifikasi menjadi
 * {@code Authentication} yang dipahami Spring Security, dengan
 * {@code PenggunaTerautentikasi} sebagai principal.
 */
public final class JwtSecurityAuthenticationConverter implements
        Converter<Jwt, UsernamePasswordAuthenticationToken> {

    private final JwtPenggunaTerautentikasiConverter penggunaTerautentikasiConverter;

    public JwtSecurityAuthenticationConverter() {
        this.penggunaTerautentikasiConverter = new JwtPenggunaTerautentikasiConverter();
    }

    @Override
    public UsernamePasswordAuthenticationToken convert(Jwt jwt) {
        Objects.requireNonNull(
                jwt, "Jwt tidak boleh kosong"
        );
        PenggunaTerautentikasi penggunaTerautentikasi =
                penggunaTerautentikasiConverter.convert(jwt);

        return new UsernamePasswordAuthenticationToken(
                penggunaTerautentikasi,
                jwt,
                Collections.emptyList()
        );
    }
}
