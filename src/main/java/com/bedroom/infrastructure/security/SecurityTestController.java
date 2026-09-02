package com.bedroom.infrastructure.security;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
class SecurityTestController {

    @GetMapping("/public/test")
    String publicEndpoint() {
        return "public";
    }

    @GetMapping("/protected/test")
    String protectedEndpoint() {
        return "protected";
    }
}