package com.bedroom.application.identity.command;

public record RegisterUserCommand(
        String username,
        String email,
        String password
) {
}
