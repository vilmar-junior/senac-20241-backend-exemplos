package controller.carros;

import exception.CarrosException;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import service.carros.CarroService;

@Path("/montadora")
public class MontadoraController {
	
	private CarroService service = new CarroService();
	
	@GET
	@Path("/estoque-carros/{idMontadora}")
	public int consultarEstoqueCarros(@PathParam("idMontadora") int idMontadora) throws CarrosException {
		return service.consultarEstoqueCarros(idMontadora);
	}
}