CREATE DATABASE miapp;
USE miapp;

CREATE TABLE configuraciones (
usuario    VARCHAR(50),
clave      VARCHAR(50),
valor      VARCHAR(255),
PRIMARY KEY (usuario, clave)
);
