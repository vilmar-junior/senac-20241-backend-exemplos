package controller.vacinacao;

import java.util.List;

import exception.VacinacaoException;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import model.entity.vacinacao.Vacina;
import model.seletor.VacinaSeletor;
import service.vacinacao.VacinaService;

@Path("/vacina")
public class VacinaController {
	
	private VacinaService service = new VacinaService();
	
	@POST
	@Path("/filtro")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<Vacina> consultarComFiltros(VacinaSeletor seletor){
		 return service.consultarComFiltros(seletor);
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Vacina salvar(Vacina novaVacina){
		 return service.salvar(novaVacina);
	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public boolean atualizar(Vacina vacinaEditada){
		 return service.atualizar(vacinaEditada);
	}
	
	@DELETE
	@Path("/{id}")
	public boolean excluir(@PathParam("id") int id) throws VacinacaoException{
		 return service.excluir(id);
	}
	
	@GET
	@Path("/{id}")
	public Vacina consultarPorId(@PathParam("id") int id){
		 return service.consultarPorId(id);
	}
	
	@GET
	@Path("/todas")
	public List<Vacina> consultarTodas(){
		 return service.consultarTodas();
	}
	
	@POST
	@Path("/contar")
	@Consumes(MediaType.APPLICATION_JSON)
	public int contarTotalRegistros(VacinaSeletor seletor) {
		return this.service.contarTotalRegistros(seletor);
	}
	
	
	@POST
	@Path("/total-paginas")
	@Consumes(MediaType.APPLICATION_JSON)
	public int contarPaginas(VacinaSeletor seletor) {
		return this.service.contarPaginas(seletor);
	}
}