package com.bedroom.infrastructure.security;

import com.bedroom.application.identitas.port.PemverifikasiTokenGoogle;
import com.bedroom.application.identitas.port.PengelolaKodeOtp;
import com.bedroom.application.identitas.port.PengelolaVerifikasiEmail;
import com.bedroom.infrastructure.security.email.PengelolaVerifikasiEmailPalsu;
import com.bedroom.infrastructure.security.google.PemverifikasiTokenGooglePalsu;
import com.bedroom.infrastructure.security.otp.PengelolaKodeOtpPalsu;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * Mendaftarkan implementasi palsu untuk port OTP, verifikasi email,
 * dan verifikasi token Google, khusus untuk profile pengujian.
 * Menggantikan {@code KonfigurasiOtpEmailGoogle} agar pengujian tidak
 * bergantung pada kredensial maupun koneksi ke layanan pihak ketiga.
 */
@Configuration
@Profile("test")
public class KonfigurasiOtpEmailGoogleUjiCoba {

    @Bean
    PengelolaKodeOtp pengelolaKodeOtp() {
        return new PengelolaKodeOtpPalsu();
    }

    @Bean
    PengelolaVerifikasiEmail pengelolaVerifikasiEmail() {
        return new PengelolaVerifikasiEmailPalsu();
    }

    @Bean
    PemverifikasiTokenGoogle pemverifikasiTokenGoogle() {
        return new PemverifikasiTokenGooglePalsu();
    }
}