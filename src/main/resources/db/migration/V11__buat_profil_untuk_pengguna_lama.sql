INSERT INTO profil (pengguna_id, nama_lengkap, tanggal_lahir, jenis_kelamin, url_avatar, diperbarui_pada)
SELECT
    p.id,
    NULL,
    NULL,
    NULL,
    NULL,
    NOW(6)
FROM pengguna p
WHERE NOT EXISTS (
    SELECT 1 FROM profil pr WHERE pr.pengguna_id = p.id
);