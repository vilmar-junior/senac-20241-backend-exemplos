package model.repository.senhorfinancas;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import model.entity.senhorfinancas.DespesaVO;
import model.repository.Banco;

public class DespesaDAO {

	public DespesaVO cadastrarDespesaDAO(DespesaVO despesaVO) {
		String query = "INSERT INTO despesa (idusuario, descricao, valor, datavencimento, datapagamento) VALUES (?, ?, ?, ?, ?)";
		Connection conn = Banco.getConnection();
		PreparedStatement pstmt = Banco.getPreparedStatementWithPk(conn, query);
		try {
			pstmt.setInt(1, despesaVO.getIdUsuario());
			pstmt.setString(2, despesaVO.getDescricao());
			pstmt.setDouble(3, despesaVO.getValor());
			pstmt.setObject(4, despesaVO.getDataVencimento());
			pstmt.setObject(5, despesaVO.getDataPagamento());
			pstmt.execute();
			ResultSet resultado = pstmt.getGeneratedKeys();
			if(resultado.next()) {
				despesaVO.setIdDespesa(resultado.getInt(1));
			}
		} catch (SQLException erro) {
			System.out.println("Erro ao executar a query do método cadastrarDespesaDAO!");
			System.out.println("Erro: " + erro.getMessage());
		} finally {
			Banco.closeStatement(pstmt);
			Banco.closeConnection(conn);
		}
		return despesaVO;
	}

	public List<DespesaVO> consultarTodasDespesasDAO(int idUsuario) {
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		
		ResultSet resultado = null;
		ArrayList<DespesaVO> listaDespesas = new ArrayList<>();
		String query = "SELECT iddespesa, idusuario, descricao, valor, datavencimento, datapagamento "
				+ "FROM despesa WHERE idusuario = " + idUsuario;

		try{
			resultado = stmt.executeQuery(query);
			while(resultado.next()){
				DespesaVO despesa = new DespesaVO();
				despesa.setIdDespesa(resultado.getInt(1));
				despesa.setIdUsuario(resultado.getInt(2));
				despesa.setDescricao(resultado.getString(3));
				despesa.setValor(Double.parseDouble(resultado.getString(4)));
				despesa.setDataVencimento(LocalDateTime.parse(resultado.getString(5), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
				if(resultado.getString(6) != null) {
					despesa.setDataPagamento(LocalDateTime.parse(resultado.getString(6), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
				}
				listaDespesas.add(despesa);
			}
		} catch (SQLException erro){
			System.out.println("Erro ao executar a query do método consultarTodasDespesasDAO!");
			System.out.println("Erro: " + erro.getMessage());
		} finally {
			Banco.closeResultSet(resultado);
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}
		return listaDespesas;
	}

	public DespesaVO consultarDespesaDAO(int idDespesa) {
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		
		ResultSet resultado = null;
		DespesaVO despesa = new DespesaVO();
		String query = "SELECT iddespesa, idusuario, descricao, valor, datavencimento, datapagamento "
				+ "FROM despesa WHERE iddespesa = " + idDespesa;
		
		try{
			resultado = stmt.executeQuery(query);
			while(resultado.next()){
				despesa.setIdDespesa(resultado.getInt(1));
				despesa.setIdUsuario(resultado.getInt(2));
				despesa.setDescricao(resultado.getString(3));
				despesa.setValor(Double.parseDouble(resultado.getString(4)));
				despesa.setDataVencimento(LocalDateTime.parse(resultado.getString(5), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
				if(resultado.getString(6) != null) {
					despesa.setDataPagamento(LocalDateTime.parse(resultado.getString(6), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
				}
			}
		} catch (SQLException erro){
			System.out.println("Erro ao executar a query do método consultarDespesaDAO!");
			System.out.println("Erro: " + erro.getMessage());
		} finally {
			Banco.closeResultSet(resultado);
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}
		return despesa;
	}

	public boolean atualizarDespesaDAO(DespesaVO despesaVO) {
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		boolean retorno = false;
		String query = "UPDATE despesa SET descricao = '" + despesaVO.getDescricao() 
					+ "', valor = " + despesaVO.getValor() 
					+ ", datavencimento = '" + despesaVO.getDataVencimento() 
					+ "', datapagamento = '" + despesaVO.getDataPagamento()
					+ "' WHERE iddespesa = " + despesaVO.getIdDespesa();
		try {
			if(stmt.executeUpdate(query) == 1) {
				retorno = true;
			}
		} catch (SQLException erro) {
			System.out.println("Erro ao executar a query do método atualizarDespesaDAO!");
			System.out.println("Erro: " + erro.getMessage());
		} finally {
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}
		return retorno;
	}

	public boolean excluirDespesaDAO(DespesaVO despesaVO) {
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		boolean retorno = false;
		String query = "DELETE FROM despesa WHERE iddespesa = " + despesaVO.getIdDespesa();
		try {
			if(stmt.executeUpdate(query) == 1) {
				retorno = true;
			}
		} catch (SQLException erro) {
			System.out.println("Erro ao executar a query do método excluirDespesaDAO!");
			System.out.println("Erro: " + erro.getMessage());
		} finally {
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}
		return retorno;
	}

}
