/*
 * Copyright (c) 2026 Farich Murobic
 * Licensed under the MIT License.
 */
package com.bedroom.infrastructure.security.otp;

import com.bedroom.application.identitas.port.PengelolaKodeOtp;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.security.SecureRandom;
import java.time.Duration;
import java.util.Objects;

/**
 * Implementasi {@code PengelolaKodeOtp} yang menyimpan kode di Redis
 * (dengan masa berlaku otomatis) dan mengirimkannya melalui SMS via Twilio.
 */
public final class RedisPengelolaKodeOtp implements PengelolaKodeOtp {

    private static final Duration MASA_BERLAKU_OTP = Duration.ofMinutes(5);
    private static final int PANJANG_KODE_OTP = 6;
    private static final String PREFIKS_KUNCI = "otp:telepon:";

    private final StringRedisTemplate redisTemplate;
    private final String nomorPengirimTwilio;
    private final SecureRandom acak = new SecureRandom();

    public RedisPengelolaKodeOtp(
            StringRedisTemplate redisTemplate,
            String twilioAccountSid,
            String twilioAuthToken,
            String nomorPengirimTwilio
    ) {
        this.redisTemplate = Objects.requireNonNull(
                redisTemplate, "Redis template tidak boleh kosong"
        );
        this.nomorPengirimTwilio = Objects.requireNonNull(
                nomorPengirimTwilio, "Nomor pengirim twilio tidak boleh kosong"
        );
        Objects.requireNonNull(
                twilioAccountSid, "Twilio account Sid tidak boleh kosong"
        );
        Objects.requireNonNull(
                twilioAuthToken, "Twilio auth token tidak boleh kosong"
        );
        Twilio.init(twilioAccountSid, twilioAuthToken);
    }

    @Override
    public void buatDanKirim(String nomorTelepon) {
        Objects.requireNonNull(
                nomorTelepon, "Nomor telepon tidak boleh kosong"
        );

        String kodeOtp = buatKodeAcak();
        redisTemplate.opsForValue().set(kunciUntuk(nomorTelepon), kodeOtp, MASA_BERLAKU_OTP);

        Message.creator(
                new PhoneNumber(nomorTelepon),
                new PhoneNumber(nomorPengirimTwilio),
                "Kode verifikasi Bedroom anda: " +
                        kodeOtp +
                        ". Jangan bagikan kode ini kepada siapapun."
        ).create();
    }

    @Override
    public boolean verifikasi(String nomorTelepon, String kodeOtp) {
        Objects.requireNonNull(
                nomorTelepon, "Nomor telepon tidak boleh kosong"
        );
        Objects.requireNonNull(
                kodeOtp, "Kode OTP tidak boleh kosong"
        );

        String kunci = kunciUntuk(nomorTelepon);
        String kodeTersimpan = redisTemplate.opsForValue().get(kunci);

        if (kodeTersimpan == null || !kodeTersimpan.equals(kodeOtp)) {
            return false;
        }

        redisTemplate.delete(kunci);
        return true;
    }

    private String kunciUntuk(String nomorTelepon) {
        return PREFIKS_KUNCI + nomorTelepon;
    }

    private String buatKodeAcak() {
        int batas = (int) Math.pow(10, PANJANG_KODE_OTP);
        int angka = acak.nextInt(batas);
        return String.format("%0" + PANJANG_KODE_OTP + "%d", angka);
    }
}