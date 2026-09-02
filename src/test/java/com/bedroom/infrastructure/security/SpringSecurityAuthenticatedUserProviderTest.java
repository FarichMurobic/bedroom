package com.bedroom.infrastructure.security;

import com.bedroom.application.security.AuthenticatedUser;
import com.bedroom.domain.identity.valueobject.UserId;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collections;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class SpringSecurityAuthenticatedUserProviderTest {

    private SpringSecurityAuthenticatedUserProvider provider;

    @BeforeEach
    void setUp() {
        provider = new SpringSecurityAuthenticatedUserProvider();
        SecurityContextHolder.clearContext();
    }

    @AfterEach
    void tearDown() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void shouldReturnAuthenticatedUserFromSecurityContext() {
        UserId userId = UserId.generate();

        AuthenticatedUser authenticatedUser =
                new AuthenticatedUser(userId);

        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(
                        authenticatedUser,
                        "test-token",
                        Collections.emptyList()
                );

        SecurityContextHolder
                .getContext()
                .setAuthentication(authentication);

        AuthenticatedUser result =
                provider.getAuthenticatedUser();

        assertNotNull(result);
        assertEquals(userId, result.userId());
    }

    @Test
    void shouldRejectWhenAuthenticationIsMissing() {
        assertThrows(
                IllegalStateException.class,
                () -> provider.getAuthenticatedUser()
        );
    }

    @Test
    void shouldRejectWhenAuthenticationIsNotAuthenticated() {
        UserId userId = UserId.generate();

        AuthenticatedUser authenticatedUser =
                new AuthenticatedUser(userId);

        UsernamePasswordAuthenticationToken authentication =
                UsernamePasswordAuthenticationToken.unauthenticated(
                        authenticatedUser,
                        "test-token"
                );

        SecurityContextHolder
                .getContext()
                .setAuthentication(authentication);

        assertThrows(
                IllegalStateException.class,
                () -> provider.getAuthenticatedUser()
        );
    }

    @Test
    void shouldRejectInvalidPrincipal() {
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(
                        "invalid-principal",
                        "test-token",
                        Collections.emptyList()
                );

        SecurityContextHolder
                .getContext()
                .setAuthentication(authentication);

        assertThrows(
                IllegalStateException.class,
                () -> provider.getAuthenticatedUser()
        );
    }
}