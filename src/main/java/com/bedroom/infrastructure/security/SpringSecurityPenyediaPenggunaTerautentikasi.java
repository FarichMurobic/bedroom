package com.bedroom.infrastructure.security;

import com.bedroom.application.security.PenggunaTerautentikasi;
import com.bedroom.application.security.PenyediaPenggunaTerautentikasi;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public final class SpringSecurityPenyediaPenggunaTerautentikasi
        implements PenyediaPenggunaTerautentikasi {

    @Override
    public PenggunaTerautentikasi ambilPenggunaTerautentikasi() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new IllegalStateException("Tidak ada pengguna yang terautentikasi");
        }

        Object principal = authentication.getPrincipal();

        if (!(principal instanceof PenggunaTerautentikasi penggunaTerautentikasi)) {
            throw new IllegalStateException("Principal yang terautentikasi tidak valid");
        }

        return penggunaTerautentikasi;
    }
}