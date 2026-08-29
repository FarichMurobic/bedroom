package com.bedroom.domain.identity.valueobject;

import java.util.Objects;
import java.util.UUID;

public record AuthenticationIdentityId(UUID value) {

    public AuthenticationIdentityId {
        Objects.requireNonNull(value, "Authentication identity ID cannot be null");
    }

    public static AuthenticationIdentityId generate() {
        return new AuthenticationIdentityId(UUID.randomUUID());
    }

    public static AuthenticationIdentityId of(UUID value) {
        return new AuthenticationIdentityId(value);
    }

    public static AuthenticationIdentityId of(String value) {
        Objects.requireNonNull(value, "Authentication identity ID cannot be null");
        String normalized = value.trim();

        if (normalized.isBlank()) {
            throw new IllegalArgumentException("Authentication identity ID cannot be blank");
        }

        try {
            return new AuthenticationIdentityId(UUID.fromString(normalized));
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException("Invalid authentication identity ID format", ex);
        }
    }
}
