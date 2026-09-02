package com.bedroom.infrastructure.security;

import com.bedroom.application.identity.port.AuthenticationTokenIssuer;
import com.bedroom.application.security.AuthenticatedUserProvider;
import com.bedroom.infrastructure.security.jwt.JwtAuthenticationTokenIssuer;
import com.bedroom.infrastructure.security.jwt.JwtSecurityAuthenticationConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Bean
    AuthenticationTokenIssuer authenticationTokenIssuer(
            RSAPublicKey publicKey,
            RSAPrivateKey privateKey
    ) {
        return new JwtAuthenticationTokenIssuer(
                publicKey,
                privateKey
        );
    }

    @Bean
    JwtDecoder jwtDecoder(
            RSAPublicKey publicKey
    ) {
        return NimbusJwtDecoder
                .withPublicKey(publicKey)
                .build();
    }

    @Bean
    JwtSecurityAuthenticationConverter
    jwtSecurityAuthenticationConverter() {
        return new JwtSecurityAuthenticationConverter();
    }

    @Bean
    SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            JwtDecoder jwtDecoder,
            JwtSecurityAuthenticationConverter
                    jwtSecurityAuthenticationConverter
    ) throws Exception {

        http
                .csrf(csrf -> csrf.disable())
                .httpBasic(httpBasic -> httpBasic.disable())
                .formLogin(formLogin -> formLogin.disable())
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/public/**").permitAll()
                        .anyRequest().authenticated()
                )
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt
                                .decoder(jwtDecoder)
                                .jwtAuthenticationConverter(
                                        jwtSecurityAuthenticationConverter
                                )
                        )
                );

        return http.build();
    }

    @Bean
    AuthenticatedUserProvider authenticatedUserProvider() {
        return new SpringSecurityAuthenticatedUserProvider();
    }
}