CREATE TABLE produto_fornecedor (
                                    produto_id    BIGINT NOT NULL,
                                    fornecedor_id BIGINT NOT NULL,
                                    PRIMARY KEY (produto_id, fornecedor_id),
                                    CONSTRAINT fk_pf_produto
                                        FOREIGN KEY (produto_id)
                                            REFERENCES produto (id)
                                            ON DELETE CASCADE,
                                    CONSTRAINT fk_pf_fornecedor
                                        FOREIGN KEY (fornecedor_id)
                                            REFERENCES fornecedor (id)
                                            ON DELETE CASCADE
);