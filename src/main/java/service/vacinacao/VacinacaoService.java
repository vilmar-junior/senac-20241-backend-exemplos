package service.vacinacao;

import java.time.LocalDate;
import java.util.List;

import exception.VacinacaoException;
import model.entity.vacinacao.Vacinacao;
import model.repository.vacinacao.VacinacaoRepository;

public class VacinacaoService {

	private static final int NOTA_MAXIMA = 5;
	private VacinacaoRepository vacinacaoRepository = new VacinacaoRepository();
	
	public Vacinacao salvar(Vacinacao novaVacinacao) throws VacinacaoException {

		if(novaVacinacao.getIdPessoa() == 0 
				|| novaVacinacao.getVacina() == null
				|| novaVacinacao.getVacina().getId() == 0) {
			throw new VacinacaoException("Informe a o id da pessoa e a vacina da aplicação");
		}
		
		novaVacinacao.setDataAplicacao(LocalDate.now());
		
		if(novaVacinacao.getAvaliacao() == 0) {
			novaVacinacao.setAvaliacao(NOTA_MAXIMA);
		}
		
		return vacinacaoRepository.salvar(novaVacinacao);
	}
	
	public boolean atualizar(Vacinacao vacinacaoEditada) throws VacinacaoException {
		return vacinacaoRepository.alterar(vacinacaoEditada);
	}

	public boolean excluir(int id) {
		return vacinacaoRepository.excluir(id);
	}

	public Vacinacao consultarPorId(int id) {
		return vacinacaoRepository.consultarPorId(id);
	}

	public List<Vacinacao> consultarTodas() {
		return vacinacaoRepository.consultarTodos();
	}
	
	public List<Vacinacao> consultarPorIdPessoa(int idPessoa) {
		return vacinacaoRepository.consultarPorIdPessoa(idPessoa);
	}
}
