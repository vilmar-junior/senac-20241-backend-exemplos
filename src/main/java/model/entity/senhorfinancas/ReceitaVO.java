package model.entity.senhorfinancas;

import java.time.LocalDateTime;

public class ReceitaVO {
	
	private int idReceita;
	private int idUsuario;
	private String descricao;
	private double valor;
	private LocalDateTime dataReceita;
	
	public ReceitaVO(int idReceita, int idUsuario, String descricao, double valor, LocalDateTime dataReceita) {
		super();
		this.idReceita = idReceita;
		this.idUsuario = idUsuario;
		this.descricao = descricao;
		this.valor = valor;
		this.dataReceita = dataReceita;
	}

	public ReceitaVO() {
		super();
	}

	public int getIdReceita() {
		return idReceita;
	}

	public void setIdReceita(int idReceita) {
		this.idReceita = idReceita;
	}

	public int getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

	public LocalDateTime getDataReceita() {
		return dataReceita;
	}

	public void setDataReceita(LocalDateTime dataReceita) {
		this.dataReceita = dataReceita;
	}
	
}
