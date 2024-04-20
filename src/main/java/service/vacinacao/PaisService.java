package service.vacinacao;

import java.util.ArrayList;

import exception.VacinacaoException;
import model.entity.vacinacao.Pais;
import model.repository.vacinacao.PaisRepository;

public class PaisService {

	private PaisRepository repository = new PaisRepository();
	
	public Pais salvar(Pais novo) throws VacinacaoException {
		return repository.salvar(novo);
	}
	
	public Pais consultarPorId(int id) {
		return repository.consultarPorId(id);
	}
	
	public ArrayList<Pais> consultarTodos() {
		return repository.consultarTodos();
	}
}
