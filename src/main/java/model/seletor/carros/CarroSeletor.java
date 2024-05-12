package model.seletor.carros;

import model.seletor.BaseSeletor;

public class CarroSeletor extends BaseSeletor{
	
	private String nomeMarca;
	private String modelo;
	private Integer anoInicial;
	private Integer anoFinal;
	
	public boolean temFiltro() {
		return  (this.nomeMarca != null && this.nomeMarca.trim().length() > 0) 
			 || (this.modelo != null && this.modelo.trim().length() > 0) 
			 || (this.anoInicial != null)
		   	 || (this.anoFinal != null);
	}
	
	public CarroSeletor() {
		
	}

	public String getNomeMarca() {
		return nomeMarca;
	}

	public void setNomeMarca(String nomeMarca) {
		this.nomeMarca = nomeMarca;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public Integer getAnoInicial() {
		return anoInicial;
	}

	public void setAnoInicial(Integer anoInicial) {
		this.anoInicial = anoInicial;
	}

	public Integer getAnoFinal() {
		return anoFinal;
	}

	public void setAnoFinal(Integer anoFinal) {
		this.anoFinal = anoFinal;
	}
}
