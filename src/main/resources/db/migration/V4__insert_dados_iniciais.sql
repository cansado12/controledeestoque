-- Fornecedores
INSERT INTO fornecedor (nome, cnpj, email, telefone, ativo) VALUES
                                                                ('Tech Supply Ltda',    '12.345.678/0001-90', 'contato@techsupply.com.br',  '(11) 91234-5678', true),
                                                                ('Alimentos Brasil S.A','23.456.789/0001-01', 'vendas@alimentosbrasil.com', '(21) 92345-6789', true),
                                                                ('Moda & Cia',          '34.567.890/0001-12', 'atendimento@modacia.com.br', '(31) 93456-7890', true),
                                                                ('InfoParts Comércio',  '45.678.901/0001-23', 'suporte@infoparts.com.br',   '(41) 94567-8901', true),
                                                                ('CleanPro Distribuidora','56.789.012/0001-34','comercial@cleanpro.com.br', '(51) 95678-9012', true);

-- Produtos
INSERT INTO produto (nome, descricao, preco, quantidade, categoria) VALUES
                                                                        ('Smartphone Galaxy A54',  'Celular Samsung 128GB',          1899.90, 15, 'ELETRONICOS'),
                                                                        ('Notebook Dell Inspiron', 'Notebook i5 8GB RAM 256GB SSD',  3499.00, 8,  'INFORMATICA'),
                                                                        ('Arroz Integral 5kg',     'Arroz integral tipo 1',            25.90, 100, 'ALIMENTOS'),
                                                                        ('Feijão Carioca 1kg',     'Feijão carioca selecionado',        8.50, 80,  'ALIMENTOS'),
                                                                        ('Camiseta Básica',        'Camiseta 100% algodão',            49.90, 50,  'VESTUARIO'),
                                                                        ('Calça Jeans Slim',       'Calça jeans masculina slim fit',  159.90, 30,  'VESTUARIO'),
                                                                        ('Sabonete Dove 90g',      'Sabonete hidratante',               4.50, 200, 'HIGIENE_PESSOAL'),
                                                                        ('Shampoo Pantene 400ml',  'Shampoo para cabelos normais',     22.90, 120, 'HIGIENE_PESSOAL'),
                                                                        ('Detergente Ypê 500ml',   'Detergente neutro',                 3.90, 150, 'LIMPEZA'),
                                                                        ('Mesa de Escritório',     'Mesa em MDF 120x60cm',            459.00, 10,  'MOVEIS');

-- Relacionamentos produto_fornecedor
INSERT INTO produto_fornecedor (produto_id, fornecedor_id) VALUES
                                                               (1, 1),
                                                               (2, 4),
                                                               (3, 2),
                                                               (4, 2),
                                                               (5, 3),
                                                               (6, 3),
                                                               (7, 2),
                                                               (8, 2),
                                                               (9, 5),
                                                               (10,1);