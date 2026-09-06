/*
 * Copyright (c) 2026 Farich Murobic
 * Licensed under the MIT License.
 */
package com.bedroom.infrastructure.security.jwt;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

/**
 * Menyediakan pasangan kunci RSA yang dibuat secara acak saat aplikasi
 * dijalankan dalam profile pengujian, sehingga tidak memerlukan konfigurasi
 * kunci sungguhan.
 */
@Configuration
@Profile("test")
public class KonfigurasiKunciJwtUjiCoba {

    @Bean
    KeyPair pasanganKunciRsaUjiCoba() throws NoSuchAlgorithmException {
        KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
        generator.initialize(2048);
        return generator.generateKeyPair();
    }

    @Bean
    RSAPublicKey kunciPublikJwt(KeyPair pasanganKunciRsaUjiCoba) {
        return (RSAPublicKey) pasanganKunciRsaUjiCoba.getPublic();
    }

    @Bean
    RSAPrivateKey kunciPrivatJwt(KeyPair pasanganKunciRsaUjiCoba) {
        return (RSAPrivateKey) pasanganKunciRsaUjiCoba.getPrivate();
    }
}