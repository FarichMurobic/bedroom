package com.bedroom.domain.identity.model;

import com.bedroom.domain.identity.enums.AuthenticationProvider;
import com.bedroom.domain.identity.valueobject.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AuthenticationIdentityTest {

    @Test
    void shouldCreateEmailAuthenticationIdentity() {
        UserId userId = UserId.generate();
        Email email = new Email("farich@gmail.com");
        PasswordHash passwordHash = new PasswordHash("hashed-password");

        AuthenticationIdentity identity = AuthenticationIdentity.forEmail(userId, email, passwordHash);

        assertNotNull(identity.id());
        assertEquals(userId, identity.userId());
        assertEquals(AuthenticationProvider.EMAIL, identity.provider());
        assertEquals(email, identity.email());
        assertNull(identity.phoneNumber());
        assertNull(identity.externalIdentifier());
        assertEquals(passwordHash, identity.passwordHash());
        assertNotNull(identity.createdAt());
        assertNotNull(identity.updatedAt());
    }

    @Test
    void shouldCreatePhoneAuthenticationIdentity() {
        UserId userId = UserId.generate();
        PhoneNumber phoneNumber = new PhoneNumber("+6285795488106");
        PasswordHash passwordHash = new PasswordHash("hashed-password");

        AuthenticationIdentity identity =
                AuthenticationIdentity.forPhone(
                        userId,
                        phoneNumber,
                        passwordHash
                );

        assertNotNull(identity.id());
        assertEquals(userId, identity.userId());
        assertEquals(AuthenticationProvider.PHONE, identity.provider());
        assertNull(identity.email());
        assertEquals(phoneNumber, identity.phoneNumber());
        assertNull(identity.externalIdentifier());
        assertEquals(passwordHash, identity.passwordHash());
        assertNotNull(identity.createdAt());
        assertNotNull(identity.updatedAt());
    }

    @Test
    void shouldCreateFacebookAuthenticationIdentity() {
        UserId userId = UserId.generate();
        ExternalIdentifier externalIdentifier =
                new ExternalIdentifier("facebook-user-123");

        AuthenticationIdentity identity =
                AuthenticationIdentity.forFacebook(
                        userId,
                        externalIdentifier
                );

        assertNotNull(identity.id());
        assertEquals(userId, identity.userId());
        assertEquals(AuthenticationProvider.FACEBOOK, identity.provider());
        assertNull(identity.email());
        assertNull(identity.phoneNumber());
        assertEquals(externalIdentifier, identity.externalIdentifier());
        assertNull(identity.passwordHash());
        assertNotNull(identity.createdAt());
        assertNotNull(identity.updatedAt());
    }

    @Test
    void shouldChangePasswordForEmailAuthentication() {
        AuthenticationIdentity identity =
                AuthenticationIdentity.forEmail(
                        UserId.generate(),
                        new Email("farich@gmail.com"),
                        new PasswordHash("old-password")
                );

        PasswordHash newPasswordHash = new PasswordHash("new-password");
        identity.changePassword(newPasswordHash);

        assertEquals(newPasswordHash, identity.passwordHash());
    }

    @Test
    void shouldChangePasswordForPhoneAuthentication() {
        AuthenticationIdentity identity =
                AuthenticationIdentity.forPhone(
                        UserId.generate(),
                        new PhoneNumber("+6285795488106"),
                        new PasswordHash("old-password")
                );
        
        PasswordHash newPasswordHash = new PasswordHash("new-password");
        identity.changePassword(newPasswordHash);

        assertEquals(newPasswordHash, identity.passwordHash());
    }

    @Test
    void shouldChangePasswordForFacebookAuthentication() {
        AuthenticationIdentity identity =
                AuthenticationIdentity.forFacebook(
                        UserId.generate(),
                        new ExternalIdentifier("facebook-user-123")
                );

        assertThrows(
                IllegalArgumentException.class,
                () -> identity.changePassword(
                        new PasswordHash("new-password")
                )
        );

        assertNull(identity.passwordHash());
    }

    @Test
    void shouldGenerateUniqueAuthenticationIdentity() {
        AuthenticationIdentity first =
                AuthenticationIdentity.forEmail(
                        UserId.generate(),
                        new Email("first@gmail.com"),
                        new PasswordHash("password")
                );

        AuthenticationIdentity second =
                AuthenticationIdentity.forEmail(
                        UserId.generate(),
                        new Email("second@gmail.com"),
                        new PasswordHash("password")
                );

        assertNotEquals(first.id(), second.id());
    }

}