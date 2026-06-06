CREATE DATABASE IF NOT EXISTS codesnippet_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE codesnippet_db;

CREATE TABLE users (
    id_user BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(80) NOT NULL,
    email VARCHAR(120) NOT NULL UNIQUE,
    grupo VARCHAR(30)
);

CREATE TABLE languages (
    id_language BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(40) NOT NULL UNIQUE,
    descripcion VARCHAR(200)
);

CREATE TABLE categories (
    id_category BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(60) NOT NULL UNIQUE,
    descripcion VARCHAR(250)
);

CREATE TABLE tags (
    id_tag BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE snippets (
    id_snippet BIGINT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(120) NOT NULL,
    descripcion VARCHAR(500),
    codigoFuente TEXT NOT NULL,
    fechaCreacion DATE,
    fechaModificacion DATE,
    id_user BIGINT NOT NULL,
    id_language BIGINT NOT NULL,
    id_category BIGINT NOT NULL,
    FOREIGN KEY (id_user) REFERENCES users(id_user),
    FOREIGN KEY (id_language) REFERENCES languages(id_language),
    FOREIGN KEY (id_category) REFERENCES categories(id_category)
);

CREATE TABLE snippet_tags (
    id_snippet BIGINT NOT NULL,
    id_tag BIGINT NOT NULL,
    PRIMARY KEY(id_snippet, id_tag),
    FOREIGN KEY(id_snippet) REFERENCES snippets(id_snippet),
    FOREIGN KEY(id_tag) REFERENCES tags(id_tag)
);
