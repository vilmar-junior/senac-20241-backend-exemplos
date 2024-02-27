package service.senhorfinancas;

import java.util.List;

import model.dto.senhorfinancas.ItemRelatorioDTO;
import model.repository.senhorfinancas.RelatorioDAO;

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
