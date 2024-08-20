# desafio-programacao
Repositório destinado ao desenvolvimento da solução do desafio criado pela empresa Elo7 para vaga de desenvolvedor Java.

Link para a utilização da documentação da API: http://localhost:8080/v3/api-docs

Queries para criação das tabelas do banco:

CREATE TABLE planet (
id SERIAL PRIMARY KEY,
x INTEGER NOT NULL,
y INTEGER NOT NULL
);

CREATE TABLE space_probe (
id SERIAL PRIMARY KEY,
x INTEGER NOT NULL,
y INTEGER NOT NULL,
direction VARCHAR(10) NOT NULL,
planet_id INTEGER NOT NULL,
CONSTRAINT fk_planet
FOREIGN KEY(planet_id)
REFERENCES planet(id)
ON DELETE CASCADE
);
