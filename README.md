# Projeto da disciplina de Desenvolvimento Desktop - 2023/1 (<span><strong>&#10003;</strong></span>)

## Estudo de caso 1: Vem no X1
* Jogadores (nome, login, senha e data de nascimento) cadastrados no sistema
* Cada carta possui 3 atributos (força, inteligência e velocidade), com valores de 1 a 5 estrelas
* Em cada partida o jogador e a CPU receberão 3 cartas cada, sorteadas e distintas
* Jogador inicia a rodada escolhendo a carta e o atributo que será usado
* CPU responde com outra carta
* Caso o atributo escolhido seja igual em ambas as cartas, jogador escolhe o atributo seguinte para desempate
* Vence quem ganhar mais duelos

### O sistema deve:
1. Cadastrar jogadores
2. Cadastrar cartas, onde o somatório dos 3 atributos não pode exceder 10 estrelas
3. Realizar partidas
4. Armazenar o histórico das partidas (data, jogador, cartas e resultado)


### Camada Model
###### Modelagem E-R

1. Tabela JOGADOR: ID(PK), NOME, LOGIN, PERCENTUAL_VITORIAS, TOTAL_PARTIDAS, DATA_NASCIMENTO 
2. Tabela PARTIDA: ID(PK), ID_JOGADOR(FK), ROUNDS_VENCIDOS_JOGADOR, ROUNDS_VENCIDOS_CPU, ROUNDS_EMPATADOS, RESULTADO, DATA 
3. Tabela CARTA: ID(PK), NOME, FORCA, INTELIGENCIA, VELOCIDADE, DATA_CADASTRO

###### Entidades
###### Repositórios / Data Access Objects (DAOs)
###### Cada classe Repository deve conter os seguintes métodos: 
* inserir(Entidade novoObjeto)
* atualizar(Entidade objetoParaAtualizar)
* excluir(int id)
* consultarPorId(int id) 
* consultarTodos()
* consultarComSeletor(Seletor seletor)

1. JogadorRepository 
2. PartidaRepository 
3. CartaRepository

###### Services / Business Objects (BOs)
* Classes que encapsulam as **regras de negócio** do sistema

1. JogadorService: (i) não permitir cadastro com login repetido
2. PartidaService:  (i) toda partida deve possuir 1 jogador, 3 cartas para o jogador e 3 cartas para a CPU (todas distintas)
3. CartaService: (i) ao cadastrar/editar uma carta, o somatório dos pontos dos 3 atributos (força, inteligência e velocidade) deve ser menor ou igual a 10

### Camada Controller
##### Classes responsáveis por: 

* Receber dados ou objetos da camada de view
* Realizar validações
* Controlar o fluxo de telas
* Chamar a camada de model para persistências ou consultas de dados
* Classes Controller, Service ou Servlet (varia conforme a arquitetura)


1. JogadorController: validar campos obrigatórios antes de inserir/atualizar
2. CartaController: validar campos obrigatórios antes de inserir/atualizar (<span><strong>&#10003;</strong></span>)
3. PartidaController: validar campos obrigatórios antes de inserir/atualizar
