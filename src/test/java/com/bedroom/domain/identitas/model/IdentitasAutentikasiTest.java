/*
 * Copyright (c) 2026 Farich Murobic
 * Licensed under the MIT License.
 */
package com.bedroom.domain.identitas.model;

import com.bedroom.domain.identitas.enums.PenyediaAutentikasi;
import com.bedroom.domain.identitas.valueobject.Email;
import com.bedroom.domain.identitas.valueobject.HashKataSandi;
import com.bedroom.domain.identitas.valueobject.IdIdentitasAutentikasi;
import com.bedroom.domain.identitas.valueobject.IdPengguna;
import com.bedroom.domain.identitas.valueobject.NomorTelepon;
import com.bedroom.domain.identitas.valueobject.PengenalEksternal;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("IdentitasAutentikasi")
class IdentitasAutentikasiTest {

    private static final IdPengguna ID_PENGGUNA = IdPengguna.baru();
    private static final Email EMAIL = new Email("budi@contoh.com");
    private static final NomorTelepon NOMOR_TELEPON = new NomorTelepon("+6281234567890");
    private static final PengenalEksternal PENGENAL_EKSTERNAL = new PengenalEksternal("google-sub-12345");
    private static final HashKataSandi HASH_KATA_SANDI = new HashKataSandi("$2a$10$contohHashBcrypt");

    @Nested
    @DisplayName("saat dibuat melalui factory method")
    class SaatDibuatMelaluiFactoryMethod {

        @Test
        @DisplayName("untukEmail() menghasilkan identitas dengan provider EMAIL")
        void untukEmail_menghasilkanIdentitasDenganProviderEmail() {
            IdentitasAutentikasi identitas = IdentitasAutentikasi.untukEmail(ID_PENGGUNA, EMAIL, HASH_KATA_SANDI);

            assertThat(identitas.penyediaAutentikasi()).isEqualTo(PenyediaAutentikasi.EMAIL);
            assertThat(identitas.email()).isEqualTo(EMAIL);
            assertThat(identitas.hashKataSandi()).isEqualTo(HASH_KATA_SANDI);
            assertThat(identitas.nomorTelepon()).isNull();
            assertThat(identitas.pengenalEksternal()).isNull();
            assertThat(identitas.idPengguna()).isEqualTo(ID_PENGGUNA);
            assertThat(identitas.id()).isNotNull();
        }

        @Test
        @DisplayName("untukTelepon() menghasilkan identitas dengan provider TELEPON")
        void untukTelepon_menghasilkanIdentitasDenganProviderTelepon() {
            IdentitasAutentikasi identitas = IdentitasAutentikasi.untukTelepon(ID_PENGGUNA, NOMOR_TELEPON, HASH_KATA_SANDI);

            assertThat(identitas.penyediaAutentikasi()).isEqualTo(PenyediaAutentikasi.TELEPON);
            assertThat(identitas.nomorTelepon()).isEqualTo(NOMOR_TELEPON);
            assertThat(identitas.hashKataSandi()).isEqualTo(HASH_KATA_SANDI);
            assertThat(identitas.email()).isNull();
            assertThat(identitas.pengenalEksternal()).isNull();
        }

        @Test
        @DisplayName("untukGoogle() menghasilkan identitas dengan provider GOOGLE tanpa kata sandi")
        void untukGoogle_menghasilkanIdentitasDenganProviderGoogleTanpaKataSandi() {
            IdentitasAutentikasi identitas = IdentitasAutentikasi.untukGoogle(ID_PENGGUNA, EMAIL, PENGENAL_EKSTERNAL);

            assertThat(identitas.penyediaAutentikasi()).isEqualTo(PenyediaAutentikasi.GOOGLE);
            assertThat(identitas.email()).isEqualTo(EMAIL);
            assertThat(identitas.pengenalEksternal()).isEqualTo(PENGENAL_EKSTERNAL);
            assertThat(identitas.hashKataSandi()).isNull();
            assertThat(identitas.nomorTelepon()).isNull();
        }
    }

    @Nested
    @DisplayName("saat validasi konsistensi field")
    class SaatValidasiKonsistensiField {

        @Test
        @DisplayName("rekonstruksi() melempar exception jika provider EMAIL tanpa email")
        void rekonstruksi_melemparException_jikaProviderEmailTanpaEmail() {
            assertThatThrownBy(() -> IdentitasAutentikasi.rekonstruksi(
                    IdIdentitasAutentikasi.baru(), ID_PENGGUNA, PenyediaAutentikasi.EMAIL,
                    null, null, null, HASH_KATA_SANDI, Instant.now(), Instant.now()
            )).isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("wajib memiliki email");
        }

        @Test
        @DisplayName("rekonstruksi() melempar exception jika provider EMAIL tanpa hash kata sandi")
        void rekonstruksi_melemparException_jikaProviderEmailTanpaHashKataSandi() {
            assertThatThrownBy(() -> IdentitasAutentikasi.rekonstruksi(
                    IdIdentitasAutentikasi.baru(), ID_PENGGUNA, PenyediaAutentikasi.EMAIL,
                    EMAIL, null, null, null, Instant.now(), Instant.now()
            )).isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("wajib memiliki hash kata sandi");
        }

