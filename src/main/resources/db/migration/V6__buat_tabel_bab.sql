CREATE TABLE bab (
    id CHAR(36) NOT NULL,
    karya_id CHAR(36) NOT NULL,
    judul VARCHAR(150) NOT NULL,
    isi LONGTEXT NOT NULL,
    urutan INT NOT NULL,
    dibuat_pada TIMESTAMP(6) NOT NULL,
    diperbarui_pada TIMESTAMP(6) NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_bab_karya
        FOREIGN KEY (karya_id) REFERENCES karya (id)
        ON DELETE CASCADE
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_unicode_ci;