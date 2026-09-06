CREATE TABLE identitas_autentikasi (
    id CHAR(36) NOT NULL,
    pengguna_id CHAR(36) NOT NULL,
    penyedia_autentikasi VARCHAR(20) NOT NULL,
    email VARCHAR(100) NULL,
    nomor_telepon VARCHAR(20) NULL,
    pengenal_eksternal VARCHAR(255) NULL,
    hash_kata_sandi VARCHAR(255) NULL,
    dibuat_pada TIMESTAMP(6) NOT NULL,
    diperbarui_pada TIMESTAMP(6) NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT uk_identitas_autentikasi_email UNIQUE (email),
    CONSTRAINT uk_identitas_autentikasi_nomor_telepon UNIQUE (nomor_telepon),
    CONSTRAINT uk_identitas_autentikasi_penyedia_pengenal UNIQUE (penyedia_autentikasi, pengenal_eksternal),
    CONSTRAINT fk_identitas_autentikasi_pengguna
        FOREIGN KEY (pengguna_id) REFERENCES pengguna (id)
        ON DELETE CASCADE
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_unicode_ci;