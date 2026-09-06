package com.bedroom.infrastructure.security.password;

import com.bedroom.application.identitas.port.PenghasilHashKataSandi;
import com.bedroom.domain.identitas.valueobject.HashKataSandi;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Objects;

/**
 * Implementasi {@code PenghasilHashKataSandi} menggunakan algoritma BCrypt.
 */
public final class BCryptPenghasilHashKataSandi implements PenghasilHashKataSandi {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public BCryptPenghasilHashKataSandi(BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.bCryptPasswordEncoder = Objects.requireNonNull(
                bCryptPasswordEncoder, "BCrypt password encoder tidak boleh kosong"
        );
    }

    @Override
    public HashKataSandi hash(String kataSandi) {
        Objects.requireNonNull(
                kataSandi, "Kata sandi tidak boleh kosong"
        );

        return new HashKataSandi(
                bCryptPasswordEncoder.encode(kataSandi)
        );
    }
}
