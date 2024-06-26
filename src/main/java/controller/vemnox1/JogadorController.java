package controller.vemnox1;

import java.util.List;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import model.entity.vemnox1.Jogador;
import service.vemnox1.JogadorService;

@Path("/restrito/jogador")
public class JogadorController {
	
	private JogadorService service = new JogadorService();
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Jogador salvar(Jogador novoJogador){
		novoJogador.setTotalPartidas(0);
		novoJogador.setPercentualVitorias(0);
		return service.salvar(novoJogador);
	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public boolean atualizar(Jogador jogadorEditado){
		 return service.atualizar(jogadorEditado);
	}
	
	@DELETE
	@Path("/{id}")
	public boolean excluir(@PathParam("id") int id){
		 return service.excluir(id);
	}
	
	@GET
	@Path("/{id}")
	public Jogador consultarPorId(@PathParam("id") int id){
		 return service.consultarPorId(id);
	}
	
	@GET
	@Path("/todos")
	public List<Jogador> consultarTodas(){
		 return service.consultarTodas();
	}
}