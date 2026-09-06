/*
 * Copyright (c) 2026 Farich Murobic
 * Licensed under the MIT License.
 */
package com.bedroom.infrastructure.security;

import com.bedroom.application.identitas.port.PemeriksaKataSandi;
import com.bedroom.application.identitas.port.PenerbitTokenAutentikasi;
import com.bedroom.application.identitas.port.PenghasilHashKataSandi;
import com.bedroom.application.security.PenyediaPenggunaTerautentikasi;
import com.bedroom.infrastructure.security.jwt.JwtPenerbitTokenAutentikasi;
import com.bedroom.infrastructure.security.jwt.JwtSecurityAuthenticationConverter;
import com.bedroom.infrastructure.security.password.BCryptPemeriksaKataSandi;
import com.bedroom.infrastructure.security.password.BCryptPenghasilHashKataSandi;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

@Configuration
@EnableWebSecurity
public class KonfigurasiKeamanan {

    @Bean
    BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    PenghasilHashKataSandi penghasilHashKataSandi(BCryptPasswordEncoder bCryptPasswordEncoder) {
        return new BCryptPenghasilHashKataSandi(bCryptPasswordEncoder);
    }

    @Bean
    PemeriksaKataSandi pemeriksaKataSandi(BCryptPasswordEncoder bCryptPasswordEncoder) {
        return new BCryptPemeriksaKataSandi(bCryptPasswordEncoder);
    }

    @Bean
    PenerbitTokenAutentikasi penerbitTokenAutentikasi(
            RSAPublicKey kunciPublikJwt,
            RSAPrivateKey kunciPrivatJwt
    ) {
        return new JwtPenerbitTokenAutentikasi(kunciPublikJwt, kunciPrivatJwt);
    }

    @Bean
    JwtDecoder jwtDecoder(RSAPublicKey kunciPublikJwt) {
        return NimbusJwtDecoder.withPublicKey(kunciPublikJwt).build();
    }

    @Bean
    JwtSecurityAuthenticationConverter jwtSecurityAuthenticationConverter() {
        return new JwtSecurityAuthenticationConverter();
    }

    @Bean
    PenyediaPenggunaTerautentikasi penyediaPenggunaTerautentikasi() {
        return new SpringSecurityPenyediaPenggunaTerautentikasi();
    }

    @Bean
    JsonAuthenticationEntryPoint jsonAuthenticationEntryPoint() {
        return new JsonAuthenticationEntryPoint();
    }

    @Bean
    JsonAccessDeniedHandler jsonAccessDeniedHandler() {
        return new JsonAccessDeniedHandler();
    }

    @Bean
    SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            JwtDecoder jwtDecoder,
            JwtSecurityAuthenticationConverter jwtSecurityAuthenticationConverter,
            JsonAuthenticationEntryPoint jsonAuthenticationEntryPoint,
            JsonAccessDeniedHandler jsonAccessDeniedHandler
    ) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .httpBasic(httpBasic -> httpBasic.disable())
                .formLogin(formLogin -> formLogin.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/publik/**")
                        .permitAll()
                        .anyRequest()
                        .authenticated())
                .exceptionHandling(handling -> handling
                        .authenticationEntryPoint(jsonAuthenticationEntryPoint)
                        .accessDeniedHandler(jsonAccessDeniedHandler))
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt
                                .decoder(jwtDecoder)
                                .jwtAuthenticationConverter(jwtSecurityAuthenticationConverter)));

        return http.build();
    }
}