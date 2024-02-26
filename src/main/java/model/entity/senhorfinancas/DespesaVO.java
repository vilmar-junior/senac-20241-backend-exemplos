package model.entity.senhorfinancas;

import java.time.LocalDateTime;

public class DespesaVO {
	
	private int idDespesa;
	private int idUsuario;
	private String descricao;
	private double valor;
	private LocalDateTime dataVencimento;
	private LocalDateTime dataPagamento;
	
	public DespesaVO(int idDespesa, int idUsuario, String descricao, double valor, LocalDateTime dataVencimento,
			LocalDateTime dataPagamento) {
		super();
		this.idDespesa = idDespesa;
		this.idUsuario = idUsuario;
		this.descricao = descricao;
		this.valor = valor;
		this.dataVencimento = dataVencimento;
		this.dataPagamento = dataPagamento;
	}

	public DespesaVO() {
		super();
	}

	public int getIdDespesa() {
		return idDespesa;
	}

	public void setIdDespesa(int idDespesa) {
		this.idDespesa = idDespesa;
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

	public LocalDateTime getDataVencimento() {
		return dataVencimento;
	}

	public void setDataVencimento(LocalDateTime dataVencimento) {
		this.dataVencimento = dataVencimento;
	}

	public LocalDateTime getDataPagamento() {
		return dataPagamento;
	}

	public void setDataPagamento(LocalDateTime dataPagamento) {
		this.dataPagamento = dataPagamento;
	}
	
}
