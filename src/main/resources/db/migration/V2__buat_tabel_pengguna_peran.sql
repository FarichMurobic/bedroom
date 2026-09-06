CREATE TABLE pengguna_peran (
    pengguna_id CHAR(36) NOT NULL,
    peran VARCHAR(20) NOT NULL,
    PRIMARY KEY (pengguna_id, peran),
    CONSTRAINT fk_pengguna_peran_pengguna
        FOREIGN KEY (pengguna_id) REFERENCES pengguna (id)
        ON DELETE CASCADE
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_unicode_ci;