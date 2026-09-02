package com.bedroom.infrastructure.security.jwt;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.MapPropertySource;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Base64;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class JwtKeyConfigurationTest {

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

        context.register(JwtKeyConfiguration.class);
        context.refresh();

        return context;
    }

    @Test
    void shouldCreateRsaPublicKeyBean() {

        try (AnnotationConfigApplicationContext context =
                     createContext()) {

            RSAPublicKey publicKey =
                    context.getBean(RSAPublicKey.class);

            assertNotNull(publicKey);
            assertEquals(
                    "RSA",
                    publicKey.getAlgorithm()
            );
        }
    }

    @Test
    void shouldCreateRsaPrivateKeyBean() {

        try (AnnotationConfigApplicationContext context =
                     createContext()) {

            RSAPrivateKey privateKey =
                    context.getBean(RSAPrivateKey.class);

            assertNotNull(privateKey);
            assertEquals(
                    "RSA",
                    privateKey.getAlgorithm()
            );
        }
    }

    @Test
    void shouldLoadTheConfiguredPublicKey() {

        try (AnnotationConfigApplicationContext context =
                     createContext()) {

            RSAPublicKey publicKey =
                    context.getBean(RSAPublicKey.class);

            assertArrayEquals(
                    keyPair.getPublic().getEncoded(),
                    publicKey.getEncoded()
            );
        }
    }

    @Test
    void shouldLoadTheConfiguredPrivateKey() {

        try (AnnotationConfigApplicationContext context =
                     createContext()) {

            RSAPrivateKey privateKey =
                    context.getBean(RSAPrivateKey.class);

            assertArrayEquals(
                    keyPair.getPrivate().getEncoded(),
                    privateKey.getEncoded()
            );
        }
    }
}