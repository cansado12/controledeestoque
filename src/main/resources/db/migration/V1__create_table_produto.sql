CREATE TABLE produto (
                         id         BIGINT         NOT NULL AUTO_INCREMENT,
                         nome       VARCHAR(150)   NOT NULL,
                         descricao  VARCHAR(255),
                         preco      DECIMAL(10, 2) NOT NULL,
                         quantidade INT            NOT NULL DEFAULT 0,
                         categoria  VARCHAR(100)   NOT NULL,
                         PRIMARY KEY (id)
);