package com.bedroom.infrastructure.web.dto;

import com.bedroom.domain.identitas.model.Pengguna;

/**
 * Representasi data {@code Pengguna} yang aman untuk ditampilkan ke klien.
 */
public record PenggunaResponse(
        String id,
        String namaPengguna,
        String status
) {
    public static PenggunaResponse dari(Pengguna pengguna) {
        return new PenggunaResponse(
                pengguna.id().nilai().toString(),
                pengguna.namaPengguna().nilai(),
                pengguna.status().name()
        );
    }
}