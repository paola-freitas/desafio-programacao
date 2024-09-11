# 🌌 Desafio de Programação
## 🎯 Objetivos
<p align="justify">
A API Space é projetada para gerenciar sondas em planetas. Ela oferece funcionalidades para criar planetas, implantar sondas espaciais e executar comandos de movimento para as sondas. A API garante que as sondas espaciais operem dentro das restrições de seus respectivos planetas e evitem colisões.
</p>

## 💻 Como Executar
1. Clone este repositório.
2. Configure seu banco de dados PostgreSQL e execute o script de criação das tabelas.
3. Compile e execute a aplicação usando Maven:
  ```
  mvn clean install
  mvn spring-boot:run
  ```
4. Utilize uma ferramenta como Postman para interagir com os endpoints.

## 📚 Regras de Negócio

### 🌍 **Criação do Planeta:**
Defina a área dos planetas onde as sondas irão se movimentar.
  - As dimensões (x, y) do planeta devem ser maiores que zero.
  - Cada planeta tem um ID único e um limite definido (x, y), dentro do qual todas as operações devem ocorrer.

### 🛰️ **Criação da Sonda Espacial:**
Pouse sondas espaciais em planetas específicos e dentro de coordenadas definidas.
  - A sonda espacial deve ser colocada dentro dos limites do planeta.
  - As coordenadas iniciais (x, y) devem ser maiores que zero.
  - A direção da sonda deve ser especificada e válida (por exemplo: NORTH, EAST, SOUTH, WEST).
  - As sondas espaciais não devem colidir entre si. Se uma colisão for detectada, a implantação é rejeitada.

### 🎮 **Execução de Comandos:**
Envie comandos de movimento para sondas espaciais para movimentá-las na do planeta.
  - Os comandos são strings consistindo em instruções válidas (por exemplo, "L" para esquerda, "R" para direita, "M" para mover).
  - A sonda espacial não deve se mover para fora dos limites do planeta.
  - Qualquer comando que resulte em uma operação inválida (por exemplo, mover-se para fora do planeta, colidir com outra sonda) acionará um erro.

## 🚀 Endpoints

### 🌍 POST /api/space/planet
Descrição: Cria um novo planeta com dimensões definidas.

Corpo da Requisição:
```json
{
  "x": 5,
  "y": 5
}
```

- Possíveis retornos:
  - 201 CREATED - com a mensagem contendo o ID do planeta criado;
  - 400 BAD REQUEST - com a mensagem contendo a informação do porquê não foi possível criar o planeta.

### 🛰️ POST /api/space/space-probe
Descrição: Cria uma nova sonda em um planeta já existente.

Corpo da Requisição:
```json
{
   "planetId": 1,
    "x": 3,
    "y": 3,
    "direction": "EAST"
}
```

- Possíveis retornos:
  - 201 CREATED - com a mensagem contendo o ID da sonda criada;
  - 402 NOT FOUND - com a mensagem de que não foi encontrado um planeta com o ID correspondente.
  - 400 BAD REQUEST - com a mensagem contendo a informação do porquê não foi possível criar a sonda.

### 🎮 PUT /api/space/commands
Descrição: Cria uma nova sonda em um planeta já existente.

Parâmetros: 
```
?id=3&commands=LMLMLMLMM
```

- Possíveis retornos:
  - 200 SUCCESS - com a mensagem contendo as novas informações da sonda;
  - 402 NOT FOUND - com a mensagem de que não foi encontrada uma sonda com o ID correspondente.
  - 400 BAD REQUEST - com a mensagem contendo a informação do porquê não foi possível movimentar a sonda.

## 📝 Script de Banco de Dados

Para utilizar a aplicação, utilize o PostgreSQL com o seguinte script:
```sql

CREATE DATABASE space_db;

\c space_db; --comando utilizado no psql para se conectar ao banco

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

```

