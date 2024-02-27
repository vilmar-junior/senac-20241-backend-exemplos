package service.senhorfinancas;

import java.util.List;

import model.entity.senhorfinancas.UsuarioVO;
import model.repository.senhorfinancas.UsuarioDAO;

public class UsuarioBO {

	public UsuarioVO realizarLoginBO(UsuarioVO usuarioVO) {
		UsuarioDAO usuarioDAO = new UsuarioDAO();
		return usuarioDAO.realizarLoginDAO(usuarioVO);
	}

	public UsuarioVO cadastrarUsuarioBO(UsuarioVO usuarioVO) {
		UsuarioDAO usuarioDAO = new UsuarioDAO();
		if(usuarioDAO.verificarExistenciaRegistroPorCpfDAO(usuarioVO)) {
			System.out.println("\nUsuário já cadastrado.");
		} else {
			usuarioVO = usuarioDAO.cadastrarUsuarioDAO(usuarioVO);
		}
		return usuarioVO;
	}

	public List<UsuarioVO> consultarTodosUsuariosBO() {
		UsuarioDAO usuarioDAO = new UsuarioDAO();
		List<UsuarioVO> listaUsuariosVO = usuarioDAO.consultarTodosUsuariosDAO();
		if(listaUsuariosVO.isEmpty()) {
			System.out.println("\nLista de Usuários está vazia.");
		}
		return listaUsuariosVO;
	}

	public UsuarioVO consultarUsuarioBO(int idUsuario) {
		UsuarioDAO usuarioDAO = new UsuarioDAO();
		UsuarioVO usuario = usuarioDAO.consultarUsuarioDAO(idUsuario);
		if(usuario == null) {
			System.out.println("\nUsuario não localizado.");
		}
		return usuario;
	}

	public boolean atualizarUsuarioBO(UsuarioVO usuarioVO) {
		boolean resultado = false;
		UsuarioDAO usuarioDAO = new UsuarioDAO();
		if(usuarioDAO.verificarExistenciaRegistroPorIdUsuarioDAO(usuarioVO.getIdUsuario())) {
			resultado = usuarioDAO.atualizarUsuarioDAO(usuarioVO);
		} else {
			System.out.println("\nUsuário ainda não foi cadastrado.");
		}
		return resultado;
	}

	public boolean excluirUsuarioBO(UsuarioVO usuarioVO) {
		boolean resultado = false;
		UsuarioDAO usuarioDAO = new UsuarioDAO();
		if(usuarioDAO.verificarExistenciaRegistroPorIdUsuarioDAO(usuarioVO.getIdUsuario())) {
			resultado = usuarioDAO.excluirUsuarioDAO(usuarioVO);
		} else {
			System.out.println("\nUsuário não existe na base de dados.");
		}
		return resultado;
	}

}
