package model.entity.vemnox1;

import java.util.Calendar;

public class Jogador {

	private int id;
	private String nome;
	private String email;
	//private String senha;
	private Calendar dataNascimento;
	private int totalPartidas;
	private double percentualVitorias;

	public Jogador() {
	}
	
	public Jogador(int id, String nome, String email, Calendar dataNascimento, int totalPartidas,
			double percentualVitorias) {
		super();
		this.id = id;
		this.nome = nome;
		this.email = email;
		this.dataNascimento = dataNascimento;
		this.totalPartidas = totalPartidas;
		this.percentualVitorias = percentualVitorias;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Calendar getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Calendar dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public int getTotalPartidas() {
		return totalPartidas;
	}

	public void setTotalPartidas(int totalPartidas) {
		this.totalPartidas = totalPartidas;
	}

	public double getPercentualVitorias() {
		return percentualVitorias;
	}

	public void setPercentualVitorias(double percentualVitorias) {
		this.percentualVitorias = percentualVitorias;
	}
}