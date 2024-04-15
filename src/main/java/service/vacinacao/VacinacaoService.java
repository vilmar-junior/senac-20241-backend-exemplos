package service.vacinacao;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import exception.VacinacaoException;
import jakarta.validation.ValidationException;
import model.entity.vacinacao.Pessoa;
import model.entity.vacinacao.Vacina;
import model.entity.vacinacao.Vacinacao;
import model.repository.vacinacao.PessoaRepository;
import model.repository.vacinacao.VacinaRepository;
import model.repository.vacinacao.VacinacaoRepository;

public class VacinacaoService {

	private static final int NOTA_MAXIMA = 5;
	private VacinacaoRepository vacinacaoRepository = new VacinacaoRepository();
	
	public Vacinacao salvar(Vacinacao novaVacinacao) throws VacinacaoException {
		validarDadosVacinacao(novaVacinacao);
		atualizarMediaVacina(novaVacinacao);
		validarAcessoPessoaParaReceberVacina(novaVacinacao);
		
		return vacinacaoRepository.salvar(novaVacinacao);
	}
	
	public boolean atualizar(Vacinacao vacinacaoEditada) throws VacinacaoException {
		validarDadosVacinacao(vacinacaoEditada);
		atualizarMediaVacina(vacinacaoEditada);
		validarAcessoPessoaParaReceberVacina(vacinacaoEditada);
		
		return vacinacaoRepository.alterar(vacinacaoEditada);
	}
	
	private void validarAcessoPessoaParaReceberVacina(Vacinacao vacinacao) throws VacinacaoException {
		Vacina vac = vacinacao.getVacina();
		Pessoa paciente = new PessoaRepository().consultarPorId(vacinacao.getIdPessoa());
		
		boolean podeReceberDose = false;
		if(vac.getEstagio() == Vacina.ESTAGIO_INICIAL && paciente.getTipo() == Pessoa.PESQUISADOR) {
			podeReceberDose = true;
		}
		
		if(vac.getEstagio() == Vacina.ESTAGIO_TESTES && 
				(paciente.getTipo() == Pessoa.PESQUISADOR || paciente.getTipo() == Pessoa.VOLUNTARIO)) {
			podeReceberDose = true;
		}
		
		if(vac.getEstagio() == Vacina.ESTAGIO_APLICACAO_MASSA) {
			podeReceberDose = true;
		}
		
		if(!podeReceberDose) {
			throw new VacinacaoException("Usuário sem permissão para receber a vacina");
		}
	}
	
	private void atualizarMediaVacina(Vacinacao novaVacinacao) {
		ArrayList<Vacinacao> dosesAplicadas = vacinacaoRepository.consultarPorIdVacina(novaVacinacao.getVacina().getId());
		
		double novaMediaVacina = 0;
		double somatorioNotasDaVacina = 0;
		
		for(Vacinacao dose: dosesAplicadas) {
			somatorioNotasDaVacina += dose.getAvaliacao();
		}
		
		novaMediaVacina = (somatorioNotasDaVacina + novaVacinacao.getAvaliacao()) / (dosesAplicadas.size() + 1);
		
		VacinaRepository vacinaRepository = new VacinaRepository();
		Vacina vacinaAplicada = vacinaRepository.consultarPorId(novaVacinacao.getVacina().getId());
		vacinaAplicada.setMedia(novaMediaVacina);
		
		vacinaRepository.alterar(vacinaAplicada);
		
		novaVacinacao.setVacina(vacinaAplicada);
	}
	
	private void validarDadosVacinacao(Vacinacao novaVacinacao) throws VacinacaoException {
		if(novaVacinacao.getIdPessoa() == 0 
				|| novaVacinacao.getVacina() == null
				|| novaVacinacao.getVacina().getId() == 0) {
			throw new VacinacaoException("Informe a o id da pessoa e a vacina da aplicação");
		}
		
		novaVacinacao.setDataAplicacao(LocalDate.now());
		
		if(novaVacinacao.getAvaliacao() == 0) {
			novaVacinacao.setAvaliacao(NOTA_MAXIMA);
		}
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
