package com.bedroom.domain.identity.valueobject;

import java.util.Objects;

public record PasswordHash(String value) {

    public PasswordHash {
        Objects.requireNonNull(value, "Password hash cannot be null");

        if (value.isBlank()) {
            throw new IllegalArgumentException("Password hash cannot be blank");
        }
    }
}