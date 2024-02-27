package model.entity.vemnox1;

import java.time.LocalDate;

public class Carta {

	private int id;
	private String nome;
	private int forca;
	private int inteligencia;
	private int velocidade;
	private LocalDate dataCadastro;
	
	public Carta() {
		super();
	}
	public Carta(int id, String nome, int forca, int inteligencia, int velocidade, LocalDate dataCadastro) {
		super();
		this.id = id;
		this.nome = nome;
		this.forca = forca;
		this.inteligencia = inteligencia;
		this.velocidade = velocidade;
		this.dataCadastro = dataCadastro;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public int getForca() {
		return forca;
	}
	public void setForca(int forca) {
		this.forca = forca;
	}
	public int getInteligencia() {
		return inteligencia;
	}
	public void setInteligencia(int inteligencia) {
		this.inteligencia = inteligencia;
	}
	public int getVelocidade() {
		return velocidade;
	}
	public void setVelocidade(int velocidade) {
		this.velocidade = velocidade;
	}
	public LocalDate getDataCadastro() {
		return dataCadastro;
	}
	public void setDataCadastro(LocalDate dataCadastro) {
		this.dataCadastro = dataCadastro;
	}
}