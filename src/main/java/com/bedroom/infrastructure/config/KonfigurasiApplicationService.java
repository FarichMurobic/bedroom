/*
 * Copyright (c) 2026 Farich Murobic
 *
 * This project is licensed under the MIT License.
 * See the LICENSE file in the project root for more information.
 */

package com.bedroom.infrastructure.config;

import com.bedroom.application.identitas.service.*;
import com.bedroom.application.interaksi.service.SukaApplicationService;
import com.bedroom.application.karya.service.*;
import com.bedroom.domain.identitas.repository.RepositoriPengguna;
import com.bedroom.domain.identitas.repository.RepositoriProfil;
import com.bedroom.domain.interaksi.repository.RepositoriSuka;
import com.bedroom.domain.karya.repository.RepositoriGenre;
import com.bedroom.domain.karya.repository.RepositoriKarya;
import com.bedroom.application.identitas.port.PemeriksaKataSandi;
import com.bedroom.application.identitas.port.PemverifikasiTokenGoogle;
import com.bedroom.application.identitas.port.PengelolaKodeOtp;
import com.bedroom.application.identitas.port.PengelolaVerifikasiEmail;
import com.bedroom.application.identitas.port.PenerbitTokenAutentikasi;
import com.bedroom.application.identitas.port.PenghasilHashKataSandi;
import com.bedroom.application.security.PenyediaPenggunaTerautentikasi;
import com.bedroom.domain.identitas.repository.RepositoriIdentitasAutentikasi;
import com.bedroom.domain.identitas.repository.RepositoriPengguna;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Mendaftarkan seluruh application service sebagai bean Spring.
 * Application service sengaja ditulis sebagai plain class tanpa
 * anotasi framework, sehingga wiring-nya didaftarkan secara eksplisit di sini.
 */
@Configuration
public class KonfigurasiApplicationService {

    @Bean
    RegistrasiApplicationService registrasiApplicationService(
            RepositoriPengguna repositoriPengguna,
            RepositoriIdentitasAutentikasi repositoriIdentitasAutentikasi,
            RepositoriProfil repositoriProfil,
            PenghasilHashKataSandi penghasilHashKataSandi,
            PengelolaVerifikasiEmail pengelolaVerifikasiEmail,
            PengelolaKodeOtp pengelolaKodeOtp
    ) {
        return new RegistrasiApplicationService(
                repositoriPengguna, repositoriIdentitasAutentikasi, repositoriProfil,
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
            RepositoriProfil repositoriProfil,
            PemeriksaKataSandi pemeriksaKataSandi,
            PenerbitTokenAutentikasi penerbitTokenAutentikasi,
            PemverifikasiTokenGoogle pemverifikasiTokenGoogle
    ) {
        return new LoginApplicationService(
                repositoriPengguna, repositoriIdentitasAutentikasi, repositoriProfil,
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

    @Bean
    PenulisanKaryaApplicationService penulisanKaryaApplicationService(
            RepositoriKarya repositoriKarya,
            PenyediaPenggunaTerautentikasi penyediaPenggunaTerautentikasi
    ) {
        return new PenulisanKaryaApplicationService(repositoriKarya, penyediaPenggunaTerautentikasi);
    }

    @Bean
    PenulisanBabApplicationService penulisanBabApplicationService(
            RepositoriKarya repositoriKarya,
            PenyediaPenggunaTerautentikasi penyediaPenggunaTerautentikasi
    ) {
        return new PenulisanBabApplicationService(repositoriKarya, penyediaPenggunaTerautentikasi);
    }

    @Bean
    PenerbitanKaryaApplicationService penerbitanKaryaApplicationService(
            RepositoriKarya repositoriKarya,
            PenyediaPenggunaTerautentikasi penyediaPenggunaTerautentikasi
    ) {
        return new PenerbitanKaryaApplicationService(repositoriKarya, penyediaPenggunaTerautentikasi);
    }

    @Bean
    GenreApplicationService genreApplicationService(
            RepositoriGenre repositoriGenre,
            RepositoriPengguna repositoriPengguna,
            PenyediaPenggunaTerautentikasi penyediaPenggunaTerautentikasi
    ) {
        return new GenreApplicationService(repositoriGenre, repositoriPengguna, penyediaPenggunaTerautentikasi);
    }

    @Bean
    PenjelajahanKaryaApplicationService penjelajahanKaryaApplicationService(
            RepositoriKarya repositoriKarya,
            RepositoriGenre repositoriGenre
    ) {
        return new PenjelajahanKaryaApplicationService(repositoriKarya, repositoriGenre);
    }

    @Bean
    SukaApplicationService sukaApplicationService(
            RepositoriSuka repositoriSuka,
            RepositoriKarya repositoriKarya,
            PenyediaPenggunaTerautentikasi penyediaPenggunaTerautentikasi
    ) {
        return new SukaApplicationService(repositoriSuka, repositoriKarya, penyediaPenggunaTerautentikasi);
    }

    @Bean
    ProfilApplicationService profilApplicationService(
            RepositoriProfil repositoriProfil,
            PenyediaPenggunaTerautentikasi penyediaPenggunaTerautentikasi
    ) {
        return new ProfilApplicationService(repositoriProfil, penyediaPenggunaTerautentikasi);
    }
}