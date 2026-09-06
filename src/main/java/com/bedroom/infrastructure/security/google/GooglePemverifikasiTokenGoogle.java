/*
 * Copyright (c) 2026 Farich Murobic
 * Licensed under the MIT License.
 */
package com.bedroom.infrastructure.security.google;

import com.bedroom.application.identitas.port.PemverifikasiTokenGoogle;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;

import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.Objects;

/**
 * Implementasi {@code PemverifikasiTokenGoogle} menggunakan pustaka resmi
 * Google API Client untuk memverifikasi signature dan klaim ID Token.
 */
public final class GooglePemverifikasiTokenGoogle implements PemverifikasiTokenGoogle {

    private final GoogleIdTokenVerifier googleIdTokenVerifier;

    public GooglePemverifikasiTokenGoogle(String googleClientId) {
        Objects.requireNonNull(googleClientId, "Google client id tidak boleh kosong");
        try {
            this.googleIdTokenVerifier = new GoogleIdTokenVerifier.Builder(
                    GoogleNetHttpTransport.newTrustedTransport(),
                    GsonFactory.getDefaultInstance()
            )
                    .setAudience(Collections.singletonList(googleClientId))
                    .build();
        } catch (GeneralSecurityException | java.io.IOException e) {
            throw new IllegalStateException(
                    "Gagal menginisialisasi pemverifikasi token Google", e
            );
        }
    }

    @Override
    public DataPenggunaGoogle verifikasi(String idTokenGoogle) {
        Objects.requireNonNull(idTokenGoogle, "ID token Google tidak boleh kosong");

        GoogleIdToken googleIdToken;
        try {
            googleIdToken = googleIdTokenVerifier.verify(idTokenGoogle);
        } catch (GeneralSecurityException | java.io.IOException pengecualian) {
            throw new IllegalArgumentException(
                    "Gagal memverifikasi token Google", pengecualian
            );
        }

        if (googleIdToken == null) {
            throw new IllegalArgumentException("Token Google tidak valid atau sudah kedaluwarsa");
        }

        GoogleIdToken.Payload payload = googleIdToken.getPayload();

        String email = payload.getEmail();
        String pengenalEksternal = payload.getSubject();
        String namaTampilan = (String) payload.get("name");

        if (email == null || pengenalEksternal == null) {
            throw new IllegalArgumentException("Token Google tidak memuat email atau pengenal yang valid");
        }
        if (namaTampilan == null || namaTampilan.isBlank()) {
            namaTampilan = email.substring(0, email.indexOf('@'));
        }

        return new DataPenggunaGoogle(email, pengenalEksternal, namaTampilan);
    }
}