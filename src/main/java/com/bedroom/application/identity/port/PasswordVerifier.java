package com.bedroom.application.identity.port;

import com.bedroom.domain.identity.valueobject.PasswordHash;

public interface PasswordVerifier {

    boolean matches(String password, PasswordHash passwordHash);
}
