package com.bedroom.domain.identity.valueobject;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmailTest {

    @Test
    void shouldCreateValidEmail() {
        Email email = new Email("farich@example.com");

        assertEquals("farich@example.com", email.value());
    }

    @Test
    void shouldNormalizeEmail() {
        Email email = new Email("  FARICH@Example.COM  ");

        assertEquals("farich@example.com", email.value());
    }

    @Test
    void shouldRejectNullEmail() {
        assertThrows(
                NullPointerException.class,
                () -> new Email(null)
        );
    }

    @Test
    void shouldRejectBlankEmail() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Email("   ")
        );
    }

    @Test
    void shouldRejectEmailExceedingMaximumLength() {
        String email = "a".repeat(90) + "@example.com";

        assertThrows(
                IllegalArgumentException.class,
                () -> new Email(email)
        );
    }

    @Test
    void shouldRejectInvalidEmailFormat() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Email("invalid-email"));
    }

    @Test
    void shouldRejectEmailWithoutDomain() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Email("farich@"));
    }

    @Test
    void shouldRejectEmailWithoutLocalPart() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Email("@example.com"));
    }

}
