USE db_locadora_veiculos;

SELECT * FROM tb_acessorio;
SELECT * FROM tb_aluguel;
SELECT * FROM tb_apolice_seguro;
SELECT * FROM tb_carro;
SELECT * FROM tb_fabricante;
SELECT * FROM tb_funcionario;
SELECT * FROM tb_modelo_carro;
SELECT * FROM tb_motorista;
SELECT * FROM tb_pessoa;
SELECT * FROM tb_carro_acessorio;

-- Faça uma consulta que pegue o nome do carro e nome dos acessórios
SELECT c.nome AS nome_carro, a.descricao AS nome_acessorio
FROM tb_carro c
         JOIN tb_carro_acessorio ca ON c.id = ca.id_carro
         JOIN tb_acessorio a ON ca.id_acessorio = a.id;

-- Faça uma consulta que mostre quantas cada acessório foi associado a um carro
SELECT a.descricao AS nome_acessorio, COUNT(ca.id_acessorio) AS quantidade
FROM tb_acessorio a
         JOIN tb_carro_acessorio ca ON a.id = ca.id_acessorio
GROUP BY a.descricao
ORDER BY quantidade DESC;

