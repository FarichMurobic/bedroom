/*
 * Copyright (c) 2026 Farich Murobic
 *
 * This project is licensed under the MIT License.
 * See the LICENSE file in the project root for more information.
 */

package com.bedroom.application.identitas.port;

import com.bedroom.domain.identitas.valueobject.HashKataSandi;

/**
 * Port untuk memeriksa apakah kata sandi mentah cocok dengan hash
 * yang tersimpan.
 *
 * <p>Interface ini mendefinisikan kontrak yang dibutuhkan lapisan aplikasi
 * tanpa bergantung pada mekanisme hashing tertentu. Implementasi konkret,
 * seperti {@code BCrypt}, disediakan oleh lapisan infrastruktur.</p>
 */
public interface PemeriksaKataSandi {

    /**
     * Memeriksa apakah kata sandi mentah sesuai dengan hash kata sandi
     * yang tersimpan.
     *
     * @param kataSandi kata sandi mentah yang akan diperiksa
     * @param hashKataSandi hash kata sandi yang tersimpan
     * @return {@code true} jika kata sandi cocok dengan hash,
     *         atau {@code false} jika tidak cocok
     */
    boolean cocok(String kataSandi, HashKataSandi hashKataSandi);
}