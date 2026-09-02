package com.bedroom.infrastructure.persistence.entity;

import com.bedroom.domain.identity.enums.Role;
import com.bedroom.domain.identity.enums.UserStatus;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.EnumSet;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class UserEntityTest {

    @Test
    void shouldCreateUserEntity() {

        UUID id = UUID.randomUUID();
        String username = "farich";
        UserStatus status = UserStatus.ACTIVE;
        Set<Role> roles = EnumSet.of(Role.USER);
        Instant createdAt = Instant.now();
        Instant updatedAt = createdAt;

        UserEntity entity = new UserEntity(
                id,
                username,
                status,
                roles,
                createdAt,
                updatedAt
        );

        assertEquals(id, entity.getId());
        assertEquals(username, entity.getUsername());
        assertEquals(status, entity.getStatus());
        assertEquals(roles, entity.getRoles());
        assertEquals(createdAt, entity.getCreatedAt());
        assertEquals(updatedAt, entity.getUpdatedAt());
    }

    @Test
    void shouldKeepMultipleRoles() {

        Set<Role> roles = EnumSet.of(
                Role.USER,
                Role.MODERATOR
        );

        UserEntity entity = new UserEntity(
                UUID.randomUUID(),
                "farich",
                UserStatus.ACTIVE,
                roles,
                Instant.now(),
                Instant.now()
        );

        assertEquals(2, entity.getRoles().size());
        assertTrue(entity.getRoles().contains(Role.USER));
        assertTrue(entity.getRoles().contains(Role.MODERATOR));
    }

    @Test
    void shouldCopyRolesInsteadOfKeepingOriginalReference() {

        Set<Role> roles = EnumSet.of(Role.USER);

        UserEntity entity = new UserEntity(
                UUID.randomUUID(),
                "farich",
                UserStatus.ACTIVE,
                roles,
                Instant.now(),
                Instant.now()
        );

        roles.add(Role.ADMIN);

        assertEquals(
                Set.of(Role.USER),
                entity.getRoles()
        );
    }

    @Test
    void shouldAllowJpaToCreateEntityUsingProtectedConstructor() {

        UserEntity entity = new UserEntity();

        assertNotNull(entity);
    }
}