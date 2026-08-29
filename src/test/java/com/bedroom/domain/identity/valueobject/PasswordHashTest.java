package com.bedroom.domain.identity.valueobject;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PasswordHashTest {

    @Test
    void shouldCreatePasswordHash() {
        PasswordHash passwordHash = new PasswordHash("hashed-password");

        assertEquals("hashed-password", passwordHash.value());
    }

    @Test
    void shouldRejectNullPasswordHash() {
        assertThrows(
                NullPointerException.class,
                () -> new PasswordHash(null)
        );
    }

    @Test
    void shouldRejectBlankPasswordHash() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new PasswordHash("  ")
        );
    }

}