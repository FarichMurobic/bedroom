package com.bedroom.infrastructure.security;

import com.bedroom.application.identity.port.AuthenticationTokenIssuer;
import com.bedroom.infrastructure.security.jwt.JwtKeyConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.MapPropertySource;
import org.springframework.security.oauth2.jwt.JwtDecoder;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.util.Base64;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class SecurityConfigurationTest {

    private KeyPair keyPair;

    @BeforeEach
    void setUp() throws Exception {
        KeyPairGenerator keyPairGenerator =
                KeyPairGenerator.getInstance("RSA");

        keyPairGenerator.initialize(2048);

        keyPair = keyPairGenerator.generateKeyPair();
    }

    private AnnotationConfigApplicationContext createContext() {

        String publicKey = Base64.getEncoder().encodeToString(
                keyPair.getPublic().getEncoded()
        );

        String privateKey = Base64.getEncoder().encodeToString(
                keyPair.getPrivate().getEncoded()
        );

        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext();

        context.getEnvironment()
                .getPropertySources()
                .addFirst(
                        new MapPropertySource(
                                "test-properties",
                                Map.of(
                                        "bedroom.security.jwt.public-key",
                                        publicKey,
                                        "bedroom.security.jwt.private-key",
                                        privateKey
                                )
                        )
                );

        context.register(
                JwtKeyConfiguration.class,
                SecurityConfiguration.class
        );

        context.refresh();

        return context;
    }

    @Test
    void shouldCreateAuthenticationTokenIssuerBean() {
        try (AnnotationConfigApplicationContext context =
                     createContext()) {

            AuthenticationTokenIssuer tokenIssuer =
                    context.getBean(
                            AuthenticationTokenIssuer.class
                    );

            assertNotNull(tokenIssuer);
        }
    }

    @Test
    void shouldCreateJwtDecoderBean() {
        try (AnnotationConfigApplicationContext context =
                     createContext()) {

            JwtDecoder jwtDecoder =
                    context.getBean(JwtDecoder.class);

            assertNotNull(jwtDecoder);
        }
    }
}