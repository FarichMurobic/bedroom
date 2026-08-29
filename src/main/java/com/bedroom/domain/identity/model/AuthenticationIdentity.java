package com.bedroom.domain.identity.model;

import com.bedroom.domain.identity.enums.AuthenticationProvider;
import com.bedroom.domain.identity.valueobject.*;

import java.time.Instant;

public final class AuthenticationIdentity {

    private final AuthenticationIdentityId id;
    private final UserId userId;
    private final AuthenticationProvider provider;
    private final Email email;
    private final PhoneNumber phoneNumber;
    private final ExternalIdentifier externalIdentifier;
    private PasswordHash passwordHash;
    private final Instant createdAt;
    private Instant updatedAt;

    private AuthenticationIdentity(
            AuthenticationIdentityId id,
            UserId userId,
            AuthenticationProvider provider,
            Email email,
            PhoneNumber phoneNumber,
            ExternalIdentifier externalIdentifier,
            PasswordHash passwordHash,
            Instant createdAt,
            Instant updatedAt
    ) {
        this.id = id;
        this.userId = userId;
        this.provider = provider;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.externalIdentifier = externalIdentifier;
        this.passwordHash = passwordHash;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static AuthenticationIdentity forEmail(
            UserId userId,
            Email email,
            PasswordHash passwordHash
    ) {
        Instant now = Instant.now();

        return new AuthenticationIdentity(
                AuthenticationIdentityId.generate(),
                userId,
                AuthenticationProvider.EMAIL,
                email,
                null,
                null,
                passwordHash,
                now,
                now
        );
    }

    public static AuthenticationIdentity forPhone(
            UserId userId,
            PhoneNumber phoneNumber,
            PasswordHash passwordHash
    ) {
        Instant now = Instant.now();

        return new AuthenticationIdentity(
                AuthenticationIdentityId.generate(),
                userId,
                AuthenticationProvider.PHONE,
                null,
                phoneNumber,
                null,
                passwordHash,
                now,
                now
        );
    }

    public static AuthenticationIdentity forFacebook(
            UserId userId,
            ExternalIdentifier externalIdentifier
    ) {
        Instant now = Instant.now();

        return new AuthenticationIdentity(
                AuthenticationIdentityId.generate(),
                userId,
                AuthenticationProvider.FACEBOOK,
                null,
                null,
                externalIdentifier,
                null,
                now,
                now
        );
    }

    public void changePassword(PasswordHash passwordHash) {
        if (provider == AuthenticationProvider.FACEBOOK) {
            throw new IllegalArgumentException(
                    "Facebook authentication does not use a Bedroom password"
            );
        }

        this.passwordHash = passwordHash;
        this.updatedAt = Instant.now();
    }

    public AuthenticationIdentityId id() {
        return id;
    }

    public UserId userId() {
        return userId;
    }

    public AuthenticationProvider provider() {
        return provider;
    }

    public Email email() {
        return email;
    }

    public PhoneNumber phoneNumber() {
        return phoneNumber;
    }

    public ExternalIdentifier externalIdentifier() {
        return externalIdentifier;
    }

    public PasswordHash passwordHash() {
        return passwordHash;
    }

    public Instant createdAt() {
        return createdAt;
    }

    public Instant updatedAt() {
        return updatedAt;
    }

}
