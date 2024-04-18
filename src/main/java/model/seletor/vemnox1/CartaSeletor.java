package model.seletor.vemnox1;

import java.time.LocalDate;

import model.seletor.BaseSeletor;

public class CartaSeletor extends BaseSeletor{
	
	private String nome;
	private int forcaMinima;
	private int forcaMaxima;
	private int inteligenciaMinima;
	private int inteligenciaMaxima;
	private int velocidadeMinima;
	private int velocidadeMaxima;
	
	//filtragem de datas por período (início, fim)
	private LocalDate dataInicioCadastro;
	private LocalDate dataFimCadastro;

	/**
	 * Verifica se este seletro tem algum campo preenchido
	 * @return true caso ao menos um dos atributos tenha sido preenchido
	 */
	public boolean temFiltro() {
		return  (this.nome != null && this.nome.trim().length() > 0) 
			 || (this.forcaMinima > 0)
			 || (this.forcaMaxima > 0)
		   	 || (this.inteligenciaMinima > 0)
			 || (this.inteligenciaMaxima > 0)
			 || (this.velocidadeMinima > 0)
			 || (this.velocidadeMaxima > 0)
			 || (this.dataInicioCadastro != null)
			 || (this.dataFimCadastro != null);
	}
	
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

	public LocalDate getDataInicioCadastro() {
		return dataInicioCadastro;
	}

	public void setDataInicioCadastro(LocalDate dataInicioCadastro) {
		this.dataInicioCadastro = dataInicioCadastro;
	}

	public LocalDate getDataFimCadastro() {
		return dataFimCadastro;
	}

	public void setDataFimCadastro(LocalDate dataFimCadastro) {
		this.dataFimCadastro = dataFimCadastro;
	}
}
