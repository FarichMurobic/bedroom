package com.bedroom.infrastructure.persistence.mapper;

import com.bedroom.domain.identity.enums.Role;
import com.bedroom.domain.identity.enums.UserStatus;
import com.bedroom.domain.identity.model.User;
import com.bedroom.domain.identity.valueobject.UserId;
import com.bedroom.domain.identity.valueobject.Username;
import com.bedroom.infrastructure.persistence.entity.UserEntity;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.EnumSet;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class UserMapperTest {

    private final UserMapper mapper = new UserMapper();

    @Test
    void shouldMapDomainToEntity() {

        User user = User.create(
                new Username("farich")
        );

        UserEntity entity = mapper.toEntity(user);

        assertEquals(
                user.id().value(),
                entity.getId()
        );

        assertEquals(
                user.username().value(),
                entity.getUsername()
        );

        assertEquals(
                user.status(),
                entity.getStatus()
        );

        assertEquals(
                user.roles(),
                entity.getRoles()
        );

        assertEquals(
                user.createdAt(),
                entity.getCreatedAt()
        );

        assertEquals(
                user.updatedAt(),
                entity.getUpdatedAt()
        );
    }

    @Test
    void shouldMapEntityToDomain() {

        UUID id = UUID.randomUUID();

        Instant createdAt = Instant.parse(
                "2026-01-01T10:00:00Z"
        );

        Instant updatedAt = Instant.parse(
                "2026-02-01T10:00:00Z"
        );

        Set<Role> roles = EnumSet.of(
                Role.USER,
                Role.MODERATOR
        );

        UserEntity entity = new UserEntity(
                id,
                "farich",
                UserStatus.SUSPENDED,
                roles,
                createdAt,
                updatedAt
        );

        User user = mapper.toDomain(entity);

        assertEquals(
                UserId.of(id),
                user.id()
        );

        assertEquals(
                new Username("farich"),
                user.username()
        );

        assertEquals(
                UserStatus.SUSPENDED,
                user.status()
        );

        assertEquals(
                roles,
                user.roles()
        );

        assertEquals(
                createdAt,
                user.createdAt()
        );

        assertEquals(
                updatedAt,
                user.updatedAt()
        );
    }

    @Test
    void shouldPreserveUserStateThroughRoundTrip() {

        User original = User.create(
                new Username("farich")
        );

        UserEntity entity = mapper.toEntity(original);
        User restored = mapper.toDomain(entity);

        assertEquals(original.id(), restored.id());
        assertEquals(original.username(), restored.username());
        assertEquals(original.status(), restored.status());
        assertEquals(original.roles(), restored.roles());
        assertEquals(original.createdAt(), restored.createdAt());
        assertEquals(original.updatedAt(), restored.updatedAt());
    }

    @Test
    void shouldRejectNullDomainUser() {

        assertThrows(
                IllegalArgumentException.class,
                () -> mapper.toEntity(null)
        );
    }

    @Test
    void shouldRejectNullEntity() {

        assertThrows(
                IllegalArgumentException.class,
                () -> mapper.toDomain(null)
        );
    }
}