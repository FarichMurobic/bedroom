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
import com.bedroom.domain.identity.valueobject.UserId;
import com.bedroom.domain.identity.valueobject.Username;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserApplicationServiceTest {

    private UserRepository userRepository;
    private AuthenticationIdentityRepository authenticationIdentityRepository;
    private PasswordHasher passwordHasher;
    private PasswordVerifier passwordVerifier;
    private UserApplicationService service;
    private AuthenticationTokenIssuer authenticationTokenIssuer;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        authenticationIdentityRepository = mock(AuthenticationIdentityRepository.class);
        passwordHasher = mock(PasswordHasher.class);
        passwordVerifier = mock(PasswordVerifier.class);
        authenticationTokenIssuer = mock(AuthenticationTokenIssuer.class);

        service = new UserApplicationService(
                userRepository,
                authenticationIdentityRepository,
                passwordHasher,
                passwordVerifier,
                authenticationTokenIssuer
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

    @Test
    void shouldLoginUserSuccessfully() {
        LoginUserCommand command = new LoginUserCommand(
                "farich@gmail.com",
                "password123"
        );

        User user = User.create(new Username("farich"));
        PasswordHash passwordHash = new PasswordHash("hashed-password");
        AuthenticationIdentity identity = AuthenticationIdentity.forEmail(
                user.id(),
                new Email("farich@gmail.com"),
                passwordHash
        );

        when(authenticationIdentityRepository.findByEmail(
                new Email("farich@gmail.com")
        )).thenReturn(Optional.of(identity));

        when(passwordVerifier.matches(
                "password123",
                passwordHash
        )).thenReturn(true);

        when(userRepository.findById(user.id()))
                .thenReturn(Optional.of(user));
        when(authenticationTokenIssuer.issue(user.id()))
                .thenReturn("test-token");

        AuthenticationResult result = service.login(command);

        assertNotNull(result);
        assertEquals(user.id(), result.userId());
        assertEquals("test-token", result.accessToken());

        verify(authenticationIdentityRepository).findByEmail(
                new Email("farich@gmail.com")
        );

        verify(passwordVerifier).matches(
                "password123",
                passwordHash
        );

        verify(userRepository).findById(user.id());

        verify(userRepository, never()).save(any());
        verify(authenticationIdentityRepository, never()).save(any());
    }

    @Test
    void shouldRejectLoginWhenEmailDoesNotExist() {
        LoginUserCommand command = new LoginUserCommand(
                "farich@gmail.com",
                "password123"
        );

        when(authenticationIdentityRepository.findByEmail(
                new Email("farich@gmail.com")
        )).thenReturn(Optional.empty());

        assertThrows(
                IllegalArgumentException.class,
                () -> service.login(command)
        );

        verify(authenticationIdentityRepository).findByEmail(
                new Email("farich@gmail.com")
        );

        verify(passwordVerifier, never())
                .matches(anyString(), any());

        verify(userRepository, never()).findById(any());

        verify(userRepository, never()).save(any());
        verify(authenticationIdentityRepository, never())
                .save(any());
    }

    @Test
    void shouldRejectLoginWhenPasswordIsIncorrect() {
        LoginUserCommand command = new LoginUserCommand(
                "farich@gmail.com",
                "wrong-password"
        );

        User user = User.create(
                new Username("farich")
        );

        PasswordHash passwordHash =
                new PasswordHash("hashed-password");

        AuthenticationIdentity identity =
                AuthenticationIdentity.forEmail(
                        user.id(),
                        new Email("farich@gmail.com"),
                        passwordHash
                );

        when(authenticationIdentityRepository.findByEmail(
                new Email("farich@gmail.com")
        )).thenReturn(Optional.of(identity));

        when(passwordVerifier.matches(
                "wrong-password",
                passwordHash
        )).thenReturn(false);

        assertThrows(
                IllegalArgumentException.class,
                () -> service.login(command)
        );

        verify(authenticationIdentityRepository).findByEmail(
                new Email("farich@gmail.com")
        );

        verify(passwordVerifier).matches(
                "wrong-password",
                passwordHash
        );

        verify(userRepository, never()).findById(any());

        verify(userRepository, never()).save(any());
        verify(authenticationIdentityRepository, never())
                .save(any());
    }

    @Test
    void shouldIssueAuthenticationTokenWhenLoginIsSuccessful() {
        LoginUserCommand command = new LoginUserCommand(
                "farich@gmail.com",
                "correct-password"
        );

        User user = User.create(new Username("farich"));
        PasswordHash passwordHash = new PasswordHash("hashed-password");

        AuthenticationIdentity identity =
                AuthenticationIdentity.forEmail(
                        user.id(),
                        new Email(command.email()),
                        passwordHash
                );

        when(authenticationIdentityRepository.findByEmail(
                new Email(command.email())
        )).thenReturn(Optional.of(identity));

        when(passwordVerifier.matches(
                command.password(),
                passwordHash
        )).thenReturn(true);

        when(userRepository.findById(user.id()))
                .thenReturn(Optional.of(user));

        when(authenticationTokenIssuer.issue(user.id()))
                .thenReturn("test-token");

        AuthenticationResult result = service.login(command);

        assertNotNull(result);
        assertEquals(user.id(), result.userId());

        verify(authenticationTokenIssuer)
                .issue(user.id());
    }

    @Test
    void shouldNotIssueAuthenticationTokenWhenPasswordIsIncorrect() {
        LoginUserCommand command = new LoginUserCommand(
                "farich@gmail.com",
                "wrong-password"
        );

        User user = User.create(new Username("farich"));
        PasswordHash passwordHash = new PasswordHash("hashed-password");

        AuthenticationIdentity identity =
                AuthenticationIdentity.forEmail(
                        user.id(),
                        new Email(command.email()),
                        passwordHash
                );

        when(authenticationIdentityRepository.findByEmail(
                new Email(command.email())
        )).thenReturn(Optional.of(identity));

        when(passwordVerifier.matches(
                command.password(),
                passwordHash
        )).thenReturn(false);

        assertThrows(
                IllegalArgumentException.class,
                () -> service.login(command)
        );

        verify(authenticationTokenIssuer, never())
                .issue(any(UserId.class));
    }

}