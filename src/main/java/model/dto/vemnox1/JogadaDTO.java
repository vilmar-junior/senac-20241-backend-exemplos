package model.dto.vemnox1;

public class JogadaDTO {
	
	private int idPartida;
	private int idCartaNaPartidaSelecionada;
	private String atributoSelecionado;
	
	public JogadaDTO(int idPartida, int idCartaSelecionada, String atributoSelecionado) {
		super();
		this.idPartida = idPartida;
		this.idCartaNaPartidaSelecionada = idCartaSelecionada;
		this.atributoSelecionado = atributoSelecionado;
	}
	public int getIdPartida() {
		return idPartida;
	}
	public void setIdPartida(int idPartida) {
		this.idPartida = idPartida;
	}
	
	public int getIdCartaNaPartidaSelecionada() {
		return idCartaNaPartidaSelecionada;
	}
	public void setIdCartaNaPartidaSelecionada(int idCartaNaPartidaSelecionada) {
		this.idCartaNaPartidaSelecionada = idCartaNaPartidaSelecionada;
	}
	public String getAtributoSelecionado() {
		return atributoSelecionado;
	}
	public void setAtributoSelecionado(String atributoSelecionado) {
		this.atributoSelecionado = atributoSelecionado;
	}
}
