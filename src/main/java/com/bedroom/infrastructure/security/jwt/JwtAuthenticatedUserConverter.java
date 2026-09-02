package com.bedroom.infrastructure.security.jwt;

import com.bedroom.application.security.AuthenticatedUser;
import com.bedroom.domain.identity.valueobject.UserId;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.Objects;

public final class JwtAuthenticatedUserConverter implements Converter<Jwt, AuthenticatedUser> {

    @Override
    public AuthenticatedUser convert(Jwt jwt) {
        Objects.requireNonNull(
                jwt,
                "JWT cannot be null"
        );

        String subject = jwt.getSubject();

        if (subject == null || subject.isBlank()) {
            throw new IllegalArgumentException(
                    "JWT subject cannot be null or blank"
            );
        }

        return new AuthenticatedUser(
                UserId.of(subject)
        );
    }
}
