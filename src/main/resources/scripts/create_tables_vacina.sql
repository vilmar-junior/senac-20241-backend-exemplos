CREATE TABLE `pessoa` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `nome` VARCHAR(255) NOT NULL,
  `cpf` VARCHAR(11) NOT NULL,
  `sexo` VARCHAR(1) NOT NULL,
  `data_nascimento` DATE NOT NULL,
  `tipo` INT NOT null comment '1- Pesquisador; 2- Voluntário; 3- Público Geral',
  PRIMARY KEY (`id`));

CREATE TABLE `vacina` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `id_pesquisador` INT NOT NULL,
  `nome` VARCHAR(300) NOT NULL,
  `pais_origem` VARCHAR(300) NOT NULL,
  `estagio_pesquisa` INT NOT null comment '1- Inicial; 2- Teste; 3- Aplicação em massa',
  `data_inicio_pesquisa` DATE NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `id_pesquisador` FOREIGN KEY (`id_pesquisador`)
  REFERENCES `pessoa`(`id`));
 
 
 
 CREATE TABLE `aplicacao_vacina` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `id_pessoa` INT NOT NULL,
  `id_vacina` INT NOT NULL,
  `data_aplicacao` DATE NOT NULL,
  `avaliacao` INT NOT null comment 'Valor entre 1 e 5',
  PRIMARY KEY (`id`),
  CONSTRAINT `id_pessoa` FOREIGN KEY (`id_pessoa`) REFERENCES `pessoa`(`id`),
  CONSTRAINT `id_vacina` FOREIGN KEY (`id_vacina`) REFERENCES `pessoa`(`id`)
 );
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
  
  
INSERT INTO VACINAS.PESQUISADOR(NOME, CPF, MATRICULA, DATA_NASCIMENTO)
VALUES('Edson Arantes do Nascimento', '00011122210', 10, '1940-03-21');

INSERT INTO VACINAS.PESQUISADOR(NOME, CPF, MATRICULA, DATA_NASCIMENTO)
VALUES('Marcos André', '00011122205', 5, '1980-05-05');

INSERT INTO VACINAS.PESQUISADOR(NOME, CPF, MATRICULA, DATA_NASCIMENTO)
VALUES('Ronaldinho Gaúcho', '00011122280', 80, '1980-05-05');

ALTER TABLE `vacinas`.`vacina` 
ADD CONSTRAINT `id_responsavel`
  FOREIGN KEY (`id_responsavel`)
  REFERENCES `vacinas`.`pesquisador` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

ALTER TABLE `vacinas`.`vacina` DROP COLUMN `nome_responsavel`;

-- Mostrar nome do país e nome do pesquisador de cada vacina
select v.pais_origem, p.nome 
from vacina v, pesquisador p
where v.id_responsavel = p.id;