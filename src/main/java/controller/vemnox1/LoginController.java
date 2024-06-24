package controller.vemnox1;

import exception.VemNoX1Exception;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import model.dto.vemnox1.UsuarioDTO;
import model.entity.vemnox1.Jogador;
import service.vemnox1.LoginService;

@Path("/login")
public class LoginController {
	
	private LoginService loginService = new LoginService();
	
	@POST
	@Path("/autenticar")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Jogador autenticar(UsuarioDTO usuarioTentandoAutenticar) throws VemNoX1Exception {
		return loginService.autenticar(usuarioTentandoAutenticar);
	}
}