package com.bedroom.infrastructure.security.jwt;

import com.bedroom.application.security.AuthenticatedUser;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.Collections;
import java.util.Objects;

public final class JwtSecurityAuthenticationConverter
        implements Converter<Jwt, UsernamePasswordAuthenticationToken> {

    private final JwtAuthenticatedUserConverter authenticatedUserConverter;

    public JwtSecurityAuthenticationConverter() {
        this.authenticatedUserConverter =
                new JwtAuthenticatedUserConverter();
    }

    @Override
    public UsernamePasswordAuthenticationToken convert(Jwt jwt) {
        Objects.requireNonNull(
                jwt,
                "JWT cannot be null"
        );

        AuthenticatedUser authenticatedUser =
                authenticatedUserConverter.convert(jwt);

        return new UsernamePasswordAuthenticationToken(
                authenticatedUser,
                jwt,
                Collections.emptyList()
        );
    }
}