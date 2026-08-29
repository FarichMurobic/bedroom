package com.bedroom.domain.identity.valueobject;

import java.util.Objects;

public record ExternalIdentifier(String value) {

    public ExternalIdentifier {
        Objects.requireNonNull(value, "External Identifier cannot be null");
        value = value.trim();

        if (value.isBlank()) {
            throw new IllegalArgumentException("External Identifier cannot be blank");
        }
    }
}
