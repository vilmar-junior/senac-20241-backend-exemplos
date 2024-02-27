package model.repository;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;

import model.entity.vemnox1.Partida;

public class PartidaRepository implements BaseRepository<Partida> {

	@Override
	public Partida salvar(Partida novaPartida) {
		//Atenção: os demais atributos não são preenchidos ao criar a partida, pois iniciam zerados
		String query = "INSERT INTO PARTIDA (ID_JOGADOR, DATA) VALUES (?, ?)";
		Connection conn = Banco.getConnection();
		PreparedStatement pstmt = Banco.getPreparedStatementWithPk(conn, query);
		try {
			pstmt.setInt(1, novaPartida.getJogador().getId());
			pstmt.setDate(2, Date.valueOf(LocalDate.now()));
			pstmt.execute();
			ResultSet resultado = pstmt.getGeneratedKeys();
			if(resultado.next()) {
				novaPartida.setId(resultado.getInt(1));
			}
		} catch (SQLException erro) {
			System.out.println("Erro criar uma nova partida");
			System.out.println("Erro: " + erro.getMessage());
		} finally {
			Banco.closeStatement(pstmt);
			Banco.closeConnection(conn);
		}
		return novaPartida;
	}

	@Override
	public boolean excluir(int id) {
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		boolean retorno = false;
		String query = "DELETE FROM partida WHERE id = " + id;
		try {
			if(stmt.executeUpdate(query) == 1) {
				retorno = true;
			}
		} catch (SQLException erro) {
			System.out.println("Erro ao excluir partida");
			System.out.println("Erro: " + erro.getMessage());
		} finally {
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}
		return retorno;
	}

	@Override
	public boolean alterar(Partida entidade) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Partida consultarPorId(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Partida> consultarTodos() {
		// TODO Auto-generated method stub
		return null;
	}

}
