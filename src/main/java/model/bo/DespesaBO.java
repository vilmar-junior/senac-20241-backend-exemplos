package model.bo;

import java.util.List;

import model.dao.DespesaDAO;
import model.vo.DespesaVO;

public class DespesaBO {
	
	public DespesaVO cadastrarDespesaBO(DespesaVO despesaVO) {
		DespesaDAO despesaDAO = new DespesaDAO();
		return despesaDAO.cadastrarDespesaDAO(despesaVO);
	}
	
	public List<DespesaVO> consultarTodasDespesasBO(int idUsuario){
		DespesaDAO despesaDAO = new DespesaDAO();
		return despesaDAO.consultarTodasDespesasDAO(idUsuario);
	}
	
	public DespesaVO consultarDespesaBO(int idDespesa) {
		DespesaDAO despesaDAO = new DespesaDAO();
		return despesaDAO.consultarDespesaDAO(idDespesa);
	}
	
	public boolean atualizarDespesaBO(DespesaVO despesaVO) {
		DespesaDAO despesaDAO = new DespesaDAO();
		return despesaDAO.atualizarDespesaDAO(despesaVO);
	}
	
	public boolean excluirDespesaBO(DespesaVO despesaVO) {
		DespesaDAO despesaDAO = new DespesaDAO();
		return despesaDAO.excluirDespesaDAO(despesaVO);
	}

}
