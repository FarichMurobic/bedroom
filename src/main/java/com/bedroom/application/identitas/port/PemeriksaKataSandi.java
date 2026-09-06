/*
 * Copyright (c) 2026 Farich Murobic
 * Licensed under the MIT License.
 */
package com.bedroom.application.identitas.port;

import com.bedroom.domain.identitas.valueobject.HashKataSandi;

/**
 * Port untuk memeriksa apakah kata sandi mentah cocok dengan hash yang tersimpan.
 * Implementasi konkret (mis. BCrypt) berada di lapisan infrastruktur.
 */
public interface PemeriksaKataSandi {

    boolean cocok(String kataSandi, HashKataSandi hashKataSandi);
}