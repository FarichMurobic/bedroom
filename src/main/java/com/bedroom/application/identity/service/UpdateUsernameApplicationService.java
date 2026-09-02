package com.bedroom.application.identity.service;

import com.bedroom.application.identity.command.UpdateUsernameCommand;
import com.bedroom.application.security.AuthenticatedUser;
import com.bedroom.application.security.AuthenticatedUserProvider;
import com.bedroom.domain.identity.model.User;
import com.bedroom.domain.identity.repository.UserRepository;
import com.bedroom.domain.identity.valueobject.UserId;
import com.bedroom.domain.identity.valueobject.Username;

import java.util.Objects;

public final class UpdateUsernameApplicationService {

    private final UserRepository userRepository;
    private final AuthenticatedUserProvider authenticatedUserProvider;

    public UpdateUsernameApplicationService(
            UserRepository userRepository,
            AuthenticatedUserProvider authenticatedUserProvider
    ) {
        this.userRepository = Objects.requireNonNull(
                userRepository,
                "User repository cannot be null"
        );
        this.authenticatedUserProvider = Objects.requireNonNull(
                authenticatedUserProvider,
                "Authenticated user provider cannot be null"
        );
    }

    public User execute(UpdateUsernameCommand command) {
        Objects.requireNonNull(
                command,
                "Update username command cannot be null"
        );

        Username username = new Username(command.username());
        AuthenticatedUser authenticatedUser =
                authenticatedUserProvider.getAuthenticatedUser();
        UserId userId = authenticatedUser.userId();
        User user = userRepository
                .findById(userId)
                .orElseThrow(() ->
                        new IllegalStateException("User not found"));

        if (userRepository.existsByUsername(username) && !user.username().equals(username)) {
            throw new IllegalStateException("Username is already taken");
        }

        user.changeUsername(username);

        return userRepository.save(user);
    }

}
