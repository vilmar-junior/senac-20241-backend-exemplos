package model.repository.vemnox1;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import model.entity.vemnox1.Jogador;
import model.repository.Banco;
import model.repository.BaseRepository;

public class JogadorRepository implements BaseRepository<Jogador> {

	@Override
	public Jogador salvar(Jogador novoJogador) {
		String query = "INSERT INTO jogador (nome, email, data_nascimento, total_partidas, percentual_vitorias) VALUES (?, ?, ?, ?, ?)";
		Connection conn = Banco.getConnection();
		PreparedStatement pstmt = Banco.getPreparedStatementWithPk(conn, query);
		try {
			//TODO este bloco repete-se no alterar().... refatorar!
			preencherParametrosParaInsertOuUpdate(pstmt, novoJogador);
			
			pstmt.execute();
			ResultSet resultado = pstmt.getGeneratedKeys();
			
			if(resultado.next()) {
				novoJogador.setId(resultado.getInt(1));
			}
		} catch (SQLException erro) {
			System.out.println("Erro ao salvar novo jogador");
			System.out.println("Erro: " + erro.getMessage());
		} finally {
			Banco.closeStatement(pstmt);
			Banco.closeConnection(conn);
		}
		return novoJogador;
	}

	private void preencherParametrosParaInsertOuUpdate(PreparedStatement pstmt
			, Jogador novoJogador) throws SQLException {
		pstmt.setString(1, novoJogador.getNome());
		pstmt.setString(2, novoJogador.getEmail());
		pstmt.setDate(3, Date.valueOf(novoJogador.getDataNascimento()));
		pstmt.setInt(4, novoJogador.getTotalPartidas());
		pstmt.setDouble(5, novoJogador.getPercentualVitorias());
	}

	@Override
	public boolean excluir(int id) {
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		boolean excluiu = false;
		String query = "DELETE FROM jogador WHERE id = " + id;
		try {
			if(stmt.executeUpdate(query) == 1) {
				excluiu = true;
			}
		} catch (SQLException erro) {
			System.out.println("Erro ao excluir jogador");
			System.out.println("Erro: " + erro.getMessage());
		} finally {
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}
		return excluiu;
	}

	@Override
	public boolean alterar(Jogador novoJogador) {
		boolean alterou = false;
		String query = " UPDATE jogador "
					 + " SET   nome=?, email=?, data_nascimento=?, "
				     + "       total_partidas=?, percentual_vitorias=? "
				     + " WHERE id=?";
		Connection conn = Banco.getConnection();
		PreparedStatement pstmt = Banco.getPreparedStatement(conn, query);
		try {
			//TODO este bloco repete-se no salvar().... refatorar!
			pstmt.setString(1, novoJogador.getNome());
			pstmt.setString(2, novoJogador.getEmail());
			pstmt.setDate(3, Date.valueOf(novoJogador.getDataNascimento()));
			pstmt.setInt(4, novoJogador.getTotalPartidas());
			pstmt.setDouble(5, novoJogador.getPercentualVitorias());
			
			pstmt.setInt(6, novoJogador.getId());
			
			alterou = pstmt.executeUpdate() > 0;
		} catch (SQLException erro) {
			System.out.println("Erro ao atualizar jogador");
			System.out.println("Erro: " + erro.getMessage());
		} finally {
			Banco.closeStatement(pstmt);
			Banco.closeConnection(conn);
		}
		return alterou;
	}

	@Override
	public Jogador consultarPorId(int id) {
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		
		ResultSet resultado = null;
		Jogador jogador = new Jogador();
		String query = " SELECT * FROM jogador WHERE id = " + id;
		
		try{
			resultado = stmt.executeQuery(query);
			if(resultado.next()){
				//TODO este bloco repete-se no consultarTodos().... refatorar!
				jogador.setId(Integer.parseInt(resultado.getString("ID")));
				jogador.setNome(resultado.getString("NOME"));
				jogador.setEmail(resultado.getString("EMAIL"));
				jogador.setDataNascimento(resultado.getDate("DATA_NASCIMENTO").toLocalDate()); 
				jogador.setTotalPartidas(resultado.getInt("TOTAL_PARTIDAS"));
				jogador.setPercentualVitorias(resultado.getInt("PERCENTUAL_VITORIAS"));
			}
		} catch (SQLException erro){
			System.out.println("Erro ao consultar jogador com id (" + id + ")");
			System.out.println("Erro: " + erro.getMessage());
		} finally {
			Banco.closeResultSet(resultado);
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}
		return jogador;
	}

	@Override
	public ArrayList<Jogador> consultarTodos() {
		ArrayList<Jogador> jogadores = new ArrayList<>();
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		
		ResultSet resultado = null;
		String query = " SELECT * FROM jogador";
		
		try{
			resultado = stmt.executeQuery(query);
			while(resultado.next()){
				Jogador jogador = new Jogador();
				
				//TODO este bloco repete-se no consultarTodos().... refatorar!
				jogador.setId(Integer.parseInt(resultado.getString("ID")));
				jogador.setNome(resultado.getString("NOME"));
				jogador.setEmail(resultado.getString("EMAIL"));
				jogador.setDataNascimento(resultado.getDate("DATA_NASCIMENTO").toLocalDate()); 
				jogador.setTotalPartidas(resultado.getInt("TOTAL_PARTIDAS"));
				jogador.setPercentualVitorias(resultado.getInt("PERCENTUAL_VITORIAS"));
				jogadores.add(jogador);
			}
		} catch (SQLException erro){
			System.out.println("Erro ao consultar todas os jogadores");
			System.out.println("Erro: " + erro.getMessage());
		} finally {
			Banco.closeResultSet(resultado);
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}
		return jogadores;
	}
}
