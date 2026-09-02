package com.bedroom.application.identity.service;

import com.bedroom.application.identity.command.UpdateUsernameCommand;
import com.bedroom.application.security.AuthenticatedUser;
import com.bedroom.application.security.AuthenticatedUserProvider;
import com.bedroom.domain.identity.model.User;
import com.bedroom.domain.identity.repository.UserRepository;
import com.bedroom.domain.identity.valueobject.UserId;
import com.bedroom.domain.identity.valueobject.Username;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdateUsernameApplicationServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private AuthenticatedUserProvider authenticatedUserProvider;

    private UpdateUsernameApplicationService service;

    @BeforeEach
    void setUp() {
        service = new UpdateUsernameApplicationService(
                userRepository,
                authenticatedUserProvider
        );
    }

    @Test
    void shouldUpdateUsernameForAuthenticatedUser() {

        User user = User.create(
                new Username("old_username")
        );

        UserId userId = user.id();

        when(authenticatedUserProvider.getAuthenticatedUser())
                .thenReturn(new AuthenticatedUser(userId));

        when(userRepository.findById(userId))
                .thenReturn(Optional.of(user));

        when(userRepository.existsByUsername(
                new Username("new_username")
        )).thenReturn(false);

        when(userRepository.save(user))
                .thenReturn(user);

        User result = service.execute(
                new UpdateUsernameCommand("new_username")
        );

        assertEquals(
                new Username("new_username"),
                result.username()
        );

        verify(authenticatedUserProvider)
                .getAuthenticatedUser();

        verify(userRepository)
                .findById(userId);

        verify(userRepository)
                .save(user);
    }

    @Test
    void shouldRejectWhenUserDoesNotExist() {

        UserId userId = UserId.generate();

        when(authenticatedUserProvider.getAuthenticatedUser())
                .thenReturn(new AuthenticatedUser(userId));

        when(userRepository.findById(userId))
                .thenReturn(Optional.empty());

        assertThrows(
                IllegalStateException.class,
                () -> service.execute(
                        new UpdateUsernameCommand("new_username")
                )
        );

        verify(userRepository)
                .findById(userId);

        verify(userRepository, never())
                .save(any());
    }

    @Test
    void shouldRejectWhenUsernameAlreadyTaken() {

        User user = User.create(
                new Username("old_username")
        );

        UserId userId = user.id();

        when(authenticatedUserProvider.getAuthenticatedUser())
                .thenReturn(new AuthenticatedUser(userId));

        when(userRepository.findById(userId))
                .thenReturn(Optional.of(user));

        when(userRepository.existsByUsername(
                new Username("new_username")
        )).thenReturn(true);

        assertThrows(
                IllegalStateException.class,
                () -> service.execute(
                        new UpdateUsernameCommand("new_username")
                )
        );

        verify(userRepository, never())
                .save(any());
    }

    @Test
    void shouldAllowKeepingCurrentUsername() {

        User user = User.create(
                new Username("current_username")
        );

        UserId userId = user.id();

        when(authenticatedUserProvider.getAuthenticatedUser())
                .thenReturn(new AuthenticatedUser(userId));

        when(userRepository.findById(userId))
                .thenReturn(Optional.of(user));

        when(userRepository.existsByUsername(
                new Username("current_username")
        )).thenReturn(true);

        when(userRepository.save(user))
                .thenReturn(user);

        User result = service.execute(
                new UpdateUsernameCommand("current_username")
        );

        assertEquals(
                new Username("current_username"),
                result.username()
        );

        verify(userRepository)
                .save(user);
    }
}