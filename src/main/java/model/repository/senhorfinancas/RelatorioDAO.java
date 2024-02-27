package model.repository.senhorfinancas;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import model.repository.Banco;

import model.dto.senhorfinancas.ItemRelatorioDTO;

public class RelatorioDAO {
	
	public ArrayList<ItemRelatorioDTO> gerarRelatorioReceitasDespesasPorUsuarioDAO(int idUsuario, int ano){
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		
		ResultSet resultado = null;
		HashMap<Integer, ItemRelatorioDTO> mapa = new HashMap<>();
		
		String query = "select relatorio.mes, sum(receita), sum(despesa) "
				+ "from ("
				+ "	SELECT month(r.datareceita) as mes, "
				+ "	sum(r.valor) as receita, 0 as despesa "
				+ "	FROM receita r, usuario u "
				+ "	WHERE r.idusuario = u.idusuario "
				+ "	AND u.idusuario = " + idUsuario
				+ "	AND year(r.datareceita) = " + ano
				+ "	GROUP BY month(r.datareceita) "
				+ "	union "
				+ "	SELECT month(d.datavencimento) as mes, "
				+ "	0 as receita, sum(d.valor) as despesa "
				+ "	FROM despesa d, usuario u "
				+ "	WHERE d.idusuario = u.idusuario "
				+ "	AND u.idusuario = " + idUsuario
				+ "	AND year(d.datavencimento) = " + ano
				+ "	GROUP BY month(d.datavencimento) "
				+ ") relatorio "
				+ " group by mes";
		
		try {
			resultado = stmt.executeQuery(query);
			while(resultado.next()) {
				ItemRelatorioDTO item = new ItemRelatorioDTO();
				item.setMes(resultado.getInt(1));
				item.setValorReceita(resultado.getDouble(2));
				item.setValorDespesa(resultado.getDouble(3));
				mapa.put(item.getMes(), item);
			}
		} catch(SQLException erro) {
			System.out.println("Erro ao executar a query do método gerarRelatorioReceitasDespesasPorUsuarioDAO");
			System.out.println("Erro: " + erro.getMessage());
		}finally {
			Banco.closeResultSet(resultado);
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}
		for(int i = 1; i < 13; i++) {
			if(mapa.get(i) == null) {
				ItemRelatorioDTO item = new ItemRelatorioDTO();
				item.setMes(i);
				mapa.put(item.getMes(), item);
			}
		}
		return new ArrayList<ItemRelatorioDTO>(mapa.values());
	}

}
