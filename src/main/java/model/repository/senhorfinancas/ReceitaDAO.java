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
import model.repository.Banco;

import model.entity.senhorfinancas.ReceitaVO;

public class ReceitaDAO {

	public ReceitaVO cadastrarReceitaDAO(ReceitaVO receitaVO) {
		String query = "INSERT INTO receita (idusuario, descricao, valor, datareceita) VALUES (?, ?, ?, ?)";
		Connection conn = Banco.getConnection();
		PreparedStatement pstmt = Banco.getPreparedStatementWithPk(conn, query);
		try {
			pstmt.setInt(1, receitaVO.getIdUsuario());
			pstmt.setString(2, receitaVO.getDescricao());
			pstmt.setDouble(3, receitaVO.getValor());
			pstmt.setObject(4, receitaVO.getDataReceita());
			pstmt.execute();
			ResultSet resultado = pstmt.getGeneratedKeys();
			if(resultado.next()) {
				receitaVO.setIdReceita(resultado.getInt(1));
			}
		} catch (SQLException erro) {
			System.out.println("Erro ao executar a query do método cadastrarReceitaDAO!");
			System.out.println("Erro: " + erro.getMessage());
		} finally {
			Banco.closeStatement(pstmt);
			Banco.closeConnection(conn);
		}
		return receitaVO;
	}

	public List<ReceitaVO> consultarTodasReceitasDAO(int idUsuario) {
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		
		ResultSet resultado = null;
		ArrayList<ReceitaVO> listaReceitas = new ArrayList<>();
		String query = "SELECT idreceita, idusuario, descricao, valor, datareceita "
				+ "FROM receita WHERE idusuario = " + idUsuario;

		try{
			resultado = stmt.executeQuery(query);
			while(resultado.next()){
				ReceitaVO receita = new ReceitaVO();
				receita.setIdReceita(Integer.parseInt(resultado.getString(1)));
				receita.setIdUsuario(Integer.parseInt(resultado.getString(2)));
				receita.setDescricao(resultado.getString(3));
				receita.setValor(Double.parseDouble(resultado.getString(4)));
				receita.setDataReceita(LocalDateTime.parse(resultado.getString(5), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
				listaReceitas.add(receita);
			}
		} catch (SQLException erro){
			System.out.println("Erro ao executar a query do método consultarTodasReceitasDAO!");
			System.out.println("Erro: " + erro.getMessage());
		} finally {
			Banco.closeResultSet(resultado);
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}
		return listaReceitas;
	}

	public ReceitaVO consultarReceitaDAO(int idReceita) {
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		
		ResultSet resultado = null;
		ReceitaVO receita = new ReceitaVO();
		String query = "SELECT idreceita, idusuario, descricao, valor, datareceita "
				+ "FROM receita WHERE idreceita = " + idReceita;

		try{
			resultado = stmt.executeQuery(query);
			if(resultado.next()){
				receita.setIdReceita(Integer.parseInt(resultado.getString(1)));
				receita.setIdUsuario(Integer.parseInt(resultado.getString(2)));
				receita.setDescricao(resultado.getString(3));
				receita.setValor(Double.parseDouble(resultado.getString(4)));
				receita.setDataReceita(LocalDateTime.parse(resultado.getString(5), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
			}
		} catch (SQLException erro){
			System.out.println("Erro ao executar a query do método consultarReceitaDAO!");
			System.out.println("Erro: " + erro.getMessage());
		} finally {
			Banco.closeResultSet(resultado);
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}
		return receita;
	}

	public boolean atualizarReceitaDAO(ReceitaVO receitaVO) {
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		boolean retorno = false;
		String query = "UPDATE receita SET descricao = '" + receitaVO.getDescricao() 
					+ "', valor = " + receitaVO.getValor() 
					+ ", datareceita = '" + receitaVO.getDataReceita() 
					+ "' WHERE idreceita = " + receitaVO.getIdReceita();
		try {
			if(stmt.executeUpdate(query) == 1) {
				retorno = true;
			}
		} catch (SQLException erro) {
			System.out.println("Erro ao executar a query do método atualizarReceitaDAO!");
			System.out.println("Erro: " + erro.getMessage());
		} finally {
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}
		return retorno;
	}

	public boolean excluirReceitaDAO(ReceitaVO receitaVO) {
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		boolean retorno = false;
		String query = "DELETE FROM receita WHERE idreceita = " + receitaVO.getIdReceita();
		try {
			if(stmt.executeUpdate(query) == 1) {
				retorno = true;
			}
		} catch (SQLException erro) {
			System.out.println("Erro ao executar a query do método excluirReceitaDAO!");
			System.out.println("Erro: " + erro.getMessage());
		} finally {
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}
		return retorno;
	}

}
