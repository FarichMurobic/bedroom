package com.bedroom.domain.identitas.repository;

import com.bedroom.domain.identitas.enums.PenyediaAutentikasi;
import com.bedroom.domain.identitas.model.IdentitasAutentikasi;
import com.bedroom.domain.identitas.valueobject.Email;
import com.bedroom.domain.identitas.valueobject.IdIdentitasAutentikasi;
import com.bedroom.domain.identitas.valueobject.NomorTelepon;
import com.bedroom.domain.identitas.valueobject.PengenalEksternal;

import java.util.Optional;

/**
 * Port untuk operasi penyimpanan dan pengambilan data {@code IdentitasAutentikasi}.
 * Implementasi konkret berada di lapisan infrastruktur.
 */
public interface RepositoriIdentitasAutentikasi {

    IdentitasAutentikasi simpan(IdentitasAutentikasi identitasAutentikasi);

    Optional<IdentitasAutentikasi> cariBerdasarkanId(IdIdentitasAutentikasi id);

    Optional<IdentitasAutentikasi> cariBerdasarkanEmail(Email email);

    Optional<IdentitasAutentikasi> cariBerdasarkanNomorTelepon(NomorTelepon nomorTelepon);

    Optional<IdentitasAutentikasi> cariBerdasarkanPenyediaDanPengenalEksternal(
            PenyediaAutentikasi penyediaAutentikasi,
            PengenalEksternal pengenalEksternal
    );

    boolean adaBerdasarkanEmail(Email email);

    boolean adaBerdasarkanNomorTelepon(NomorTelepon nomorTelepon);

    boolean adaBerdasarkanPenyediaDanPengenalEksternal(
            PenyediaAutentikasi penyediaAutentikasi,
            PengenalEksternal pengenalEksternal
    );

    void hapusBerdasarkanId(IdIdentitasAutentikasi id);
}