package controller.vemnox1;

import java.util.List;

import exception.VemNoX1Exception;
import filter.AuthFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import model.dto.vemnox1.JogadaDTO;
import model.dto.vemnox1.PartidaDTO;
import model.entity.enums.vemnox1.PerfilAcesso;
import model.entity.vemnox1.Jogador;
import model.entity.vemnox1.Partida;
import service.vemnox1.JogadorService;
import service.vemnox1.PartidaService;

@Path("/restrito/partida")
public class PartidaController {
	
	@Context
	private HttpServletRequest request;

	private PartidaService service = new PartidaService();
	private JogadorService jogadorService = new JogadorService();
	
	@Path("/iniciar/{idJogador}")
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public PartidaDTO iniciarPartida(@PathParam("idJogador") int idJogador) throws VemNoX1Exception{
		return service.iniciarPartida(idJogador);
	}
	
	@Path("/jogar")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public PartidaDTO jogar(JogadaDTO jogada) throws VemNoX1Exception{
		return service.jogar(jogada);
	}
	
	/**
	 * Lista todas as partidas de um jogador, dado o seu id.
	 * @param id do jogador
	 * @return lista de partidas do jogador
	 * @throws VemNoX1Exception o usuário autenticado só tem 
	 *                          acesso caso ADMINISTRADOR ou se for o dono das partidas
	 */
	@Path("/por-jogador/{id}")
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<Partida> consultarPartidasDoJogador(@PathParam("id") int id) throws VemNoX1Exception{
		
		String idSessaoNoHeader = request.getHeader(AuthFilter.CHAVE_ID_SESSAO);
		
		if(idSessaoNoHeader == null || idSessaoNoHeader.isEmpty()) {
			throw new VemNoX1Exception("Usuário sem permissão (idSessao não informado)");
		}
		
		Jogador jogadorAutenticado = this.jogadorService.consultarPorIdSessao(idSessaoNoHeader);
		
		if(jogadorAutenticado == null) {
			throw new VemNoX1Exception("Usuário não encontrado");
		}
		
		if(jogadorAutenticado.getPerfil() == PerfilAcesso.JOGADOR
				&& jogadorAutenticado.getId() != id) {
			throw new VemNoX1Exception("Usuário sem permissão de acesso");
		}
		
		return this.service.consultarPartidasDoJogador(id);
	}
}