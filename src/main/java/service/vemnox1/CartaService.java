package service.vemnox1;

import java.util.ArrayList;
import java.util.List;

import exception.VemNoX1Exception;
import model.entity.vemnox1.Carta;
import model.repository.vemnox1.CartaRepository;

public class CartaService {

	private static final int MAXIMO_ATRIBUTOS_PERMITIDO = 10;
	private CartaRepository repository = new CartaRepository();
	
	public Carta salvar(Carta novaCarta) throws VemNoX1Exception {
		
		int totalPontosAtributos = novaCarta.getForca() 
							+ novaCarta.getInteligencia()
							+ novaCarta.getVelocidade();
		
		if(totalPontosAtributos > MAXIMO_ATRIBUTOS_PERMITIDO) {
			throw new VemNoX1Exception("Excedeu o total de " 
									   + MAXIMO_ATRIBUTOS_PERMITIDO + " atributos");
		}
		
		return repository.salvar(novaCarta);
	}

	public boolean atualizar(Carta cartaEditada) {
		// TODO vamos fazer em sala....
		return false;
	}

	public boolean excluir(int id) {
		//TODO pode excluir carta já usada em partidas?
		return repository.excluir(id);
	}

	public Carta consultarPorId(int id) {
		return repository.consultarPorId(id);
	}

	public List<Carta> consultarTodas() {
		return repository.consultarTodos();
	}

	public ArrayList<Carta> sortearSeisCartas() {
		return repository.sortearSeisCartas();
	}
}
