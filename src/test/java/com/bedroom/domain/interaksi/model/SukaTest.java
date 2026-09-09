package com.bedroom.domain.interaksi.model;

import com.bedroom.domain.identitas.valueobject.IdPengguna;
import com.bedroom.domain.karya.valueobject.IdKarya;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Suka")
class SukaTest {

    @Test
    @DisplayName("buat() menghasilkan suka dengan data yang sesuai")
    void buat_menghasilkanSukaDenganDataSesuai() {
        IdPengguna idPengguna = IdPengguna.baru();
        IdKarya idKarya = IdKarya.baru();

        Suka suka = Suka.buat(idPengguna, idKarya);

        assertThat(suka.idPengguna()).isEqualTo(idPengguna);
        assertThat(suka.idKarya()).isEqualTo(idKarya);
        assertThat(suka.id()).isNotNull();
        assertThat(suka.disukaiPada()).isNotNull();
    }
}