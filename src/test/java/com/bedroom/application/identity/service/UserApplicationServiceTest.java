package com.bedroom.application.identity.service;

import com.bedroom.application.identity.command.RegisterUserCommand;
import com.bedroom.application.identity.port.PasswordHasher;
import com.bedroom.domain.identity.model.AuthenticationIdentity;
import com.bedroom.domain.identity.model.User;
import com.bedroom.domain.identity.repository.AuthenticationIdentityRepository;
import com.bedroom.domain.identity.repository.UserRepository;
import com.bedroom.domain.identity.valueobject.Email;
import com.bedroom.domain.identity.valueobject.PasswordHash;
import com.bedroom.domain.identity.valueobject.Username;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserApplicationServiceTest {

    private UserRepository userRepository;
    private AuthenticationIdentityRepository authenticationIdentityRepository;
    private PasswordHasher passwordHasher;
    private UserApplicationService service;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        authenticationIdentityRepository = mock(AuthenticationIdentityRepository.class);
        passwordHasher = mock(PasswordHasher.class);

        service = new UserApplicationService(
                userRepository,
                authenticationIdentityRepository,
                passwordHasher
        );
    }

    @Test
    void shouldRegisterUserSuccessfully() {
        RegisterUserCommand command = new RegisterUserCommand(
                "farich",
                "farich@gmail.com",
                "password123"
        );

        PasswordHash passwordHash = new PasswordHash("hashed-password");

        when(userRepository.existsByUsername(new Username("farich")))
                .thenReturn(false);
        when(authenticationIdentityRepository.existsByEmail(new Email("farich@gmail.com")))
                .thenReturn(false);
        when(passwordHasher.hash("password123"))
                .thenReturn(passwordHash);

        User user = service.register(command);

        assertNotNull(user);
        assertEquals(new Username("farich"), user.username());

        verify(authenticationIdentityRepository).existsByEmail(
                new Email("farich@gmail.com")
        );
        verify(passwordHasher).hash("password123");
        verify(userRepository).save(user);
        verify(authenticationIdentityRepository).save(any(AuthenticationIdentity.class));
    }

    @Test
    void shouldRejectDuplicateUsername() {
        RegisterUserCommand command = new RegisterUserCommand(
                "farich",
                "farich@gmail.com",
                "password123"
        );

        when(userRepository.existsByUsername(new Username("farich")))
                .thenReturn(true);

        assertThrows(
                IllegalArgumentException.class,
                () -> service.register(command)
        );

        verify(userRepository).existsByUsername(
                new Username("farich")
        );
        verify(authenticationIdentityRepository, never())
                .existsByEmail(any());
        verify(passwordHasher, never()).hash(anyString());
        verify(userRepository, never()).save(any());
        verify(authenticationIdentityRepository, never())
                .save(any());
    }

    @Test
    void shouldRejectDuplicateEmail() {
        RegisterUserCommand command = new RegisterUserCommand(
                "farich",
                "farich@gmail.com",
                "password123"
        );

        when(userRepository.existsByUsername(new Username("farich")))
                .thenReturn(false);
        when(authenticationIdentityRepository.existsByEmail(
                new Email("farich@gmail.com")
        )).thenReturn(true);

        assertThrows(
                IllegalArgumentException.class,
                () -> service.register(command)
        );

        verify(userRepository).existsByUsername(
                new Username("farich")
        );
        verify(authenticationIdentityRepository).existsByEmail(
                new Email("farich@gmail.com")
        );
        verify(passwordHasher, never()).hash(anyString());
        verify(userRepository, never()).save(any());
        verify(authenticationIdentityRepository, never()).save(any());
    }

    @Test
    void shouldUseHashedPasswordWhenCreatingAuthenticationIdentity() {
        RegisterUserCommand command = new RegisterUserCommand(
                "farich",
                "farich@gmail.com",
                "password123"
        );

        PasswordHash passwordHash =
                new PasswordHash("hashed-password");

        when(userRepository.existsByUsername(new Username("farich")))
                .thenReturn(false);
        when(authenticationIdentityRepository.existsByEmail(
                new Email("farich@gmail.com")
        )).thenReturn(false);
        when(passwordHasher.hash("password123"))
                .thenReturn(passwordHash);

        service.register(command);

        verify(passwordHasher).hash("password123");
        verify(authenticationIdentityRepository).save(
                argThat(identity ->
                        identity.passwordHash().equals(passwordHash) &&
                                identity.email().equals(new Email("farich@gmail.com")))
        );
    }

    @Test
    void shouldLinkAuthenticationIdentityToCreatedUser() {
        RegisterUserCommand command = new RegisterUserCommand(
                "farich",
                "farich@gmail.com",
                "password123"
        );

        PasswordHash passwordHash = new PasswordHash("hashed-password");

        when(userRepository.existsByUsername(new Username("farich")))
                .thenReturn(false);
        when(authenticationIdentityRepository.existsByEmail(
                new Email("farich@gmail.com")
        )).thenReturn(false);
        when(passwordHasher.hash("password123"))
                .thenReturn(passwordHash);

        User user = service.register(command);

        verify(authenticationIdentityRepository).save(
                argThat(identiity ->
                        identiity.userId().equals(user.id()))
        );
    }
}
