
CREATE TABLE IF NOT EXISTS users(
    id_pembeli SERIAL PRIMARY KEY,
    nama_pembeli VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL
);