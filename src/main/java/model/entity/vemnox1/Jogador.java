package model.entity.vemnox1;

import java.time.LocalDate;

import model.entity.enums.vemnox1.PerfilAcesso;

public class Jogador {

	private int id;
	private String nome;
	private String email;
	private String senha;
	private LocalDate dataNascimento;
	private int totalPartidas;
	private double percentualVitorias;
	private PerfilAcesso perfil;
	private String idSessao;

	public Jogador() {
	}
	
	public Jogador(int id, String nome, String email, String senha, LocalDate dataNascimento, int totalPartidas,
			double percentualVitorias, PerfilAcesso perfil) {
		super();
		this.id = id;
		this.nome = nome;
		this.email = email;
		this.senha = senha;
		this.dataNascimento = dataNascimento;
		this.totalPartidas = totalPartidas;
		this.percentualVitorias = percentualVitorias;
		this.perfil = perfil;
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

	public LocalDate getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(LocalDate dataNascimento) {
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

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}


	public PerfilAcesso getPerfil() {
		return perfil;
	}


	public void setPerfil(PerfilAcesso perfil) {
		this.perfil = perfil;
	}

	public String getIdSessao() {
		return idSessao;
	}

	public void setIdSessao(String idSessao) {
		this.idSessao = idSessao;
	}
}