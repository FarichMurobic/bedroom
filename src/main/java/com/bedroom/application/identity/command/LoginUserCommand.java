package com.bedroom.application.identity.command;

public record LoginUserCommand (
        String email,
        String password
) {
}
