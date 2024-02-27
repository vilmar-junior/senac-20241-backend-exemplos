package service.senhorfinancas;

import java.util.List;

import model.entity.senhorfinancas.ReceitaVO;
import model.repository.senhorfinancas.ReceitaDAO;

public class ReceitaBO {
	
	public ReceitaVO cadastrarReceitaBO(ReceitaVO receitaVO) {
		ReceitaDAO receitaDAO = new ReceitaDAO();
		return receitaDAO.cadastrarReceitaDAO(receitaVO);
	}
	
	public List<ReceitaVO> consultarTodasReceitasBO(int idUsuario){
		ReceitaDAO receitaDAO = new ReceitaDAO();
		return receitaDAO.consultarTodasReceitasDAO(idUsuario);
	}
	
	public ReceitaVO consultarReceitaBO(int idReceita) {
		ReceitaDAO receitaDAO = new ReceitaDAO();
		return receitaDAO.consultarReceitaDAO(idReceita);
	}
	
	public boolean atualizarReceitaBO(ReceitaVO receitaVO) {
		ReceitaDAO receitaDAO = new ReceitaDAO();
		return receitaDAO.atualizarReceitaDAO(receitaVO);
	}
	
	public boolean excluirReceitaBO(ReceitaVO receitaVO) {
		ReceitaDAO receitaDAO = new ReceitaDAO();
		return receitaDAO.excluirReceitaDAO(receitaVO);
	}

}
