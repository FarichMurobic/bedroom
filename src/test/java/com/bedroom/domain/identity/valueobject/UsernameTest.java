package com.bedroom.domain.identity.valueobject;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UsernameTest {

    @Test
    void shouldCreateValidUsername() {
        Username username = new Username("farich");

        assertEquals("farich", username.value());
    }

    @Test
    void shouldTrimUsername() {
        Username username = new Username("  farich  ");

        assertEquals("farich", username.value());
    }

    @Test
    void shouldRejectNullUsername() {
        assertThrows(
                NullPointerException.class,
                () -> new Username(null)
        );
    }

    @Test
    void shouldRejectBlankUsername() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Username("   ")
        );
    }

    @Test
    void shouldRejectUsernameShorterThanMinimumLength() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Username("ab")
        );
    }

    @Test
    void shouldRejectUsernameLongerThanMaximumLength() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Username("a".repeat(31))
        );
    }

    @Test
    void shouldAcceptUsernameWithMinimumLength() {
        Username username = new Username("abc");

        assertEquals("abc", username.value());
    }

    @Test
    void shouldAcceptUsernameWithMaximumLength() {
        String value = "a".repeat(30);

        Username username = new Username(value);

        assertEquals(value, username.value());
    }

}
