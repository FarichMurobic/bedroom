/*
 * Copyright (c) 2026 Farich Murobic
 *
 * This project is licensed under the MIT License.
 * See the LICENSE file in the project root for more information.
 */

package com.bedroom.application.identitas.port;

import com.bedroom.domain.identitas.valueobject.HashKataSandi;

/**
 * Port untuk menghasilkan hash dari kata sandi mentah.
 * Implementasi konkret (mis. BCrypt) berada di lapisan infrastruktur.
 */
public interface PenghasilHashKataSandi {

    /**
     * Mengubah kata sandi mentah menjadi hash yang aman.
     *
     * @param kataSandi kata sandi mentah yang akan di-hash
     * @return hash kata sandi dalam bentuk value object
     */
    HashKataSandi hash(String kataSandi);
}