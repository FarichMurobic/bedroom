package com.bedroom.infrastructure.security.email;

import com.bedroom.application.identitas.port.PengelolaVerifikasiEmail;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.security.SecureRandom;
import java.time.Duration;
import java.util.Base64;
import java.util.Objects;

/**
 * Implementasi {@code PengelolaVerifikasiEmail} yang menyimpan token di Redis
 * (dengan masa berlaku otomatis) dan mengirimkan tautan konfirmasi melalui email.
 */
public final class RedisPengelolaVerifikasiEmail implements PengelolaVerifikasiEmail {

    private static final Duration MASA_BERLAKU_TOKEN = Duration.ofHours(24);
    private static final String PREFIKS_KUNCI = "verifikasi:email:";

    private final StringRedisTemplate redisTemplate;
    private final JavaMailSender javaMailSender;
    private final String alamatEmailPengirim;
    private final String urlDasarVerifikasi;
    private final SecureRandom acak = new SecureRandom();

    public RedisPengelolaVerifikasiEmail(
            StringRedisTemplate redisTemplate,
            JavaMailSender javaMailSender,
            String alamatEmailPengirim,
            String urlDasarVerifikasi
    ) {
        this.redisTemplate = Objects.requireNonNull(
                redisTemplate, "Redis template tidak boleh kosong");
        this.javaMailSender = Objects.requireNonNull(
                javaMailSender, "Java mail sender tidak boleh kosong");
        this.alamatEmailPengirim = Objects.requireNonNull(
                alamatEmailPengirim, "Alamat email pengirim tidak boleh kosong");
        this.urlDasarVerifikasi = Objects.requireNonNull(
                urlDasarVerifikasi, "URL dasar verifikasi tidak boleh kosong");
    }

    @Override
    public void buatDanKirim(String email) {
        Objects.requireNonNull(email, "Email tidak boleh kosong");

        String token = buatTokenAcak();
        redisTemplate.opsForValue().set(kunciUntuk(email), token, MASA_BERLAKU_TOKEN);

        String tautanVerifikasi = urlDasarVerifikasi
                + "?email=" + email
                + "&token=" + token;

        SimpleMailMessage pesan = new SimpleMailMessage();
        pesan.setFrom(alamatEmailPengirim);
        pesan.setTo(email);
        pesan.setSubject("Verifikasi Akun Bedroom Anda");
        pesan.setText(
                "Halo,\n\n"
                        + "Terima kasih telah mendaftar di Bedroom. Silakan klik tautan berikut untuk memverifikasi akun Anda:\n\n"
                        + tautanVerifikasi + "\n\n"
                        + "Tautan ini berlaku selama 24 jam. Jika Anda tidak merasa mendaftar, abaikan email ini."
        );

        javaMailSender.send(pesan);
    }

    @Override
    public boolean verifikasi(String email, String token) {
        Objects.requireNonNull(email, "Email tidak boleh kosong");
        Objects.requireNonNull(token, "Token tidak boleh kosong");

        String kunci = kunciUntuk(email);
        String tokenTersimpan = redisTemplate.opsForValue().get(kunci);

        if (tokenTersimpan == null || !tokenTersimpan.equals(token)) {
            return false;
        }

        redisTemplate.delete(kunci);
        return true;
    }

    private String kunciUntuk(String email) {
        return PREFIKS_KUNCI + email;
    }

    private String buatTokenAcak() {
        byte[] byteAcak = new byte[32];
        acak.nextBytes(byteAcak);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(byteAcak);
    }
}