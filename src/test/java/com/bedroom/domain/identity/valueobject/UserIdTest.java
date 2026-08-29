package com.bedroom.domain.identity.valueobject;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class UserIdTest {

    @Test
    void shouldGenerateUserId() {
        UserId id = UserId.generate();

        assertNotNull(id);
        assertNotNull(id.value());
    }

    @Test
    void shouldCreateFromUuid() {
        UUID uuid = UUID.randomUUID();

        UserId id = UserId.of(uuid);

        assertEquals(uuid, id.value());
    }

    @Test
    void shouldCreateFromString() {
        UUID uuid = UUID.randomUUID();

        UserId id = UserId.of(uuid.toString());

        assertEquals(uuid, id.value());
    }

    @Test
    void shouldRejectNullUuid() {
        assertThrows(
                NullPointerException.class,
                () -> UserId.of((UUID) null)
        );
    }

    @Test
    void shouldRejectNullString() {
        assertThrows(
                NullPointerException.class,
                () -> UserId.of((String) null)
        );
    }

    @Test
    void shouldRejectBlankString() {
        assertThrows(
                IllegalArgumentException.class,
                () -> UserId.of("   ")
        );
    }

    @Test
    void shouldRejectInvalidUuidString() {
        assertThrows(
                IllegalArgumentException.class,
                () -> UserId.of("invalid-uuid")
        );
    }

    @Test
    void shouldTrimStringBeforeParsing() {
        UUID uuid = UUID.randomUUID();

        UserId id =
                UserId.of("  " + uuid + "  ");

        assertEquals(uuid, id.value());
    }

}
