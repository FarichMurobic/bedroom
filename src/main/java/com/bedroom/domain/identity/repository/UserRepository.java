package com.bedroom.domain.identity.repository;

import com.bedroom.domain.identity.model.User;
import com.bedroom.domain.identity.valueobject.UserId;
import com.bedroom.domain.identity.valueobject.Username;

import java.util.Optional;

public interface UserRepository {

    User save(User user);

    Optional<User> findById(UserId userId);

    Optional<User> findByUsername(Username username);

    boolean existsByUsername(Username username);

    void deleteById(UserId userId);

}
