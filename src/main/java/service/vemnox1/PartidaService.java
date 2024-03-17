package service.vemnox1;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import exception.VemNoX1Exception;
import model.dto.vemnox1.JogadaDTO;
import model.dto.vemnox1.PartidaDTO;
import model.entity.enums.vemnox1.Resultado;
import model.entity.vemnox1.Carta;
import model.entity.vemnox1.CartaNaPartida;
import model.entity.vemnox1.Partida;
import model.repository.vemnox1.CartaPartidaRepository;
import model.repository.vemnox1.CartaRepository;
import model.repository.vemnox1.JogadorRepository;
import model.repository.vemnox1.PartidaRepository;

public class PartidaService {
	
	private static final String FORCA = "Força";
	private static final String INTELIGENCIA = "Inteligência";
	private static final String VELOCIDADE = "Velocidade";

	private JogadorRepository jogadorRepository = new JogadorRepository();
	private PartidaRepository partidaRepository = new PartidaRepository();
	private CartaRepository cartaRepository = new CartaRepository();
	private CartaPartidaRepository cartaPartidaRepository = new CartaPartidaRepository();
	
	public PartidaDTO iniciarPartida(int idJogador) {
		PartidaDTO dto = new PartidaDTO();
		//TODO refatorar separando em métodos distintos
		//Criar uma partida -> inserir Partida [PartidaRepository]
		Partida novaPartida = new Partida();
		novaPartida.setResultado(Resultado.EM_ANDAMENTO);
		novaPartida.setData(LocalDateTime.now());
		novaPartida.setJogador(jogadorRepository.consultarPorId(idJogador));
		novaPartida = partidaRepository.salvar(novaPartida);
		
		//Sortear as 6 cartas -> sortearCartas [CartaRepository]
		ArrayList<Carta> seisCartas = cartaRepository.sortearSeisCartas();
		ArrayList<CartaNaPartida> cartasDoJogador = new ArrayList<CartaNaPartida>();
		
		//Distribuir para jogador e CPU -> inserir CartaPartida
		boolean ehDoJogador = true;
		for(Carta carta : seisCartas) {
			CartaNaPartida cartaDaPartida = new CartaNaPartida();
			cartaDaPartida.setIdPartida(novaPartida.getId());
			cartaDaPartida.setCarta(carta);
			cartaDaPartida.setPertenceAoJogador(ehDoJogador);
			cartaDaPartida = cartaPartidaRepository.salvar(cartaDaPartida);
			
			if(ehDoJogador) {
				cartasDoJogador.add(cartaDaPartida);
			}
			
			//Exclamação é operador de NEGAÇÃO
			//Usado para intercalar a distribuição de cartas entre jogador e CPU
			ehDoJogador = !ehDoJogador;
		}
		
		//Montar o PartidaDTO e retornar 
		dto.setIdPartida(novaPartida.getId());
		dto.setResultadoUltimaJogada(null);
		dto.setAtributosDisponiveis(obterAtributosDisponiveis(novaPartida));
		dto.setCartasJogador(cartasDoJogador);
		return dto;
	}

	private List<String> obterAtributosDisponiveis(Partida novaPartida) {
		ArrayList<String> atributos = new ArrayList<String>();
		
		if(!novaPartida.isJogouForca()) {
			atributos.add(FORCA);
		}
		if(!novaPartida.isJogouInteligencia()) {
			atributos.add(INTELIGENCIA);
		}
		
		if(!novaPartida.isJogouVelocidade()) {
			atributos.add(VELOCIDADE);
		}
		
		return atributos;
	}

	public PartidaDTO jogar(JogadaDTO jogada) throws VemNoX1Exception {
		PartidaDTO partidaAtualizada = new PartidaDTO();
		
		Partida partida = partidaRepository.consultarPorId(jogada.getIdPartida());
		CartaNaPartida cartaSelecionadaPeloJogador = cartaPartidaRepository.consultarPorId(jogada.getIdCartaNaPartidaSelecionada());
		String atributoSelecionado = jogada.getAtributoSelecionado();
		int valorAtributoJogador = obterValorAtributo(cartaSelecionadaPeloJogador.getCarta(), atributoSelecionado);
		
		List<CartaNaPartida> cartasCpuDisponiveis = partida.getCartasCpu().stream().filter(c -> !c.isUtilizada()).toList();
		
		if(cartaSelecionadaPeloJogador.isUtilizada()) {
			throw new VemNoX1Exception("Carta selecionada já utilizada");
		}
		
		if(cartasCpuDisponiveis.isEmpty()) {
			throw new VemNoX1Exception("Todas as cartas foram utilizadas");
		}
		
		if(!obterAtributosDisponiveis(partida).contains(atributoSelecionado)) {
			throw new VemNoX1Exception("Atributo selecionado [" + atributoSelecionado + "] já jogado");
		}
		
		CartaNaPartida cartaCpuSelecionada;
		cartaCpuSelecionada = obterCartaVitoriaCpu(cartasCpuDisponiveis, atributoSelecionado, valorAtributoJogador);
		
		if(cartaCpuSelecionada == null) {
			cartaCpuSelecionada = obterCartaEmpate(cartasCpuDisponiveis, atributoSelecionado, valorAtributoJogador);
		}
		
		if(cartaCpuSelecionada == null) {
			cartaCpuSelecionada = obterPiorCartaCpu(cartasCpuDisponiveis, atributoSelecionado);
		}
		
		int valorAtributoCpu = obterValorAtributo(cartaCpuSelecionada.getCarta(), atributoSelecionado);
		Resultado resultadoJogada = this.aferirResultadoJogada(valorAtributoCpu, valorAtributoJogador);

		this.marcarCartasComoUsadas(cartaSelecionadaPeloJogador, cartaCpuSelecionada);
		this.atualizarJogadaNaPartida(partida, atributoSelecionado, resultadoJogada);
		this.atualizarPartida(partida);
		partida = this.partidaRepository.consultarPorId(partida.getId());
		
		partidaAtualizada.setResultadoUltimaJogada("Atributo selecionado: " + atributoSelecionado
				+ " Carta do jogador [" + cartaSelecionadaPeloJogador.getCarta().getNome() + " - " + valorAtributoJogador + "]" 
				+ " X Carta da CPU [" + cartaSelecionadaPeloJogador.getCarta().getNome() + " - " + valorAtributoCpu + "]" 
				+ "Resultado da jogada: " + resultadoJogada);
		partidaAtualizada.setCartasJogador(partida.getCartasJogador());
		
		return partidaAtualizada;
	}

