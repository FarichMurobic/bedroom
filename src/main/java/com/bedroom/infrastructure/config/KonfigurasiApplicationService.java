/*
 * Copyright (c) 2026 Farich Murobic
 * Licensed under the MIT License.
 */
package com.bedroom.infrastructure.config;

import com.bedroom.application.identitas.port.PemeriksaKataSandi;
import com.bedroom.application.identitas.port.PemverifikasiTokenGoogle;
import com.bedroom.application.identitas.port.PengelolaKodeOtp;
import com.bedroom.application.identitas.port.PengelolaVerifikasiEmail;
import com.bedroom.application.identitas.port.PenerbitTokenAutentikasi;
import com.bedroom.application.identitas.port.PenghasilHashKataSandi;
import com.bedroom.application.identitas.service.GantiNamaPenggunaApplicationService;
import com.bedroom.application.identitas.service.LoginApplicationService;
import com.bedroom.application.identitas.service.RegistrasiApplicationService;
import com.bedroom.application.identitas.service.VerifikasiApplicationService;
import com.bedroom.application.security.PenyediaPenggunaTerautentikasi;
import com.bedroom.domain.identitas.repository.RepositoriIdentitasAutentikasi;
import com.bedroom.domain.identitas.repository.RepositoriPengguna;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Mendaftarkan seluruh application service domain identitas sebagai bean
 * Spring. Application service sengaja ditulis sebagai plain class tanpa
 * anotasi framework, sehingga wiring-nya didaftarkan secara eksplisit di sini.
 */
@Configuration
public class KonfigurasiApplicationService {

    @Bean
    RegistrasiApplicationService registrasiApplicationService(
            RepositoriPengguna repositoriPengguna,
            RepositoriIdentitasAutentikasi repositoriIdentitasAutentikasi,
            PenghasilHashKataSandi penghasilHashKataSandi,
            PengelolaVerifikasiEmail pengelolaVerifikasiEmail,
            PengelolaKodeOtp pengelolaKodeOtp
    ) {
        return new RegistrasiApplicationService(
                repositoriPengguna, repositoriIdentitasAutentikasi,
                penghasilHashKataSandi, pengelolaVerifikasiEmail, pengelolaKodeOtp
        );
    }

    @Bean
    VerifikasiApplicationService verifikasiApplicationService(
            RepositoriPengguna repositoriPengguna,
            RepositoriIdentitasAutentikasi repositoriIdentitasAutentikasi,
            PengelolaVerifikasiEmail pengelolaVerifikasiEmail,
            PengelolaKodeOtp pengelolaKodeOtp,
            PenerbitTokenAutentikasi penerbitTokenAutentikasi
    ) {
        return new VerifikasiApplicationService(
                repositoriPengguna, repositoriIdentitasAutentikasi,
                pengelolaVerifikasiEmail, pengelolaKodeOtp, penerbitTokenAutentikasi
        );
    }

    @Bean
    LoginApplicationService loginApplicationService(
            RepositoriPengguna repositoriPengguna,
            RepositoriIdentitasAutentikasi repositoriIdentitasAutentikasi,
            PemeriksaKataSandi pemeriksaKataSandi,
            PenerbitTokenAutentikasi penerbitTokenAutentikasi,
            PemverifikasiTokenGoogle pemverifikasiTokenGoogle
    ) {
        return new LoginApplicationService(
                repositoriPengguna, repositoriIdentitasAutentikasi,
                pemeriksaKataSandi, penerbitTokenAutentikasi, pemverifikasiTokenGoogle
        );
    }

    @Bean
    GantiNamaPenggunaApplicationService gantiNamaPenggunaApplicationService(
            RepositoriPengguna repositoriPengguna,
            PenyediaPenggunaTerautentikasi penyediaPenggunaTerautentikasi
    ) {
        return new GantiNamaPenggunaApplicationService(repositoriPengguna, penyediaPenggunaTerautentikasi);
    }
}