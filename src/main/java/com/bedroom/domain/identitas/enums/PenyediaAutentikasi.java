/*
 * Copyright (c) 2026 Farich Murobic
 * Licensed under the MIT License.
 */
package com.bedroom.domain.identitas.enums;

/**
 * Merepresentasikan metode autentikasi yang didukung oleh sistem.
 * Setiap {@code IdentitasAutentikasi} pengguna terikat pada satu penyedia.
 */
public enum PenyediaAutentikasi {
    EMAIL,
    TELEPON,
    GOOGLE
}
