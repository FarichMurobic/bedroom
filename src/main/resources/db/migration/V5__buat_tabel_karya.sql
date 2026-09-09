CREATE TABLE karya (
    id CHAR(36) NOT NULL,
    penulis_id CHAR(36) NOT NULL,
    judul VARCHAR(150) NOT NULL,
    sinopsis VARCHAR(500) NOT NULL,
    status VARCHAR(20) NOT NULL,
    dibuat_pada TIMESTAMP(6) NOT NULL,
    diperbarui_pada TIMESTAMP(6) NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_karya_penulis
        FOREIGN KEY (penulis_id) REFERENCES pengguna (id)
        ON DELETE CASCADE
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_unicode_ci;