package service.vemnox1;

import java.util.ArrayList;
import java.util.List;

import exception.VemNoX1Exception;
import model.entity.vemnox1.Carta;
import model.repository.vemnox1.CartaRepository;

public class CartaService {

	private static final int SOMATORIO_MAXIMO_ATRIBUTOS_PERMITIDO = 10;
	private static final int MAXIMO_CARTA = 5;
	private static final int MINIMO_CARTA = 1;
	
	private CartaRepository repository = new CartaRepository();
	
	public Carta salvar(Carta novaCarta) throws VemNoX1Exception {
		validarCarta(novaCarta);
		
		return repository.salvar(novaCarta);
	}

	public boolean atualizar(Carta cartaEditada) throws VemNoX1Exception {
		validarCarta(cartaEditada);
		
		return repository.alterar(cartaEditada);
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
	
	private void validarCarta(Carta carta) throws VemNoX1Exception {
		validarAtributoDaCarta(carta.getForca(), "Força");
		validarAtributoDaCarta(carta.getInteligencia(), "Inteligência");
		validarAtributoDaCarta(carta.getVelocidade(), "Velocidade");
		
		int totalPontosAtributos = carta.getForca() 
				+ carta.getInteligencia()
				+ carta.getVelocidade();
		
		if(totalPontosAtributos > SOMATORIO_MAXIMO_ATRIBUTOS_PERMITIDO) {
			throw new VemNoX1Exception("Excedeu o total de " 
									   + SOMATORIO_MAXIMO_ATRIBUTOS_PERMITIDO + " atributos");
		}
	}

	private void validarAtributoDaCarta(int valorAtributo, String nomeAtributo) throws VemNoX1Exception {
		if(valorAtributo < MINIMO_CARTA || valorAtributo > MAXIMO_CARTA) {
			throw new VemNoX1Exception("Valor do atributo " + nomeAtributo + " deve situar-se entre " 
									   + MINIMO_CARTA + " e " + MAXIMO_CARTA);
		}
	}
}
