package service.vacinacao;

import java.util.ArrayList;
import java.util.List;

import exception.VacinacaoException;
import model.entity.vacinacao.Vacina;
import model.entity.vacinacao.Vacinacao;
import model.repository.vacinacao.VacinaRepository;
import model.repository.vacinacao.VacinacaoRepository;
import model.seletor.VacinaSeletor;

public class VacinaService {

	private VacinaRepository repository = new VacinaRepository();
	
	public Vacina salvar(Vacina novaVacina){
		return repository.salvar(novaVacina);
	}

	public boolean atualizar(Vacina vacinaEditada) {
		return repository.alterar(vacinaEditada);
	}

	public boolean excluir(int id) throws VacinacaoException {
		VacinacaoRepository vacinacaoRepository = new VacinacaoRepository();
		ArrayList<Vacinacao> aplicacoesDaVacina = vacinacaoRepository.consultarPorIdVacina(id);
		
		if(aplicacoesDaVacina.size() > 0) {
			throw new VacinacaoException("Vacina não pode ser excluída, pois já foi aplicada");
		}
		
		return repository.excluir(id);
	}

	public Vacina consultarPorId(int id) {
		return repository.consultarPorId(id);
	}

	public List<Vacina> consultarTodas() {
		return repository.consultarTodos();
	}
	
	public List<Vacina> consultarComFiltros(VacinaSeletor seletor) {
		return repository.consultarComFiltros(seletor);
	}
	
	public int contarPaginas(VacinaSeletor seletor) {
		return this.repository.contarPaginas(seletor);
	}
	
	public int contarTotalRegistros(VacinaSeletor seletor) {
		return this.repository.contarTotalRegistros(seletor);
	}
}
