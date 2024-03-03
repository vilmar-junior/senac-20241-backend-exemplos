package model.entity.vemnox1;

public class CartaNaPartida {
	private int id;
	private int idPartida;
	private Carta carta;
	private boolean pertenceAoJogador;
	private boolean utilizada;

	public CartaNaPartida() {
	}
	
	public CartaNaPartida(int id, int idPartida, Carta carta, boolean pertenceAoJogador, boolean utilizada) {
		super();
		this.id = id;
		this.idPartida = idPartida;
		this.carta = carta;
		this.pertenceAoJogador = pertenceAoJogador;
		this.utilizada = utilizada;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getIdPartida() {
		return idPartida;
	}

	public void setIdPartida(int idPartida) {
		this.idPartida = idPartida;
	}

	public Carta getCarta() {
		return carta;
	}

	public void setCarta(Carta carta) {
		this.carta = carta;
	}

	public boolean isPertenceAoJogador() {
		return pertenceAoJogador;
	}

	public void setPertenceAoJogador(boolean pertenceAoJogador) {
		this.pertenceAoJogador = pertenceAoJogador;
	}

	public boolean isUtilizada() {
		return utilizada;
	}

	public void setUtilizada(boolean utilizada) {
		this.utilizada = utilizada;
	}
}