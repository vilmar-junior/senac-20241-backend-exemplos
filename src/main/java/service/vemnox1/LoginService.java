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
		
		if(jogadorAutenticado == null) {
			throw new VemNoX1Exception("Login ou senha inválidos, tente novamente");
		}

		String idSessao = UUID.randomUUID().toString();
		jogadorAutenticado.setIdSessao(idSessao);
		repository.alterarIdSessao(jogadorAutenticado);
		
		return jogadorAutenticado;
	}

	public boolean chaveValida(String idSessao) {
		Jogador jogador = this.repository.consultarPorIdSessao(idSessao);

		return jogador != null 
				&& jogador.getIdSessao() != null
				&& jogador.getIdSessao().equals(idSessao);
	}
}
