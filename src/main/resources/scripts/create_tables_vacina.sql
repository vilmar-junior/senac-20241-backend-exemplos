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
  CONSTRAINT `id_vacina` FOREIGN KEY (`id_vacina`) REFERENCES `vacina`(`id`)
 );
 
 -- V2
 CREATE TABLE exemplos.Pais (
	id INT auto_increment NOT NULL,
	Nome varchar(100) NOT NULL,
	Sigla varchar(2) NOT NULL,
	CONSTRAINT Pais_pk PRIMARY KEY (id)
);

ALTER TABLE exemplos.pessoa ADD ID_PAIS INT NULL;
ALTER TABLE exemplos.pessoa ADD CONSTRAINT pessoa_pais_FK FOREIGN KEY (ID_PAIS) REFERENCES exemplos.pais(id);

alter table vacina add column id_pais int;
alter table vacina add CONSTRAINT 
vacina_pais_FK FOREIGN KEY (ID_PAIS) REFERENCES exemplos.pais(id);


 
 