        @Test
        @DisplayName("rekonstruksi() melempar exception jika provider EMAIL memiliki nomor telepon")
        void rekonstruksi_melemparException_jikaProviderEmailMemilikiNomorTelepon() {
            assertThatThrownBy(() -> IdentitasAutentikasi.rekonstruksi(
                    IdIdentitasAutentikasi.baru(), ID_PENGGUNA, PenyediaAutentikasi.EMAIL,
                    EMAIL, NOMOR_TELEPON, null, HASH_KATA_SANDI, Instant.now(), Instant.now()
            )).isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("tidak boleh memiliki nomor telepon");
        }

        @Test
        @DisplayName("rekonstruksi() melempar exception jika provider TELEPON tanpa nomor telepon")
        void rekonstruksi_melemparException_jikaProviderTeleponTanpaNomorTelepon() {
            assertThatThrownBy(() -> IdentitasAutentikasi.rekonstruksi(
                    IdIdentitasAutentikasi.baru(), ID_PENGGUNA, PenyediaAutentikasi.TELEPON,
                    null, null, null, HASH_KATA_SANDI, Instant.now(), Instant.now()
            )).isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("wajib memiliki nomor telepon");
        }

        @Test
        @DisplayName("rekonstruksi() melempar exception jika provider GOOGLE tanpa pengenal eksternal")
        void rekonstruksi_melemparException_jikaProviderGoogleTanpaPengenalEksternal() {
            assertThatThrownBy(() -> IdentitasAutentikasi.rekonstruksi(
                    IdIdentitasAutentikasi.baru(), ID_PENGGUNA, PenyediaAutentikasi.GOOGLE,
                    EMAIL, null, null, null, Instant.now(), Instant.now()
            )).isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("wajib memiliki pengenal eksternal");
        }

        @Test
        @DisplayName("rekonstruksi() melempar exception jika provider GOOGLE memiliki hash kata sandi")
        void rekonstruksi_melemparException_jikaProviderGoogleMemilikiHashKataSandi() {
            assertThatThrownBy(() -> IdentitasAutentikasi.rekonstruksi(
                    IdIdentitasAutentikasi.baru(), ID_PENGGUNA, PenyediaAutentikasi.GOOGLE,
                    EMAIL, null, PENGENAL_EKSTERNAL, HASH_KATA_SANDI, Instant.now(), Instant.now()
            )).isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("tidak boleh memiliki nomor telepon atau kata sandi");
        }

        @Test
        @DisplayName("rekonstruksi() berhasil untuk kombinasi field yang valid per provider")
        void rekonstruksi_berhasil_untukKombinasiFieldValid() {
            assertThatCode(() -> IdentitasAutentikasi.rekonstruksi(
                    IdIdentitasAutentikasi.baru(), ID_PENGGUNA, PenyediaAutentikasi.GOOGLE,
                    EMAIL, null, PENGENAL_EKSTERNAL, null, Instant.now(), Instant.now()
            )).doesNotThrowAnyException();
        }
    }

    @Nested
    @DisplayName("saat mengganti kata sandi")
    class SaatGantiKataSandi {

        @Test
        @DisplayName("gantiKataSandi() berhasil untuk identitas berbasis email")
        void gantiKataSandi_berhasil_untukIdentitasEmail() {
            IdentitasAutentikasi identitas = IdentitasAutentikasi.untukEmail(ID_PENGGUNA, EMAIL, HASH_KATA_SANDI);
            HashKataSandi hashBaru = new HashKataSandi("$2a$10$hashBaru");

            identitas.gantiKataSandi(hashBaru);

            assertThat(identitas.hashKataSandi()).isEqualTo(hashBaru);
        }

        @Test
        @DisplayName("gantiKataSandi() berhasil untuk identitas berbasis telepon")
        void gantiKataSandi_berhasil_untukIdentitasTelepon() {
            IdentitasAutentikasi identitas = IdentitasAutentikasi.untukTelepon(ID_PENGGUNA, NOMOR_TELEPON, HASH_KATA_SANDI);
            HashKataSandi hashBaru = new HashKataSandi("$2a$10$hashBaru");

            identitas.gantiKataSandi(hashBaru);

            assertThat(identitas.hashKataSandi()).isEqualTo(hashBaru);
        }

        @Test
        @DisplayName("gantiKataSandi() melempar exception untuk identitas berbasis Google")
        void gantiKataSandi_melemparException_untukIdentitasGoogle() {
            IdentitasAutentikasi identitas = IdentitasAutentikasi.untukGoogle(ID_PENGGUNA, EMAIL, PENGENAL_EKSTERNAL);
            HashKataSandi hashBaru = new HashKataSandi("$2a$10$hashBaru");

            assertThatThrownBy(() -> identitas.gantiKataSandi(hashBaru))
                    .isInstanceOf(IllegalStateException.class)
                    .hasMessageContaining("tidak menggunakan kata sandi Bedroom");
        }
    }
}