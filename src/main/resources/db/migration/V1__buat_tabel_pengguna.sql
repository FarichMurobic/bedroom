CREATE TABLE pengguna (
    id CHAR(36) NOT NULL,
    nama_pengguna VARCHAR(30) NOT NULL,
    status VARCHAR(30) NOT NULL,
    dibuat_pada TIMESTAMP(6) NOT NULL,
    diperbarui_pada TIMESTAMP(6) NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT uk_pengguna_nama_pengguna UNIQUE (nama_pengguna)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_unicode_ci;