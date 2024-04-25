package service.vacinacao;

import java.util.ArrayList;
import java.util.List;

import exception.VacinacaoException;
import jakarta.validation.ValidationException;
import model.entity.vacinacao.Pessoa;
import model.entity.vacinacao.Vacinacao;
import model.repository.vacinacao.PessoaRepository;
import model.repository.vacinacao.VacinaRepository;
import model.repository.vacinacao.VacinacaoRepository;

public class PessoaService {

	private PessoaRepository repository = new PessoaRepository();
	
	public Pessoa salvar(Pessoa novaPessoa) throws VacinacaoException {
		validarCamposObrigatorios(novaPessoa);
		
		validarCpf(novaPessoa);
		
		return repository.salvar(novaPessoa);
	}
	
	public boolean atualizar(Pessoa pessoaEditada) throws VacinacaoException {
		validarCamposObrigatorios(pessoaEditada);
		
		//TODO porque não valido o CPF? Veremos em sala
		
		return repository.alterar(pessoaEditada);
	}

	public boolean excluir(int id) throws VacinacaoException   {
		
		VacinacaoRepository vacinacaoRepository = new VacinacaoRepository();
		ArrayList<Vacinacao> aplicacoesNaPessoa = vacinacaoRepository.consultarPorIdPessoa(id);
		
		if(aplicacoesNaPessoa.size() > 0) {
			throw new VacinacaoException("Pessoa não pode ser excluída, pois já foi vacinada");
		}
		
		return repository.excluir(id);
	}

	public Pessoa consultarPorId(int id) {
		return repository.consultarPorId(id);
	}

	public List<Pessoa> consultarTodas() {
		return repository.consultarTodos();
	}
	
	private void validarCpf(Pessoa novaPessoa) throws VacinacaoException {
		if(repository.cpfJaCadastrado(novaPessoa.getCpf())) {
			throw new VacinacaoException("CPF " + novaPessoa.getCpf() + " já cadastrado "); 
		}
	}

	private void validarCamposObrigatorios(Pessoa p) throws VacinacaoException{
		String mensagemValidacao = "";
		if(p.getNome() == null || p.getNome().isEmpty()) {
			mensagemValidacao += " - informe o nome \n";
		}
		if(p.getDataNascimento() == null) {
			mensagemValidacao += " - informe a data de nascimento \n";
		}
		if(p.getCpf() == null || p.getCpf().isEmpty() || p.getCpf().length() != 11) {
			mensagemValidacao += " - informe o CPF";
		}
		if(p.getSexo() == ' ') {
			mensagemValidacao += " - informe o sexo";
		}
		if(p.getTipo() < 1 || p.getTipo() > 3) {
			mensagemValidacao += " - informe o tipo (entre 1 e 3)";
		}
		if(p.getPaisOrigem() == null) {
			mensagemValidacao += " - informe o país de origem";
		}
		
		if(!mensagemValidacao.isEmpty()) {
			throw new VacinacaoException("Preencha o(s) seguinte(s) campo(s) \n " + mensagemValidacao);
		}
	}

	public List<Pessoa> consultarPesquisadores() {
		return this.repository.consultarPesquisadores();
	}
}
