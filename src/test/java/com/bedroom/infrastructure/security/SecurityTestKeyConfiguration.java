package com.bedroom.infrastructure.security;

import org.springframework.context.annotation.Bean;
import org.springframework.boot.test.context.TestConfiguration;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

@TestConfiguration
public class SecurityTestKeyConfiguration {

    @Bean
    KeyPair jwtKeyPair() throws Exception {
        KeyPairGenerator keyPairGenerator =
                KeyPairGenerator.getInstance("RSA");

        keyPairGenerator.initialize(2048);

        return keyPairGenerator.generateKeyPair();
    }

    @Bean
    RSAPublicKey jwtPublicKey(KeyPair jwtKeyPair) {
        return (RSAPublicKey) jwtKeyPair.getPublic();
    }

    @Bean
    RSAPrivateKey jwtPrivateKey(KeyPair jwtKeyPair) {
        return (RSAPrivateKey) jwtKeyPair.getPrivate();
    }
}