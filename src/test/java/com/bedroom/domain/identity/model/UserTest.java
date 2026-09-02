package com.bedroom.domain.identity.model;

import com.bedroom.domain.identity.enums.Role;
import com.bedroom.domain.identity.enums.UserStatus;
import com.bedroom.domain.identity.valueobject.UserId;
import com.bedroom.domain.identity.valueobject.Username;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.EnumSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class UserTest {

    @Test
    void shouldCreateUserWithDefaultState() {
        Username username = new Username("Farich");
        User user = User.create(username);

        assertNotNull(user.id());
        assertEquals(username, user.username());
        assertEquals(UserStatus.ACTIVE, user.status());
        assertEquals(Set.of(Role.USER), user.roles());
        assertNotNull(user.createdAt());
        assertNotNull(user.updatedAt());
    }

    @Test
    void shouldChangeUsernameWhenUserIsActive() {
        User user = User.create(new Username("farich"));
        Username newUsername = new Username("bedroom");
        user.changeUsername(newUsername);

        assertEquals(newUsername, user.username());
    }

    @Test
    void shouldAddRole() {
        User user = User.create(new Username("farich"));
        user.addRole(Role.MODERATOR);

        assertTrue(user.roles().contains(Role.MODERATOR));
    }

    @Test
    void shouldRemoveRole() {
        User user = User.create(new Username("farich"));
        user.addRole(Role.MODERATOR);
        user.removeRole(Role.MODERATOR);

        assertEquals(Set.of(Role.USER), user.roles());
    }

    @Test
    void shouldNotAllowedRemovingLastRole() {
        User user = User.create(new Username("farich"));

        assertThrows(
                IllegalStateException.class,
                () -> user.removeRole(Role.USER));
    }

    @Test
    void shouldDeactivateUser() {
        User user = User.create(new Username("farich"));
        user.deactivate();

        assertEquals(UserStatus.INACTIVE, user.status());
    }

    @Test
    void shouldActivateUser() {
        User user = User.create(new Username("farich"));
        user.deactivate();
        user.activate();

        assertEquals(UserStatus.ACTIVE, user.status());
    }

    @Test
    void shouldSuspendUser() {
        User user = User.create(new Username("farich"));
        user.suspend();

        assertEquals(UserStatus.SUSPENDED, user.status());
    }

    @Test
    void shouldNotChangeUsernameWhenUserIsInActive() {
        User user = User.create(new Username("farich"));
        user.deactivate();

        assertThrows(
                IllegalStateException.class,
                () -> user.changeUsername(
                        new Username("bedroom")
                )
        );
    }

    @Test
    void shouldReconstituteUserFromExistingState() {

        UserId userId = UserId.generate();
        Username username = new Username("farich");
        UserStatus status = UserStatus.SUSPENDED;
        Set<Role> roles = EnumSet.of(
                Role.USER,
                Role.MODERATOR
        );
        Instant createdAt = Instant.parse(
                "2026-01-01T10:00:00Z"
        );
        Instant updatedAt = Instant.parse(
                "2026-02-01T10:00:00Z"
        );

        User user = User.reconstitute(
                userId,
                username,
                status,
                roles,
                createdAt,
                updatedAt
        );

        assertEquals(userId, user.id());
        assertEquals(username, user.username());
        assertEquals(status, user.status());
        assertEquals(roles, user.roles());
        assertEquals(createdAt, user.createdAt());
        assertEquals(updatedAt, user.updatedAt());
    }
}