package service.vemnox1;

import exception.VemNoX1Exception;
import model.entity.vemnox1.Carta;
import model.repository.CartaRepository;

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
}
