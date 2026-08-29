package com.bedroom.domain.identity.valueobject;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class AuthenticationIdentityIdTest {

    @Test
    void shouldGenerateAuthenticationIdentityId() {
        AuthenticationIdentityId id =
                AuthenticationIdentityId.generate();

        assertNotNull(id);
        assertNotNull(id.value());
    }

    @Test
    void shouldCreateFromUuid() {
        UUID uuid = UUID.randomUUID();

        AuthenticationIdentityId id =
                AuthenticationIdentityId.of(uuid);

        assertEquals(uuid, id.value());
    }

    @Test
    void shouldCreateFromString() {
        UUID uuid = UUID.randomUUID();

        AuthenticationIdentityId id =
                AuthenticationIdentityId.of(uuid.toString());

        assertEquals(uuid, id.value());
    }

    @Test
    void shouldRejectNullUuid() {
        assertThrows(
                NullPointerException.class,
                () -> AuthenticationIdentityId.of((UUID) null)
        );
    }

    @Test
    void shouldRejectNullString() {
        assertThrows(
                NullPointerException.class,
                () -> AuthenticationIdentityId.of((String) null)
        );
    }

    @Test
    void shouldRejectBlankString() {
        assertThrows(
                IllegalArgumentException.class,
                () -> AuthenticationIdentityId.of("   ")
        );
    }

    @Test
    void shouldRejectInvalidUuidString() {
        assertThrows(
                IllegalArgumentException.class,
                () -> AuthenticationIdentityId.of("invalid-uuid")
        );
    }

    @Test
    void shouldTrimStringBeforeParsing() {
        UUID uuid = UUID.randomUUID();

        AuthenticationIdentityId id =
                AuthenticationIdentityId.of("  " + uuid + "  ");

        assertEquals(uuid, id.value());
    }
}