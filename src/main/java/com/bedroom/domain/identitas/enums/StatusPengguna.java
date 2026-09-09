/*
 * Copyright (c) 2026 Farich Murobic
 *
 * This project is licensed under the MIT License.
 * See the LICENSE file in the project root for more information.
 */

package com.bedroom.domain.identitas.enums;

/**
 * Merepresentasikan status akun seorang {@code Pengguna}.
 *
 * <p>Status ini menentukan akses pengguna terhadap fitur-fitur tertentu
 * yang membutuhkan kondisi akun tertentu, seperti menulis dan menerbitkan karya.</p>
 */
public enum StatusPengguna {
    MENUNGGU_VERIFIKASI,
    AKTIF,
    TIDAK_AKTIF,
    DITANGGUHKAN,
    DIBLOKIR
}