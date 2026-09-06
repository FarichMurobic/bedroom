/*
 * Copyright (c) 2026 Farich Murobic
 * Licensed under the MIT License.
 */
package com.bedroom.domain.identitas.enums;

/**
 * Merepresentasikan status akun seorang {@code Pengguna}.
 * Status ini menentukan hak akses pengguna terhadap fitur-fitur
 * yang membutuhkan verifikasi, seperti menulis dan menerbitkan karya.
 */
public enum StatusPengguna {
    MENUNGGU_VERIFIKASI,
    AKTIF,
    TIDAK_AKTIF,
    DITANGGUHKAN,
    DIBLOKIR

}
