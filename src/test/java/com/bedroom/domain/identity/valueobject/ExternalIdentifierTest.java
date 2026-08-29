package com.bedroom.domain.identity.valueobject;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ExternalIdentifierTest {

    @Test
    void shouldCreateExternalIdentifier() {
        ExternalIdentifier identifier =
                new ExternalIdentifier("facebook-user-123");

        assertEquals("facebook-user-123", identifier.value());
    }

    @Test
    void shouldTrimExternalIdentifier() {
        ExternalIdentifier identifier =
                new ExternalIdentifier("  facebook-user-123  ");

        assertEquals("facebook-user-123", identifier.value());
    }

    @Test
    void shouldRejectNullExternalIdentifier() {
        assertThrows(
                NullPointerException.class,
                () -> new ExternalIdentifier(null)
        );
    }

    @Test
    void shouldRejectBlankExternalIdentifier() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new ExternalIdentifier("   ")
        );
    }

}
