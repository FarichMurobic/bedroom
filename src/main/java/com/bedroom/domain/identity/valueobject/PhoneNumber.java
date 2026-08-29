package com.bedroom.domain.identity.valueobject;

import java.util.Objects;

public record PhoneNumber(String value) {

    public PhoneNumber {
        Objects.requireNonNull(value, "Phone number cannot be null");
        value = value.trim();

        if (value.isBlank()) {
            throw new IllegalArgumentException("Phone number cannot be blank");
        }

        if (!value.matches("^\\+[1-9]\\d{7,14}$")) {
            throw new IllegalArgumentException(
                    "Phone number must be in international E.164 format"
            );
        }
    }
}