	private void atualizarPartida(Partida partida) {
		Resultado resultado = Resultado.EM_ANDAMENTO;
		int vitoriasJogador = partida.getRoundsVencidosJogador();
		int vitoriasCpu = partida.getRoundsVencidosCpu();
		int empates = partida.getRoundsEmpatados();
		
		if(vitoriasJogador >= 2) {
			resultado = Resultado.VITORIA_JOGADOR;
		}
		
		if(vitoriasCpu >= 2) {
			resultado = Resultado.VITORIA_CPU;
		}
		
		if(empates == 3 || (empates + vitoriasCpu + vitoriasJogador) == 3) {
			resultado = Resultado.EMPATE;
		}
		
		partida.setResultado(resultado);
	}

	private void atualizarJogadaNaPartida(Partida partida, String atributoSelecionado, Resultado resultadoJogada) {
		switch (atributoSelecionado) {
			case FORCA: {
				partida.setJogouForca(true);
				break;
			}
			case INTELIGENCIA: {
				partida.setJogouInteligencia(true);
				break;
			}
			case VELOCIDADE: {
				partida.setJogouVelocidade(true);
				break;
			}
		}
		
		partida.setResultado(resultadoJogada);
		
		if(resultadoJogada == Resultado.EMPATE) {
			partida.setRoundsEmpatados(partida.getRoundsEmpatados() + 1);
		}
		
		if(resultadoJogada == Resultado.VITORIA_CPU) {
			partida.setRoundsEmpatados(partida.getRoundsVencidosCpu() + 1);
		}
		
		if(resultadoJogada == Resultado.VITORIA_JOGADOR) {
			partida.setRoundsEmpatados(partida.getRoundsVencidosJogador() + 1);
		}
	}

	private CartaNaPartida obterCartaVitoriaCpu(List<CartaNaPartida> cartasCpuDisponiveis, String atributoSelecionado, int valorAtributoJogador) {
		CartaNaPartida cartaCpuSelecionada = null;
		int valorAtributoCpu = 0;
		for(CartaNaPartida cartaCpu: cartasCpuDisponiveis) {
			valorAtributoCpu = obterValorAtributo(cartaCpu.getCarta(), atributoSelecionado);
			
			if(valorAtributoCpu > valorAtributoJogador) {
				cartaCpuSelecionada = cartaCpu;
				break;
			}
		}
		
		return cartaCpuSelecionada;
	}
	
	private CartaNaPartida obterCartaEmpate(List<CartaNaPartida> cartasCpuDisponiveis, String atributoSelecionado, int valorAtributoJogador) {
		CartaNaPartida cartaCpuSelecionada = null;
		int valorAtributoCpu = 0;
		for(CartaNaPartida cartaCpu: cartasCpuDisponiveis) {
			valorAtributoCpu = obterValorAtributo(cartaCpu.getCarta(), atributoSelecionado);
			
			if(valorAtributoCpu == valorAtributoJogador) {
				cartaCpuSelecionada = cartaCpu;
				break;
			}
		}
		
		return cartaCpuSelecionada;
	}

	private CartaNaPartida obterPiorCartaCpu(List<CartaNaPartida> cartasCpuDisponiveis, String atributoSelecionado) {
		CartaNaPartida piorCartaCpuSelecionada = null;
		int piorValorAtributoCpu = 6;
		for(CartaNaPartida cartaCpu: cartasCpuDisponiveis) {
			int valorAtributoCpu = obterValorAtributo(cartaCpu.getCarta(), atributoSelecionado);
			
			if(piorValorAtributoCpu > valorAtributoCpu) {
				piorCartaCpuSelecionada = cartaCpu;
			}
		}
		
		return piorCartaCpuSelecionada;
	}

	private void marcarCartasComoUsadas(CartaNaPartida cartaJogada, CartaNaPartida cartaCpuSelecionada) {
		cartaJogada.setUtilizada(true);
		cartaCpuSelecionada.setUtilizada(true);
		
		this.cartaPartidaRepository.alterar(cartaJogada);
		this.cartaPartidaRepository.alterar(cartaCpuSelecionada);
	}

	private Resultado aferirResultadoJogada(int valorAtributoCpu, int valorAtributoJogador) {
		Resultado resultadoJogada = Resultado.EMPATE;
		
		if(valorAtributoCpu > valorAtributoJogador) {
			resultadoJogada = Resultado.VITORIA_CPU;
		}
		
		if(valorAtributoCpu < valorAtributoJogador) {
			resultadoJogada = Resultado.VITORIA_JOGADOR;
		}

		return resultadoJogada;
	}
	
	private int obterValorAtributo(Carta carta, String atributoSelecionado) {
		switch (atributoSelecionado) {
			case FORCA: {
				return carta.getForca();
			}
			case INTELIGENCIA: {
				return carta.getInteligencia();
			}
			case VELOCIDADE: {
				return carta.getVelocidade();
			}
			default:
				return 0;
		}
	}
}
