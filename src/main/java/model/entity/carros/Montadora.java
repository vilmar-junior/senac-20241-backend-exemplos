package model.entity.carros;

public class Montadora {
	
	private Integer id;
	private String nome;
	private String paisFundacao;
	
	public Montadora() {
		
	}
	
	public Montadora(Integer id, String nome, String paisFundacao) {
		super();
		this.id = id;
		this.nome = nome;
		this.paisFundacao = paisFundacao;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getPaisFundacao() {
		return paisFundacao;
	}
	public void setPaisFundacao(String paisFundacao) {
		this.paisFundacao = paisFundacao;
	}
}
