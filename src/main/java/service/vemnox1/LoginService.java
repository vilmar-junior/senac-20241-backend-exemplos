package service.vemnox1;

import java.util.UUID;

import exception.VemNoX1Exception;
import model.dto.vemnox1.UsuarioDTO;
import model.entity.vemnox1.Jogador;
import model.repository.vemnox1.JogadorRepository;

public class LoginService {

	private JogadorRepository repository = new JogadorRepository();
	
	public Jogador autenticar(UsuarioDTO usuarioDTO) throws VemNoX1Exception {
		
		if(usuarioDTO == null || 
			(usuarioDTO.getLogin() == null || usuarioDTO.getLogin().trim().isEmpty())) {
			throw new VemNoX1Exception("Usuário não informado");
		}
		
		if(usuarioDTO.getSenha() == null || usuarioDTO.getSenha().trim().isEmpty()) {
			throw new VemNoX1Exception("Senha não informada");
		}
		
		Jogador jogadorAutenticado = repository.consultarPorLoginSenha(usuarioDTO);
		
		//Inclui o uuid de sessão --> TODO revisar (criar por session ou salvar no em coluna no banco?)
		String idSessao = UUID.randomUUID().toString();
		jogadorAutenticado.setIdSessao(idSessao);
		repository.alterarIdSessao(jogadorAutenticado);
		
		return jogadorAutenticado;
	}

	public boolean chaveValida(String idSessao, String login) {
		Jogador jogador = this.repository.consultarPorLogin(login);

		return jogador != null 
				&& jogador.getIdSessao() != null
				&& jogador.getIdSessao().equals(idSessao);
	}
}
