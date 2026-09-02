package com.bedroom.infrastructure.security;

import com.bedroom.application.security.AuthenticatedUser;
import com.bedroom.application.security.AuthenticatedUserProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public final class SpringSecurityAuthenticatedUserProvider
        implements AuthenticatedUserProvider {

    @Override
    public AuthenticatedUser getAuthenticatedUser() {

        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        if (authentication == null
                || !authentication.isAuthenticated()) {
            throw new IllegalStateException(
                    "No authenticated user found"
            );
        }

        Object principal = authentication.getPrincipal();

        if (!(principal instanceof AuthenticatedUser authenticatedUser)) {
            throw new IllegalStateException(
                    "Authenticated principal is invalid"
            );
        }

        return authenticatedUser;
    }
}