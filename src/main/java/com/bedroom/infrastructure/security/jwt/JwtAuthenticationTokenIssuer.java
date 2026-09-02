package com.bedroom.infrastructure.security.jwt;

import com.bedroom.application.identity.port.AuthenticationTokenIssuer;
import com.bedroom.domain.identity.valueobject.UserId;
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.time.Duration;
import java.time.Instant;
import java.util.Objects;

public final class JwtAuthenticationTokenIssuer implements AuthenticationTokenIssuer {

    private static final Duration TOKEN_LIFETIME = Duration.ofHours(1);
    private final JwtEncoder jwtEncoder;

    public JwtAuthenticationTokenIssuer(RSAPublicKey publicKey, RSAPrivateKey privateKey) {
        Objects.requireNonNull(publicKey, "Public key cannot be null");
        Objects.requireNonNull(privateKey, "Private key cannot be null");

        this.jwtEncoder = NimbusJwtEncoder
                .withKeyPair(publicKey, privateKey)
                .algorithm(SignatureAlgorithm.RS256)
                .build();
    }

    @Override
    public String issue(UserId userId) {
        Objects.requireNonNull(
                userId,
                "User ID cannot be null"
        );

        Instant issuedAt = Instant.now();
        Instant expiresAt = issuedAt.plus(TOKEN_LIFETIME);

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .subject(userId.value().toString())
                .issuedAt(issuedAt)
                .expiresAt(expiresAt)
                .build();

        return jwtEncoder
                .encode(
                        JwtEncoderParameters.from(claims)
                )
                .getTokenValue();
    }

}