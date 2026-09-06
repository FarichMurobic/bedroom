package com.bedroom.domain.identitas.repository;

import com.bedroom.domain.identitas.model.Pengguna;
import com.bedroom.domain.identitas.valueobject.IdPengguna;
import com.bedroom.domain.identitas.valueobject.NamaPengguna;

import java.util.Optional;

/**
 * Port untuk operasi penyimpanan dan pengambilan data {@code Pengguna}.
 * Implementasi konkret berada di lapisan infrastruktur.
 */
public interface RepositoriPengguna {

    Pengguna simpan(Pengguna pengguna);

    Optional<Pengguna> cariBerdasarkanId(IdPengguna id);

    Optional<Pengguna> cariBerdasarkanNamaPengguna(NamaPengguna namaPengguna);

    boolean adaBerdasarkanNamaPengguna(NamaPengguna namaPengguna);

    void hapusBerdasarkanId(IdPengguna id);
}