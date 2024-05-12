package service.carros;

import java.util.ArrayList;

import model.entity.carros.Montadora;

public class MontadoraService {

	public ArrayList<Montadora> consultarTodas() {
		return this.gerarMockMontadoras();
	}
	
	public ArrayList<Montadora> gerarMockMontadoras() {
		ArrayList<Montadora> montadoras = new ArrayList<>();
		montadoras.add(new Montadora(1, "Toyota", "Japão"));
		montadoras.add(new Montadora(2, "Ford", "Estados Unidos"));
		montadoras.add(new Montadora(3, "Volkswagen", "Alemanha"));
		montadoras.add(new Montadora(4, "GM", "Estados Unidos"));
		montadoras.add(new Montadora(5, "Honda", "Japão"));
		montadoras.add(new Montadora(6, "Nissan", "Japão"));

		return montadoras;
	}
}
