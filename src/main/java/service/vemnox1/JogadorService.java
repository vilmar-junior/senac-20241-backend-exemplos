package service.vemnox1;

import java.util.List;

import model.entity.vemnox1.Jogador;
import model.repository.vemnox1.JogadorRepository;

public class JogadorService {

	private JogadorRepository repository = new JogadorRepository();
	
	public Jogador salvar(Jogador novaJogador){
		return repository.salvar(novaJogador);
	}

	public boolean atualizar(Jogador jogadorEditado) {
		return repository.alterar(jogadorEditado);
	}

	public boolean excluir(int id) {
		//TODO pode excluir jogador que já possui partidas?
		return repository.excluir(id);
	}

	public Jogador consultarPorId(int id) {
		return repository.consultarPorId(id);
	}

	public List<Jogador> consultarTodas() {
		return repository.consultarTodos();
	}
}
