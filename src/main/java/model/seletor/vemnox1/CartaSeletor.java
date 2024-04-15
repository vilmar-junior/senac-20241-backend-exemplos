package model.seletor.vemnox1;

import model.seletor.BaseSeletor;

public class CartaSeletor extends BaseSeletor{
	
	private String nome;
	private int forcaMinima;
	private int forcaMaxima;
	private int inteligenciaMinima;
	private int inteligenciaMaxima;
	private int velocidadeMinima;
	private int velocidadeMaxima;

	public CartaSeletor() {
		
	}
	
	public CartaSeletor(String nome, int forcaMinima, int forcaMaxima, int inteligenciaMinima, int inteligenciaMaxima,
			int velocidadeMinima, int velocidadeMaxima) {
		super();
		this.nome = nome;
		this.forcaMinima = forcaMinima;
		this.forcaMaxima = forcaMaxima;
		this.inteligenciaMinima = inteligenciaMinima;
		this.inteligenciaMaxima = inteligenciaMaxima;
		this.velocidadeMinima = velocidadeMinima;
		this.velocidadeMaxima = velocidadeMaxima;
	}
	
	public boolean temFiltro() {
		boolean temFiltroPreenchido = false;
		
		if(this.nome != null && this.nome.trim().length() > 0) {
			temFiltroPreenchido = true;
		}
		if(this.forcaMinima > 0) {
			temFiltroPreenchido = true;
		}
		if(this.forcaMaxima > 0) {
			temFiltroPreenchido = true;
		}
		if(this.inteligenciaMinima > 0) {
			temFiltroPreenchido = true;
		}
		if(this.inteligenciaMaxima > 0) {
			temFiltroPreenchido = true;
		}
		if(this.velocidadeMinima > 0) {
			temFiltroPreenchido = true;
		}
		if(this.velocidadeMaxima > 0) {
			temFiltroPreenchido = true;
		}
		
		return temFiltroPreenchido;
	}
	
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public int getForcaMinima() {
		return forcaMinima;
	}
	public void setForcaMinima(int forcaMinima) {
		this.forcaMinima = forcaMinima;
	}
	public int getForcaMaxima() {
		return forcaMaxima;
	}
	public void setForcaMaxima(int forcaMaxima) {
		this.forcaMaxima = forcaMaxima;
	}
	public int getInteligenciaMinima() {
		return inteligenciaMinima;
	}
	public void setInteligenciaMinima(int inteligenciaMinima) {
		this.inteligenciaMinima = inteligenciaMinima;
	}
	public int getInteligenciaMaxima() {
		return inteligenciaMaxima;
	}
	public void setInteligenciaMaxima(int inteligenciaMaxima) {
		this.inteligenciaMaxima = inteligenciaMaxima;
	}
	public int getVelocidadeMinima() {
		return velocidadeMinima;
	}
	public void setVelocidadeMinima(int velocidadeMinima) {
		this.velocidadeMinima = velocidadeMinima;
	}
	public int getVelocidadeMaxima() {
		return velocidadeMaxima;
	}
	public void setVelocidadeMaxima(int velocidadeMaxima) {
		this.velocidadeMaxima = velocidadeMaxima;
	}
}
