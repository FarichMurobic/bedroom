/*
 * Copyright (c) 2026 Farich Murobic
 *
 * This project is licensed under the MIT License.
 * See the LICENSE file in the project root for more information.
 */

package com.bedroom.domain.identitas.enums;

/**
 * Merepresentasikan penyedia autentikasi yang didukung oleh sistem.
 *
 * <p>Setiap {@code IdentitasAutentikasi} pengguna terikat pada satu penyedia
 * autentikasi.</p>
 */
public enum PenyediaAutentikasi {
    EMAIL,
    TELEPON,
    GOOGLE
}