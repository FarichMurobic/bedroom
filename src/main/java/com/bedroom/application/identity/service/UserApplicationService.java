package com.bedroom.application.identity.service;

import com.bedroom.application.identity.command.LoginUserCommand;
import com.bedroom.application.identity.command.RegisterUserCommand;
import com.bedroom.application.identity.port.AuthenticationTokenIssuer;
import com.bedroom.application.identity.port.PasswordHasher;
import com.bedroom.application.identity.port.PasswordVerifier;
import com.bedroom.application.identity.result.AuthenticationResult;
import com.bedroom.domain.identity.model.AuthenticationIdentity;
import com.bedroom.domain.identity.model.User;
import com.bedroom.domain.identity.repository.AuthenticationIdentityRepository;
import com.bedroom.domain.identity.repository.UserRepository;
import com.bedroom.domain.identity.valueobject.Email;
import com.bedroom.domain.identity.valueobject.PasswordHash;
import com.bedroom.domain.identity.valueobject.Username;

import java.util.Objects;

public final class UserApplicationService {

    private final UserRepository userRepository;
    private final AuthenticationIdentityRepository authenticationIdentityRepository;
    private final PasswordHasher passwordHasher;
    private final PasswordVerifier passwordVerifier;
    private final AuthenticationTokenIssuer authenticationTokenIssuer;

    public UserApplicationService(
            UserRepository userRepository,
            AuthenticationIdentityRepository authenticationIdentityRepository,
            PasswordHasher passwordHasher,
            PasswordVerifier passwordVerifier,
            AuthenticationTokenIssuer authenticationTokenIssuer
    ) {
        this.userRepository = Objects.requireNonNull(userRepository);
        this.authenticationIdentityRepository = Objects.requireNonNull(authenticationIdentityRepository);
        this.passwordHasher = Objects.requireNonNull(passwordHasher);
        this.passwordVerifier = Objects.requireNonNull(passwordVerifier);
        this.authenticationTokenIssuer = Objects.requireNonNull(authenticationTokenIssuer);
    }

    public User register(RegisterUserCommand command) {
        Objects.requireNonNull(
                command,
                "Register user command cannot be null"
        );

        Username username = new Username(command.username());
        Email email = new Email(command.email());

        if (userRepository.existsByUsername(username)) {
            throw new IllegalArgumentException("Username already exists");
        }

        if (authenticationIdentityRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email already exists");
        }

        PasswordHash passwordHash = passwordHasher.hash(command.password());
        User user = User.create(username);
        AuthenticationIdentity authenticationIdentity =
                AuthenticationIdentity.forEmail(
                        user.id(),
                        email,
                        passwordHash
                );

        userRepository.save(user);
        authenticationIdentityRepository.save(authenticationIdentity);

        return user;
    }

    public AuthenticationResult login(LoginUserCommand command) {
        Objects.requireNonNull(command, "Login user command cannot be null");

        Email email = new Email(command.email());

        AuthenticationIdentity identity =
                authenticationIdentityRepository.findByEmail(email)
                        .orElseThrow(() ->
                                new IllegalArgumentException(
                                        "Invalid email or password"
                                )
                        );

        boolean passwordMatches =
                passwordVerifier.matches(
                        command.password(),
                        identity.passwordHash()
                );

        if (!passwordMatches) {
            throw new IllegalArgumentException(
                    "Invalid email or password"
            );
        }

        User user = userRepository.findById(identity.userId())
                .orElseThrow(() ->
                        new IllegalStateException(
                                "User associated with authentication identity not found"
                        )
                );

        String accessToken = authenticationTokenIssuer.issue(user.id());

        return new AuthenticationResult(
                user.id(),
                accessToken
        );
    }

}