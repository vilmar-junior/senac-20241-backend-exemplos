package model.repository.vacinacao;

import java.util.ArrayList;

import model.entity.vacinacao.Vacina;
import model.repository.BaseRepository;

public class VacinaRepository implements BaseRepository<Vacina> {

	@Override
	public Vacina salvar(Vacina novaEntidade) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean excluir(int id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean alterar(Vacina entidade) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Vacina consultarPorId(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Vacina> consultarTodos() {
		// TODO Auto-generated method stub
		return null;
	}


}
