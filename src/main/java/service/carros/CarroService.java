package service.carros;

import java.util.ArrayList;
import java.util.List;

import exception.CarrosException;
import model.entity.carros.Carro;
import model.entity.carros.Montadora;
import model.seletor.carros.CarroSeletor;

public class CarroService {
	
	private MontadoraService montadoraService = new MontadoraService();

	public ArrayList<Carro> consultarComFiltros(CarroSeletor seletor) {
		ArrayList<Carro> carros = new ArrayList<>();
		
		//TODO obter os carros cadastrados no banco
		
		return carros;
	}

	//Método depreciado, não deve ser utilizado
	@Deprecated()
	private ArrayList<Carro> gerarMockCarros() {
		ArrayList<Montadora> montadoras = montadoraService.gerarMockMontadoras();
		
		ArrayList<Carro> carros = new ArrayList<>();
		carros.add(new Carro(1, "Corolla", "TOY-0001", montadoras.get(0), 2024, 150000.0));
		carros.add(new Carro(1, "Bandeirante", "TOY-0002", montadoras.get(0), 1984, 21000.0));
		carros.add(new Carro(1, "Focus", "FOR-0001", montadoras.get(1), 2020, 80000.0));
		carros.add(new Carro(1, "Golf", "VWB-0001", montadoras.get(2), 2024, 180000.0));
		carros.add(new Carro(1, "Fusca", "VWB-0002", montadoras.get(2), 1970, 5000.0));
		carros.add(new Carro(1, "Kombi", "VWB-0003", montadoras.get(2), 1980, 3500.0));
		
		return carros;
	}

	public int consultarEstoqueCarros(int idMontadora) throws CarrosException {
		ArrayList<Carro> carros = gerarMockCarros();
		ArrayList<Montadora> montadoras = montadoraService.gerarMockMontadoras();
		
		boolean montadoraCadastrada = montadoras.stream().anyMatch(montadora -> montadora.getId().equals(idMontadora));
		
		if(!montadoraCadastrada) {
			throw new CarrosException("Montadora não encontrada. Id informado: " + idMontadora); 
		}
		
		List<Carro> carrosDaMontadoraSelecionada = carros.stream().filter(c -> c.getMontadora().getId().equals(idMontadora)).toList();

		return carrosDaMontadoraSelecionada.size();
	}
}
