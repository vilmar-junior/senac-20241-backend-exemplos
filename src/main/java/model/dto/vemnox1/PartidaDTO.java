package model.dto.vemnox1;

import java.util.List;

import model.entity.vemnox1.CartaNaPartida;

public class PartidaDTO {
	
	private int idPartida;
	private List<CartaNaPartida> cartaJogador;
	private List<String> atributosDisponiveis;
	private String resultadoUltimaJogada;
	
	public PartidaDTO(int idPartida, List<CartaNaPartida> cartaJogador, List<String> atributosDisponiveis,
			String resultadoUltimaJogada) {
		super();
		this.idPartida = idPartida;
		this.cartaJogador = cartaJogador;
		this.atributosDisponiveis = atributosDisponiveis;
		this.resultadoUltimaJogada = resultadoUltimaJogada;
	}
	public int getIdPartida() {
		return idPartida;
	}
	public void setIdPartida(int idPartida) {
		this.idPartida = idPartida;
	}
	public List<CartaNaPartida> getCartaJogador() {
		return cartaJogador;
	}
	public void setCartaJogador(List<CartaNaPartida> cartaJogador) {
		this.cartaJogador = cartaJogador;
	}
	public List<String> getAtributosDisponiveis() {
		return atributosDisponiveis;
	}
	public void setAtributosDisponiveis(List<String> atributosDisponiveis) {
		this.atributosDisponiveis = atributosDisponiveis;
	}
	public String getResultadoUltimaJogada() {
		return resultadoUltimaJogada;
	}
	public void setResultadoUltimaJogada(String resultadoUltimaJogada) {
		this.resultadoUltimaJogada = resultadoUltimaJogada;
	}
}
