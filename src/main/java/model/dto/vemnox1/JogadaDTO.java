package model.dto.vemnox1;

public class JogadaDTO {
	
	private int idPartida;
	private int idCartaSelecionada;
	private String atributoSelecionado;
	
	public JogadaDTO(int idPartida, int idCartaSelecionada, String atributoSelecionado) {
		super();
		this.idPartida = idPartida;
		this.idCartaSelecionada = idCartaSelecionada;
		this.atributoSelecionado = atributoSelecionado;
	}
	public int getIdPartida() {
		return idPartida;
	}
	public void setIdPartida(int idPartida) {
		this.idPartida = idPartida;
	}
	public int getIdCartaSelecionada() {
		return idCartaSelecionada;
	}
	public void setIdCartaSelecionada(int idCartaSelecionada) {
		this.idCartaSelecionada = idCartaSelecionada;
	}
	public String getAtributoSelecionado() {
		return atributoSelecionado;
	}
	public void setAtributoSelecionado(String atributoSelecionado) {
		this.atributoSelecionado = atributoSelecionado;
	}
}
