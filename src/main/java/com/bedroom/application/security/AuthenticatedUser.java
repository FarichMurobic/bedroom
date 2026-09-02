package com.bedroom.application.security;

import com.bedroom.domain.identity.valueobject.UserId;

import java.util.Objects;

public record AuthenticatedUser(
        UserId userId
) {

    public AuthenticatedUser {
        Objects.requireNonNull(userId,"User ID cannot be null");
    }
}
