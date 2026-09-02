package com.bedroom.application.identity.result;

import com.bedroom.domain.identity.valueobject.UserId;

public record AuthenticationResult (
        UserId userId,
        String accessToken
) {
}
