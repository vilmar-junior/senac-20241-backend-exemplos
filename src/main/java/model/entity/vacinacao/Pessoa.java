package model.entity.vacinacao;

import java.time.LocalDate;

public class Pessoa {
	
	private int id;
	private String nome; 
	private String cpf;
	private char sexo;
	private LocalDate dataNascimento;
	private int tipo;
	
	public Pessoa() {
		
	}

	public Pessoa(int id, String nome, String cpf, char sexo, LocalDate dataNascimento, int tipo) {
		super();
		this.id = id;
		this.nome = nome;
		this.cpf = cpf;
		this.sexo = sexo;
		this.dataNascimento = dataNascimento;
		this.tipo = tipo;
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
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	public char getSexo() {
		return sexo;
	}
	public void setSexo(char sexo) {
		this.sexo = sexo;
	}
	public LocalDate getDataNascimento() {
		return dataNascimento;
	}
	public void setDataNascimento(LocalDate dataNascimento) {
		this.dataNascimento = dataNascimento;
	}
	public int getTipo() {
		return tipo;
	}
	public void setTipo(int tipo) {
		this.tipo = tipo;
	}
}
