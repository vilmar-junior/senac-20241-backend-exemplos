package model.bo;

import java.util.List;

import model.dao.RelatorioDAO;
import model.dto.ItemRelatorioDTO;

public class RelatorioBO {
	
	public List<ItemRelatorioDTO> gerarRelatorioReceitasDespesasPorUsuarioBO(int idUsuario, int ano){
		RelatorioDAO relatorioDAO = new RelatorioDAO();
		List<ItemRelatorioDTO> lista = relatorioDAO.gerarRelatorioReceitasDespesasPorUsuarioDAO(idUsuario, ano);
		if(lista.isEmpty()) {
			System.out.println("Nenhuma receita e despesa lançadas para esse usuário!");
		}
		return lista;
	}

}
