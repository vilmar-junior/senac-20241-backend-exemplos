package service.vemnox1;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

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
		
		ArrayList<String> atributos = new ArrayList<String>();
		atributos.add("Força");
		atributos.add("Inteligência");
		atributos.add("Velocidade");
		
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
		dto.setAtributosDisponiveis(atributos);
		dto.setCartasJogador(cartasDoJogador);
		return dto;
	}

	public PartidaDTO jogar(JogadaDTO jogada) {
		PartidaDTO partidaAtualizada = new PartidaDTO();
		Partida partida = partidaRepository.consultarPorId(jogada.getIdPartida());
		CartaNaPartida cartaSelecionadaPeloJogador = cartaPartidaRepository.consultarPorId(jogada.getIdCartaNaPartidaSelecionada());
		String atributoSelecionado = jogada.getAtributoSelecionado();
		int valorAtributoJogador = obterValorAtributo(cartaSelecionadaPeloJogador.getCarta(), atributoSelecionado);
		
		List<CartaNaPartida> cartasCpuDisponiveis = partida.getCartasCpu().stream().filter(c -> !c.isUtilizada()).toList();
		
		CartaNaPartida cartaCpuSelecionada;
		cartaCpuSelecionada = obterCartaVitoriaCpu(cartasCpuDisponiveis, atributoSelecionado, valorAtributoJogador);
		
		if(cartaCpuSelecionada == null) {
			cartaCpuSelecionada = obterCartaEmpate(cartasCpuDisponiveis, atributoSelecionado, valorAtributoJogador);
		}
		
		if(cartaCpuSelecionada == null) {
			cartaCpuSelecionada = obterPiorCartaCpu(cartasCpuDisponiveis, atributoSelecionado);
		}
		
		this.marcarCartasComoUsadas(cartaSelecionadaPeloJogador, cartaCpuSelecionada);
		partidaAtualizada.setResultadoUltimaJogada(this.aferirResultadoJogada(cartaSelecionadaPeloJogador, cartaCpuSelecionada));
		partidaAtualizada.setCartasJogador(obterCartasJogadorAtualizadas());
		
		return partidaAtualizada;
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
		// TODO Auto-generated method stub
		//partida.setCartasJogador(buscarCartasJogador());
	}

	private String aferirResultadoJogada(CartaNaPartida cartaJogada, CartaNaPartida cartaCpuSelecionada) {
		// TODO Auto-generated method stub
		return null;
	}
	
	private List<CartaNaPartida> obterCartasJogadorAtualizadas() {
		// TODO Auto-generated method stub
		return null;
	}
	
	private int obterValorAtributo(Carta carta, String atributoSelecionado) {
		switch (atributoSelecionado) {
			case "Força": {
				return carta.getForca();
			}
			case "Inteligência": {
				return carta.getInteligencia();
			}
			case "Velocidade": {
				return carta.getVelocidade();
			}
			default:
				return 0;
		}
	}
}
