package controller.senhorfinancas;

import java.util.ArrayList;

import exception.VemNoX1Exception;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import model.entity.vemnox1.Carta;
import model.repository.CartaRepository;
import service.vemnox1.CartaService;

@Path("/carta")
public class CartaController {
	
	//Violando o modelo MVC (apenas para teste inicial)
	private CartaRepository repository = new CartaRepository();
	
	private CartaService service = new CartaService();
	
	@POST
	@Path("/salvar")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Carta salvar(Carta novaCarta) throws VemNoX1Exception {
		 return service.salvar(novaCarta);
	}
	
	@GET
	@Path("/sortear")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Carta> sortear(){
		return repository.sortearSeisCartas();
	}
	
	
}