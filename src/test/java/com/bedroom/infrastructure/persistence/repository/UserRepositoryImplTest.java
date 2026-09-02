package com.bedroom.infrastructure.persistence.repository;

import com.bedroom.domain.identity.enums.Role;
import com.bedroom.domain.identity.model.User;
import com.bedroom.domain.identity.valueobject.UserId;
import com.bedroom.domain.identity.valueobject.Username;
import com.bedroom.infrastructure.persistence.entity.UserEntity;
import com.bedroom.infrastructure.persistence.mapper.UserMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.EnumSet;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserRepositoryImplTest {

    @Mock
    private JpaUserRepository jpaUserRepository;

    private UserRepositoryImpl userRepository;

    private UserMapper userMapper;

    @BeforeEach
    void setUp() {
        userMapper = new UserMapper();

        userRepository = new UserRepositoryImpl(
                jpaUserRepository,
                userMapper
        );
    }

    @Test
    void shouldSaveUser() {

        User user = User.create(
                new Username("farich")
        );

        when(jpaUserRepository.save(any(UserEntity.class)))
                .thenAnswer(invocation ->
                        invocation.getArgument(0)
                );

        User result = userRepository.save(user);

        assertEquals(
                user.id(),
                result.id()
        );

        assertEquals(
                user.username(),
                result.username()
        );

        assertEquals(
                user.status(),
                result.status()
        );

        assertEquals(
                user.roles(),
                result.roles()
        );

        assertEquals(
                user.createdAt(),
                result.createdAt()
        );

        assertEquals(
                user.updatedAt(),
                result.updatedAt()
        );

        verify(jpaUserRepository)
                .save(any(UserEntity.class));
    }

    @Test
    void shouldFindUserById() {

        UserId userId = UserId.generate();

        UserEntity entity = new UserEntity(
                userId.value(),
                "farich",
                com.bedroom.domain.identity.enums.UserStatus.ACTIVE,
                EnumSet.of(Role.USER),
                Instant.now(),
                Instant.now()
        );

        when(jpaUserRepository.findById(userId.value()))
                .thenReturn(Optional.of(entity));

        Optional<User> result =
                userRepository.findById(userId);

        assertTrue(result.isPresent());

        assertEquals(
                userId,
                result.get().id()
        );

        assertEquals(
                "farich",
                result.get().username().value()
        );

        verify(jpaUserRepository)
                .findById(userId.value());
    }

    @Test
    void shouldReturnEmptyWhenUserDoesNotExistById() {

        UserId userId = UserId.generate();

        when(jpaUserRepository.findById(userId.value()))
                .thenReturn(Optional.empty());

        Optional<User> result =
                userRepository.findById(userId);

        assertTrue(result.isEmpty());

        verify(jpaUserRepository)
                .findById(userId.value());
    }

    @Test
    void shouldFindUserByUsername() {

        Username username =
                new Username("farich");

        UserEntity entity = new UserEntity(
                UUID.randomUUID(),
                username.value(),
                com.bedroom.domain.identity.enums.UserStatus.ACTIVE,
                EnumSet.of(Role.USER),
                Instant.now(),
                Instant.now()
        );

        when(jpaUserRepository.findByUsername(
                username.value()
        )).thenReturn(Optional.of(entity));

        Optional<User> result =
                userRepository.findByUsername(username);

        assertTrue(result.isPresent());

        assertEquals(
                username,
                result.get().username()
        );

        verify(jpaUserRepository)
                .findByUsername(username.value());
    }

    @Test
    void shouldReturnEmptyWhenUserDoesNotExistByUsername() {

        Username username =
                new Username("farich");

        when(jpaUserRepository.findByUsername(
                username.value()
        )).thenReturn(Optional.empty());

        Optional<User> result =
                userRepository.findByUsername(username);

        assertTrue(result.isEmpty());

        verify(jpaUserRepository)
                .findByUsername(username.value());
    }

    @Test
    void shouldCheckUsernameExistence() {

        Username username =
                new Username("farich");

        when(jpaUserRepository.existsByUsername(
                username.value()
        )).thenReturn(true);

        assertTrue(
                userRepository.existsByUsername(username)
        );

        verify(jpaUserRepository)
                .existsByUsername(username.value());
    }

    @Test
    void shouldReturnFalseWhenUsernameDoesNotExist() {

        Username username =
                new Username("farich");

        when(jpaUserRepository.existsByUsername(
                username.value()
        )).thenReturn(false);

        assertFalse(
                userRepository.existsByUsername(username)
        );

        verify(jpaUserRepository)
                .existsByUsername(username.value());
    }

    @Test
    void shouldDeleteUserById() {

        UserId userId = UserId.generate();

        userRepository.deleteById(userId);

        verify(jpaUserRepository)
                .deleteById(userId.value());
    }
}