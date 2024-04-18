package service.vemnox1;

import java.time.LocalDateTime;
import java.util.ArrayList;

import exception.VemNoX1Exception;
import model.dto.vemnox1.JogadaDTO;
import model.dto.vemnox1.PartidaDTO;
import model.entity.enums.vemnox1.Resultado;
import model.entity.vemnox1.Carta;
import model.entity.vemnox1.CartaNaPartida;
import model.entity.vemnox1.Jogador;
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
	
	public PartidaDTO iniciarPartida(int idJogador) throws VemNoX1Exception {
		Jogador jogador = jogadorRepository.consultarPorId(idJogador);
		
		if(jogador == null) {
			throw new VemNoX1Exception("Informe um jogador válido");
		}
		
		
		PartidaDTO dto = new PartidaDTO();
		
		//Criar uma partida -> inserir Partida [PartidaRepository]
		Partida novaPartida = new Partida();
		novaPartida.setResultado(Resultado.EM_ANDAMENTO);
		novaPartida.setData(LocalDateTime.now());
		novaPartida.setJogador(jogador);
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
		CartaNaPartida cartaJogada = cartaPartidaRepository.consultarPorId(jogada.getIdCartaNaPartidaSelecionada());
		
		if(jogada.getAtributoSelecionado() == "Força") {
			int valorForca = cartaJogada.getCarta().getForca();
			//TODO continuar
			
		}
		
		
		return partidaAtualizada;
	}

}
