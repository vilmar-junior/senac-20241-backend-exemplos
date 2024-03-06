package controller.vemnox1;

import java.util.List;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import model.dto.vemnox1.JogadaDTO;
import model.dto.vemnox1.PartidaDTO;
import model.entity.vemnox1.Partida;

@Path("/partida")
public class PartidaController {
	
	@Path("/iniciar")
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public PartidaDTO iniciarPartida(@PathParam("id") int id){
		//TODO chamar o PartidaService
		return null;
	}
	
	@Path("/jogar")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public PartidaDTO jogar(JogadaDTO jogada){
		//TODO chamar o PartidaService
		return null;
	}
	
	@Path("/todas/{id}")
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<Partida> consultarPartidasDoJogador(@PathParam("id") int id){
		//TODO chamar o PartidaService
		return null;
	}
}