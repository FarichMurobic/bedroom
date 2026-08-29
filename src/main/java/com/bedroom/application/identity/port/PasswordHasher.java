package com.bedroom.application.identity.port;

import com.bedroom.domain.identity.valueobject.PasswordHash;

public interface PasswordHasher {

    PasswordHash hash(String password);

}
