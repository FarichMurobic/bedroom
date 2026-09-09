/*
 * Copyright (c) 2026 Farich Murobic
 *
 * This project is licensed under the MIT License.
 * See the LICENSE file in the project root for more information.
 */

package com.bedroom.application.identitas.command;

import java.time.LocalDate;

/**
 * Perintah untuk melengkapi atau memperbarui data profil pengguna
 * yang sedang login.
 *
 * <p>Field yang bernilai {@code null} menunjukkan bahwa field tersebut
 * tidak diubah dari nilai sebelumnya.</p>
 *
 * @param namaLengkap nama lengkap pengguna yang akan disimpan, jika diubah
 * @param tanggalLahir tanggal lahir pengguna yang akan disimpan, jika diubah
 * @param jenisKelamin jenis kelamin pengguna yang akan disimpan, jika diubah
 * @param urlAvatar URL avatar pengguna yang akan disimpan, jika diubah
 */
public record LengkapiProfilCommand(
        String namaLengkap,
        LocalDate tanggalLahir,
        String jenisKelamin,
        String urlAvatar
) {
}