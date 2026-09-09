/*
 * Copyright (c) 2026 Farich Murobic
 *
 * This project is licensed under the MIT License.
 * See the LICENSE file in the project root for more information.
 */

package com.bedroom.infrastructure.security.password;

import com.bedroom.application.identitas.port.PemeriksaKataSandi;
import com.bedroom.domain.identitas.valueobject.HashKataSandi;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Objects;

/**
 * Implementasi {@code PemeriksaKataSandi} menggunakan algoritma BCrypt.
 */
public final class BCryptPemeriksaKataSandi implements PemeriksaKataSandi {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public BCryptPemeriksaKataSandi(BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.bCryptPasswordEncoder = Objects.requireNonNull(
                bCryptPasswordEncoder, "BCrypt password encoder tidak boleh kosong"
        );
    }

    @Override
    public boolean cocok(String kataSandi, HashKataSandi hashKataSandi) {
        Objects.requireNonNull(
                kataSandi, "Kata sandi tidak boleh kosong"
        );
        Objects.requireNonNull(
                hashKataSandi, "Hash kata sandi tidak boleh kosong"
        );

        return bCryptPasswordEncoder.matches(kataSandi, hashKataSandi.nilai());
    }
}
