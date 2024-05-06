package model.seletor;

public class VacinaSeletor extends BaseSeletor {
	
	private String nomePais;
	private String nomePesquisador;
	private String nomeVacina;
	
	public VacinaSeletor() {
		
	}
	
	public String getNomePais() {
		return nomePais;
	}
	public void setNomePais(String nomePais) {
		this.nomePais = nomePais;
	}
	public String getNomePesquisador() {
		return nomePesquisador;
	}
	public void setNomePesquisador(String nomePesquisador) {
		this.nomePesquisador = nomePesquisador;
	}
	public String getNomeVacina() {
		return nomeVacina;
	}
	public void setNomeVacina(String nomeVacina) {
		this.nomeVacina = nomeVacina;
	}
}
