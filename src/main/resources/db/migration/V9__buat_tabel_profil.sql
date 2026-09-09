CREATE TABLE profil (
    pengguna_id CHAR(36) NOT NULL,
    nama_lengkap VARCHAR(100) NULL,
    tanggal_lahir DATE NULL,
    jenis_kelamin VARCHAR(20) NULL,
    url_avatar VARCHAR(500) NULL,
    diperbarui_pada TIMESTAMP(6) NOT NULL,
    PRIMARY KEY (pengguna_id),
    CONSTRAINT fk_profil_pengguna
        FOREIGN KEY (pengguna_id) REFERENCES pengguna (id)
        ON DELETE CASCADE
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_unicode_ci;