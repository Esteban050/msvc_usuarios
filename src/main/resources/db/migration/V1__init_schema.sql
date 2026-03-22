CREATE TABLE usuarios (
    id BIGSERIAL PRIMARY KEY,
    nombre VARCHAR(255),
    email VARCHAR(255) UNIQUE,
    password VARCHAR(255)
);