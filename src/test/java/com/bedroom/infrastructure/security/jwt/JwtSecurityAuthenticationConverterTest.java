package com.bedroom.infrastructure.security.jwt;

import com.bedroom.application.security.AuthenticatedUser;
import com.bedroom.domain.identity.valueobject.UserId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;

import java.time.Instant;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class JwtSecurityAuthenticationConverterTest {

    private JwtSecurityAuthenticationConverter converter;

    @BeforeEach
    void setUp() {
        converter = new JwtSecurityAuthenticationConverter();
    }

    @Test
    void shouldConvertJwtToAuthentication() {
        UUID uuid = UUID.randomUUID();

        Jwt jwt = Jwt.withTokenValue("test-token")
                .header("alg", "RS256")
                .subject(uuid.toString())
                .issuedAt(Instant.now())
                .expiresAt(Instant.now().plusSeconds(3600))
                .build();

        Authentication authentication =
                converter.convert(jwt);

        assertNotNull(authentication);
        assertTrue(authentication.isAuthenticated());

        assertInstanceOf(
                AuthenticatedUser.class,
                authentication.getPrincipal()
        );

        AuthenticatedUser authenticatedUser =
                (AuthenticatedUser) authentication.getPrincipal();

        assertEquals(
                UserId.of(uuid),
                authenticatedUser.userId()
        );
    }

    @Test
    void shouldUseJwtAsCredentials() {
        Jwt jwt = Jwt.withTokenValue("test-token")
                .header("alg", "RS256")
                .subject(UUID.randomUUID().toString())
                .issuedAt(Instant.now())
                .expiresAt(Instant.now().plusSeconds(3600))
                .build();

        Authentication authentication =
                converter.convert(jwt);

        assertSame(
                jwt,
                authentication.getCredentials()
        );
    }

    @Test
    void shouldHaveNoAuthoritiesInitially() {
        Jwt jwt = Jwt.withTokenValue("test-token")
                .header("alg", "RS256")
                .subject(UUID.randomUUID().toString())
                .issuedAt(Instant.now())
                .expiresAt(Instant.now().plusSeconds(3600))
                .build();

        Authentication authentication =
                converter.convert(jwt);

        assertTrue(
                authentication.getAuthorities().isEmpty()
        );
    }

    @Test
    void shouldRejectJwtWithoutSubject() {
        Jwt jwt = Jwt.withTokenValue("test-token")
                .header("alg", "RS256")
                .issuedAt(Instant.now())
                .expiresAt(Instant.now().plusSeconds(3600))
                .build();

        assertThrows(
                IllegalArgumentException.class,
                () -> converter.convert(jwt)
        );
    }

    @Test
    void shouldRejectJwtWithInvalidSubject() {
        Jwt jwt = Jwt.withTokenValue("test-token")
                .header("alg", "RS256")
                .subject("not-a-valid-user-id")
                .issuedAt(Instant.now())
                .expiresAt(Instant.now().plusSeconds(3600))
                .build();

        assertThrows(
                IllegalArgumentException.class,
                () -> converter.convert(jwt)
        );
    }

    @Test
    void shouldRejectNullJwt() {
        assertThrows(
                NullPointerException.class,
                () -> converter.convert(null)
        );
    }
}