CREATE TABLE fornecedor (
                            id       BIGINT       NOT NULL AUTO_INCREMENT,
                            nome     VARCHAR(150) NOT NULL,
                            cnpj     VARCHAR(14)  NOT NULL UNIQUE,
                            email    VARCHAR(150),
                            telefone VARCHAR(20),
                            ativo BOOLEAN NOT NULL DEFAULT TRUE,
                            PRIMARY KEY (id)
);