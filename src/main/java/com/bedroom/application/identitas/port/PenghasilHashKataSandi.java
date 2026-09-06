/*
 * Copyright (c) 2026 Farich Murobic
 * Licensed under the MIT License.
 */
package com.bedroom.application.identitas.port;

import com.bedroom.domain.identitas.valueobject.HashKataSandi;

/**
 * Port untuk menghasilkan hash dari kata sandi mentah.
 * Implementasi konkret (mis. BCrypt) berada di lapisan infrastruktur.
 */
public interface PenghasilHashKataSandi {

    HashKataSandi hash(String kataSandi);
}