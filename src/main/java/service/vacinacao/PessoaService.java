package service.vacinacao;

import exception.VacinacaoException;
import model.entity.vacinacao.Pessoa;
import model.repository.vacinacao.PessoaRepository;

public class PessoaService {

	private PessoaRepository repository = new PessoaRepository();
	
	public Pessoa salvar(Pessoa novaPessoa) throws VacinacaoException {
		validarCamposObrigatorios(novaPessoa);
		
		validarCpf(novaPessoa);
		
		return repository.salvar(novaPessoa);
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
		
		if(!mensagemValidacao.isEmpty()) {
			throw new VacinacaoException("Preencha o(s) seguinte(s) campo(s) \n " + mensagemValidacao);
		}
		
	}
}
