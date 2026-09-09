/*
 * Copyright (c) 2026 Farich Murobic
 *
 * This project is licensed under the MIT License.
 * See the LICENSE file in the project root for more information.
 */

package com.bedroom.infrastructure.security;

import com.bedroom.application.identitas.port.PemverifikasiTokenGoogle;
import com.bedroom.application.identitas.port.PengelolaKodeOtp;
import com.bedroom.application.identitas.port.PengelolaVerifikasiEmail;
import com.bedroom.infrastructure.security.email.RedisPengelolaVerifikasiEmail;
import com.bedroom.infrastructure.security.google.GooglePemverifikasiTokenGoogle;
import com.bedroom.infrastructure.security.otp.RedisPengelolaKodeOtp;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.mail.javamail.JavaMailSender;

/**
 * Mendaftarkan implementasi konkret untuk port OTP, verifikasi email,
 * dan verifikasi token Google sebagai bean Spring.
 */
@Configuration
@Profile("!test")
public class KonfigurasiOtpEmailGoogle {

    @Bean
    PengelolaKodeOtp pengelolaKodeOtp(
            StringRedisTemplate stringRedisTemplate,
            @Value("${bedroom.integrasi.twilio.account-sid}") String twilioAccountSid,
            @Value("${bedroom.integrasi.twilio.auth-token}") String twilioAuthToken,
            @Value("${bedroom.integrasi.twilio.nomor-pengirim}") String nomorPengirimTwilio
    ) {
        return new RedisPengelolaKodeOtp(
                stringRedisTemplate,
                twilioAccountSid,
                twilioAuthToken,
                nomorPengirimTwilio
        );
    }

    @Bean
    PengelolaVerifikasiEmail pengelolaVerifikasiEmail(
            StringRedisTemplate stringRedisTemplate,
            JavaMailSender javaMailSender,
            @Value("${bedroom.integrasi.email.alamat-pengirim}") String alamatEmailPengirim,
            @Value("${bedroom.keamanan.verifikasi-email.url-dasar}") String urlDasarVerifikasi
    ) {
        return new RedisPengelolaVerifikasiEmail(
                stringRedisTemplate,
                javaMailSender,
                alamatEmailPengirim,
                urlDasarVerifikasi
        );
    }

    @Bean
    PemverifikasiTokenGoogle pemverifikasiTokenGoogle(
            @Value("${bedroom.integrasi.google.client-id}") String googleClientId
    ) {
        return new GooglePemverifikasiTokenGoogle(googleClientId);
    }
}