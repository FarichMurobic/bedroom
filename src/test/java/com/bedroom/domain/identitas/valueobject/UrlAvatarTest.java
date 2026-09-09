package com.bedroom.domain.identitas.valueobject;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("UrlAvatar")
class UrlAvatarTest {

    @Nested
    @DisplayName("saat nilai valid")
    class SaatNilaiValid {

        @ParameterizedTest
        @DisplayName("menerima URL http/https yang valid")
        @ValueSource(strings = {
                "https://contoh.com/avatar.jpg",
                "http://contoh.com/gambar.png",
                "https://cdn.contoh.com/user/123/foto.webp"
        })
        void menerimaUrlValid(String urlValid) {
            assertThat(new UrlAvatar(urlValid).nilai()).isEqualTo(urlValid);
        }
    }

    @Nested
    @DisplayName("saat nilai tidak valid")
    class SaatNilaiTidakValid {

        @Test
        @DisplayName("melempar exception jika null")
        void melemparException_jikaNull() {
            assertThatThrownBy(() -> new UrlAvatar(null)).isInstanceOf(NullPointerException.class);
        }

        @ParameterizedTest
        @DisplayName("melempar exception untuk format yang tidak valid")
        @ValueSource(strings = {
                "bukan-url",
                "ftp://contoh.com/avatar.jpg",
                "javascript:alert(1)",
                "contoh.com/avatar.jpg"
        })
        void melemparException_untukFormatTidakValid(String urlTidakValid) {
            assertThatThrownBy(() -> new UrlAvatar(urlTidakValid))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("Format URL avatar tidak valid");
        }
    }
}