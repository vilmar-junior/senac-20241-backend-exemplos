package model.entity.vemnox1;

public class PartidaCarta {

	private int id;

	private Partida partida;
	private Carta carta;
	private boolean pertenceAoJogador;
	private boolean utilizada;

	public PartidaCarta() {
	}

	public PartidaCarta(int id, Partida partida, Carta carta, boolean pertenceAoJogador, boolean utilizada) {
		super();
		this.id = id;
		this.partida = partida;
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

	public Partida getPartida() {
		return partida;
	}

	public void setPartida(Partida partida) {
		this.partida = partida;
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