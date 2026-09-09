CREATE TABLE karya_genre (
    karya_id CHAR(36) NOT NULL,
    genre_id CHAR(36) NOT NULL,
    PRIMARY KEY (karya_id, genre_id),
    CONSTRAINT fk_karya_genre_karya
        FOREIGN KEY (karya_id) REFERENCES karya (id)
        ON DELETE CASCADE,
    CONSTRAINT fk_karya_genre_genre
        FOREIGN KEY (genre_id) REFERENCES genre (id)
        ON DELETE CASCADE
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_unicode_ci;