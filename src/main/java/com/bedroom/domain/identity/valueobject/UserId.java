package com.bedroom.domain.identity.valueobject;

import java.util.Objects;
import java.util.UUID;

public record UserId(UUID value) {

    public UserId {
        Objects.requireNonNull(value, "User ID cannot be null");
    }

    public static UserId generate() {
        return new UserId(UUID.randomUUID());
    }

    public static UserId of(UUID value) {
        return new UserId(value);
    }

    public static UserId of(String value) {
        Objects.requireNonNull(value, "User ID cannot be null");

        String normalized = value.trim();

        if (normalized.isBlank()) {
            throw new IllegalArgumentException("User ID cannot be blank");
        }

        try {
            return new UserId(UUID.fromString(normalized));
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException(
                    "Invalid user ID format",
                    ex
            );
        }
    }
}