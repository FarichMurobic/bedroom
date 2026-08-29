package com.bedroom.domain.identity.valueobject;

import java.util.Objects;

public record Username(String value) {

    private static final int MIN_LENGTH = 3;
    private static final int MAX_LENGTH = 30;

    public Username {
        Objects.requireNonNull(value, "Username cannot be null");
        value = value.trim();

        if (value.isBlank()) {
            throw new IllegalArgumentException("Username cannot be blank");
        }

        if (value.length() < MIN_LENGTH || value.length() > MAX_LENGTH) {
            throw new IllegalArgumentException(
                    "Username must be between %d and %d characters"
                            .formatted(MIN_LENGTH, MAX_LENGTH)
            );
        }
    }
}