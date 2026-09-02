package com.bedroom.infrastructure.security.jwt;

import com.bedroom.domain.identity.valueobject.UserId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.time.Instant;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class JwtAuthenticationTokenIssuerTest {

    private KeyPair keyPair;
    private JwtAuthenticationTokenIssuer tokenIssuer;
    private JwtDecoder jwtDecoder;

    @BeforeEach
    void setUp() throws Exception {
        KeyPairGenerator keyPairGenerator =
                KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);
        keyPair = keyPairGenerator.generateKeyPair();
        RSAPublicKey publicKey =
                (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey =
                (RSAPrivateKey) keyPair.getPrivate();

        tokenIssuer = new JwtAuthenticationTokenIssuer(
                publicKey,
                privateKey
        );

        jwtDecoder = NimbusJwtDecoder
                .withPublicKey(publicKey)
                .build();
    }

    @Test
    void shouldIssueValidJwtContainingUserIdAsSubject() {
        UserId userId = UserId.generate();
        String token = tokenIssuer.issue(userId);

        assertNotNull(token);
        assertFalse(token.isBlank());

        Jwt jwt = jwtDecoder.decode(token);

        assertEquals(
                userId.value().toString(),
                jwt.getSubject()
        );
    }

    @Test
    void shouldIncludeIssuedAtClaim() {
        UserId userId = UserId.generate();

        String token = tokenIssuer.issue(userId);

        Jwt jwt = jwtDecoder.decode(token);

        assertNotNull(jwt.getIssuedAt());
    }

    @Test
    void shouldIncludeExpirationClaim() {
        UserId userId = UserId.generate();
        String token = tokenIssuer.issue(userId);
        Jwt jwt = jwtDecoder.decode(token);

        assertNotNull(jwt.getExpiresAt());
        assertTrue(jwt.getExpiresAt().isAfter(jwt.getIssuedAt()));
    }

    @Test
    void shouldRejectNullUserId() {
        assertThrows(
                NullPointerException.class,
                () -> tokenIssuer.issue(null)
        );
    }
}