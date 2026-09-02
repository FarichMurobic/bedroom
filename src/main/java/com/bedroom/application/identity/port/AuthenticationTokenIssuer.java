package com.bedroom.application.identity.port;

import com.bedroom.domain.identity.valueobject.UserId;

public interface AuthenticationTokenIssuer {

    String issue(UserId userId);
}
