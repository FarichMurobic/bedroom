/*
 * Copyright (c) 2026 Farich Murobic
 *
 * This project is licensed under the MIT License.
 * See the LICENSE file in the project root for more information.
 */

package com.bedroom.infrastructure.web;

import com.bedroom.domain.identitas.exception.AkunTidakAktifException;
import com.bedroom.domain.identitas.exception.BukanAdminException;
import com.bedroom.domain.interaksi.exception.TidakBisaMenyukaiKaryaSendiriException;
import com.bedroom.domain.karya.exception.BukanPemilikKaryaException;
import com.bedroom.infrastructure.web.dto.ErrorResponse;
import com.bedroom.shared.exception.AksesDitolakException;
import com.bedroom.shared.exception.KonflikDataException;
import com.bedroom.shared.exception.KredensialTidakValidException;
import com.bedroom.shared.exception.SumberDayaTidakDitemukanException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

/**
 * Menangani seluruh exception yang terjadi di lapisan web, mengubahnya
 * menjadi respons JSON yang konsisten dengan status HTTP yang sesuai,
 * alih-alih halaman error default Spring yang tidak terstruktur.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger LOG = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * Kegagalan validasi anotasi {@code @Valid} pada request body
     * (mis. field kosong yang wajib diisi).
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> tanganiValidasiTidakValid(MethodArgumentNotValidException pengecualian) {
        List<String> detail = pengecualian.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .toList();

        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(), "Data yang dikirim tidak valid", detail
        );
        return ResponseEntity.badRequest().body(errorResponse);
    }

    /**
     * Kesalahan format/validasi input, termasuk pelanggaran invariant
     * value object domain (mis. format email tidak valid).
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> tanganiArgumenTidakValid(IllegalArgumentException pengecualian) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), pengecualian.getMessage());
        return ResponseEntity.badRequest().body(errorResponse);
    }

    /**
     * Sumber daya yang diminta tidak ditemukan.
     */
    @ExceptionHandler(SumberDayaTidakDitemukanException.class)
    public ResponseEntity<ErrorResponse> tanganiSumberDayaTidakDitemukan(SumberDayaTidakDitemukanException pengecualian) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND.value(), pengecualian.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    /**
     * Permintaan bertentangan dengan data yang sudah ada
     * (mis. email atau nama pengguna sudah digunakan).
     */
    @ExceptionHandler(KonflikDataException.class)
    public ResponseEntity<ErrorResponse> tanganiKonflikData(KonflikDataException pengecualian) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.CONFLICT.value(), pengecualian.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }

    /**
     * Kredensial yang diberikan tidak valid (login gagal, OTP/token salah).
     */
    @ExceptionHandler(KredensialTidakValidException.class)
    public ResponseEntity<ErrorResponse> tanganiKredensialTidakValid(KredensialTidakValidException pengecualian) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.UNAUTHORIZED.value(), pengecualian.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
    }

    /**
     * Pengguna dikenali secara sah namun tidak diizinkan melakukan
     * tindakan karena status akunnya (ditangguhkan, diblokir, dst).
     */
    @ExceptionHandler(AksesDitolakException.class)
    public ResponseEntity<ErrorResponse> tanganiAksesDitolak(AksesDitolakException pengecualian) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.FORBIDDEN.value(), pengecualian.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponse);
    }

    /**
     * Pelanggaran terhadap invariant state entity yang tidak masuk kategori
     * spesifik di atas (mis. gagal ganti kata sandi Google, verifikasi ulang
     * akun yang sudah aktif).
     */
    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ErrorResponse> tanganiStatusTidakValid(IllegalStateException pengecualian) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.CONFLICT.value(), pengecualian.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }

    /**
     * Jaring pengaman terakhir untuk exception tak terduga. Detail teknis
     * dicatat penuh ke log server, namun tidak dibocorkan ke klien.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> tanganiKesalahanTidakTerduga(Exception pengecualian) {
        LOG.error("Terjadi kesalahan tak terduga", pengecualian);

        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(), "Terjadi kesalahan pada server, silakan coba lagi nanti"
        );
        return ResponseEntity.internalServerError().body(errorResponse);
    }

    /**
     * Tindakan ditolak karena akun pengguna sedang tidak berstatus aktif.
     */
    @ExceptionHandler(AkunTidakAktifException.class)
    public ResponseEntity<ErrorResponse> tanganiAkunTidakAktif(AkunTidakAktifException pengecualian) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.FORBIDDEN.value(), pengecualian.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponse);
    }

    /**
     * Pengguna mencoba mengubah karya yang bukan miliknya.
     */
    @ExceptionHandler(BukanPemilikKaryaException.class)
    public ResponseEntity<ErrorResponse> tanganiBukanPemilikKarya(BukanPemilikKaryaException pengecualian) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.FORBIDDEN.value(), pengecualian.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponse);
    }

    /**
     * Tindakan hanya dapat dilakukan oleh pengguna berperan ADMIN.
     */
    @ExceptionHandler(BukanAdminException.class)
    public ResponseEntity<ErrorResponse> tanganiBukanAdmin(BukanAdminException pengecualian) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.FORBIDDEN.value(), pengecualian.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponse);
    }

    /**
     * Pengguna mencoba menyukai karya yang ditulis oleh dirinya sendiri.
     */
    @ExceptionHandler(TidakBisaMenyukaiKaryaSendiriException.class)
    public ResponseEntity<ErrorResponse> tanganiTidakBisaMenyukaiKaryaSendiri(TidakBisaMenyukaiKaryaSendiriException pengecualian) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.FORBIDDEN.value(), pengecualian.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponse);
    }
}