package controller.senhorfinancas;

import java.util.List;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import model.dto.senhorfinancas.ItemRelatorioDTO;
import service.senhorfinancas.RelatorioBO;

@Path("/relatorio")
public class RelatorioRest {

	@GET
	@Path("/receita-despesa/{idUsuario}/{ano}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<ItemRelatorioDTO> gerarRelatorioReceitasDespesasPorUsuarioController(@PathParam("idUsuario") int idUsuario, @PathParam("ano") int ano){
		RelatorioBO relatorioBO = new RelatorioBO();
		return relatorioBO.gerarRelatorioReceitasDespesasPorUsuarioBO(idUsuario, ano);
	}
}
