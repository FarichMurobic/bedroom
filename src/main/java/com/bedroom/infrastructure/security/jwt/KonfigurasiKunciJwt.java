/*
 * Copyright (c) 2026 Farich Murobic
 *
 * This project is licensed under the MIT License.
 * See the LICENSE file in the project root for more information.
 */

package com.bedroom.infrastructure.security.jwt;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

/**
 * Konfigurasi kunci RSA untuk JWT dari environment variable.
 * Hanya aktif pada profile selain 'test'.
 */
@Configuration
@Profile("!test")
public class KonfigurasiKunciJwt {

    @Bean
    RSAPublicKey kunciPublikJwt(@Value("${bedroom.keamanan.jwt.kunci-publik}") String kunciPublikBase64) {
        try {
            byte[] byteKunci = Base64.getDecoder().decode(kunciPublikBase64);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PublicKey kunci = keyFactory.generatePublic(new X509EncodedKeySpec(byteKunci));
            return (RSAPublicKey) kunci;
        } catch (Exception e) {
            throw new IllegalStateException("Gagal memuat kunci publik JWT", e);
        }
    }

    @Bean
    RSAPrivateKey kunciPrivatJwt(@Value("${bedroom.keamanan.jwt.kunci-privat}") String kunciPrivatBase64) {
        try {
            byte[] byteKunci = Base64.getDecoder().decode(kunciPrivatBase64);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PrivateKey kunci = keyFactory.generatePrivate(new PKCS8EncodedKeySpec(byteKunci));
            return (RSAPrivateKey) kunci;
        } catch (Exception e) {
            throw new IllegalStateException("Gagal memuat kunci privat JWT", e);
        }
    }
}