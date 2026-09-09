CREATE TABLE suka (
    id CHAR(36) NOT NULL,
    pengguna_id CHAR(36) NOT NULL,
    karya_id CHAR(36) NOT NULL,
    disukai_pada TIMESTAMP(6) NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT uk_suka_pengguna_karya UNIQUE (pengguna_id, karya_id),
    CONSTRAINT fk_suka_pengguna
        FOREIGN KEY (pengguna_id) REFERENCES pengguna (id)
        ON DELETE CASCADE,
    CONSTRAINT fk_suka_karya
        FOREIGN KEY (karya_id) REFERENCES karya (id)
        ON DELETE CASCADE
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_unicode_ci;