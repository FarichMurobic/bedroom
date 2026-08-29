package com.bedroom.domain.identity.model;

import com.bedroom.domain.identity.enums.AuthenticationProvider;
import com.bedroom.domain.identity.valueobject.Email;
import com.bedroom.domain.identity.valueobject.PasswordHash;
import com.bedroom.domain.identity.valueobject.UserId;
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

}
