package com.bedroom.infrastructure.persistence.repository;

import com.bedroom.domain.identity.model.User;
import com.bedroom.domain.identity.repository.UserRepository;
import com.bedroom.domain.identity.valueobject.UserId;
import com.bedroom.domain.identity.valueobject.Username;
import com.bedroom.infrastructure.persistence.mapper.UserMapper;
import org.springframework.stereotype.Repository;

import java.util.Objects;
import java.util.Optional;

@Repository
public class UserRepositoryImpl
        implements UserRepository {

    private final JpaUserRepository jpaUserRepository;
    private final UserMapper userMapper;

    public UserRepositoryImpl(
            JpaUserRepository jpaUserRepository,
            UserMapper userMapper
    ) {
        this.jpaUserRepository =
                Objects.requireNonNull(
                        jpaUserRepository,
                        "JPA user repository cannot be null"
                );

        this.userMapper =
                Objects.requireNonNull(
                        userMapper,
                        "User mapper cannot be null"
                );
    }

    @Override
    public User save(User user) {

        Objects.requireNonNull(
                user,
                "User cannot be null"
        );

        var entity = userMapper.toEntity(user);

        var savedEntity = jpaUserRepository.save(entity);

        return userMapper.toDomain(savedEntity);
    }

    @Override
    public Optional<User> findById(UserId userId) {

        Objects.requireNonNull(
                userId,
                "User ID cannot be null"
        );

        return jpaUserRepository
                .findById(userId.value())
                .map(userMapper::toDomain);
    }

    @Override
    public Optional<User> findByUsername(
            Username username
    ) {

        Objects.requireNonNull(
                username,
                "Username cannot be null"
        );

        return jpaUserRepository
                .findByUsername(username.value())
                .map(userMapper::toDomain);
    }

    @Override
    public boolean existsByUsername(
            Username username
    ) {

        Objects.requireNonNull(
                username,
                "Username cannot be null"
        );

        return jpaUserRepository
                .existsByUsername(username.value());
    }

    @Override
    public void deleteById(UserId userId) {

        Objects.requireNonNull(
                userId,
                "User ID cannot be null"
        );

        jpaUserRepository.deleteById(
                userId.value()
        );
    }
}