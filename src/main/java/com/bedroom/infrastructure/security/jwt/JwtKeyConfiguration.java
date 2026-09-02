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

@Configuration
@Profile("!test")
public class JwtKeyConfiguration {

    @Bean
    RSAPublicKey jwtPublicKey(
            @Value("${bedroom.security.jwt.public-key}") String publicKey
    ) {
        try {
            byte[] keyBytes = Base64.getDecoder().decode(publicKey);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");

            PublicKey key = keyFactory.generatePublic(
                    new X509EncodedKeySpec(keyBytes)
            );

            return (RSAPublicKey) key;
        } catch (Exception exception) {
            throw new IllegalStateException(
                    "Failed to load JWT public key",
                    exception
            );
        }
    }

    @Bean
    RSAPrivateKey jwtPrivateKey(
            @Value("${bedroom.security.jwt.private-key}") String privateKey
    ) {
        try {
            byte[] keyBytes = Base64.getDecoder().decode(privateKey);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");

            PrivateKey key = keyFactory.generatePrivate(
                    new PKCS8EncodedKeySpec(keyBytes)
            );

            return (RSAPrivateKey) key;
        } catch (Exception exception) {
            throw new IllegalStateException(
                    "Failed to load JWT private key",
                    exception
            );
        }
    }
}