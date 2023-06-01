DROP DATABASE IF EXISTS crudinho;
CREATE DATABASE crudinho CHARACTER SET utf8 COLLATE utf8_general_ci;
USE crudinho;

CREATE TABLE trecos (
    id INT PRIMARY KEY AUTO_INCREMENT,
    data TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    nome VARCHAR(63) NOT NULL,
    descricao VARCHAR(127) NOT NULL,
    localizacao VARCHAR(127) NOT NULL,
    status ENUM('0', '1', '2') DEFAULT '2'
);

INSERT INTO `trecos` (`nome`, `descricao`, `localizacao`) VALUES
('Carlos', 'estudante de Java no Senac', 'Rio de Janeiro'),
('Luana', 'Estudante de Javascript', 'São Paulo'),
('Maria', 'Estudante de Python', 'Rio Grande do Sul'),
('Mario', 'Estudante de React', 'Pará');
