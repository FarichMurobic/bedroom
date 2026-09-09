CREATE TABLE genre (
    id CHAR(36) NOT NULL,
    nama VARCHAR(30) NOT NULL,
    dibuat_pada TIMESTAMP(6) NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT uk_genre_nama UNIQUE (nama)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_unicode_ci;