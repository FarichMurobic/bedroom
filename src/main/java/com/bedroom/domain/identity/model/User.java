package com.bedroom.domain.identity.model;

import com.bedroom.domain.identity.enums.Role;
import com.bedroom.domain.identity.enums.UserStatus;
import com.bedroom.domain.identity.valueobject.UserId;
import com.bedroom.domain.identity.valueobject.Username;

import java.time.Instant;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;

public final class User {

    private final UserId id;
    private Username username;
    private UserStatus status;
    private final Set<Role> roles;
    private final Instant createdAt;
    private Instant updatedAt;

    private User(
            UserId id,
            Username username,
            UserStatus status,
            Set<Role> roles,
            Instant createdAt,
            Instant updatedAt
    ) {
        this.id = id;
        this.username = username;
        this.status = status;
        this.roles = EnumSet.copyOf(roles);
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static User create(Username username) {
        Instant now = Instant.now();

        return new User(
                UserId.generate(),
                username,
                UserStatus.ACTIVE,
                EnumSet.of(Role.USER),
                now,
                now
        );
    }

    public void changeUsername(Username username) {
        ensureActive();
        this.username = username;
        touch();
    }

    public void activate() {
        if (status == UserStatus.ACTIVE) {
            return;
        }

        this.status = UserStatus.ACTIVE;
        touch();
    }

    public void deactivate() {
        if (status == UserStatus.INACTIVE) {
            return;
        }

        this.status = UserStatus.INACTIVE;
        touch();
    }

    public void suspend() {
        if (status == UserStatus.SUSPENDED) {
            return;
        }

        this.status = UserStatus.SUSPENDED;
        touch();
    }

    public void addRole(Role role) {
        if (role == null) {
            throw new IllegalArgumentException("Role cannot be null");
        }

        if (!roles.add(role)) {
            return;
        }

        touch();
    }

    public void removeRole(Role role) {
        if (role == null) {
            throw new IllegalArgumentException("Role cannot be null");
        }

        if (roles.size() == 1) {
            throw new IllegalStateException(
                    "User must have at least one role"
            );
        }

        if (!roles.remove(role)) {
            return;
        }

        touch();
    }

    private void ensureActive() {
        if (status != UserStatus.ACTIVE) {
            throw new IllegalStateException(
                    "User must be active to perform this operation"
            );
        }
    }

    private void touch() {
        updatedAt = Instant.now();
    }

    public UserId id() {
        return id;
    }

    public Username username() {
        return username;
    }

    public UserStatus status() {
        return status;
    }

    public Set<Role> roles() {
        return Collections.unmodifiableSet(roles);
    }

    public Instant createdAt() {
        return createdAt;
    }

    public Instant updatedAt() {
        return updatedAt;
    }

}