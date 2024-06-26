package model.repository.vemnox1;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import model.dto.vemnox1.UsuarioDTO;
import model.entity.enums.vemnox1.PerfilAcesso;
import model.entity.vemnox1.Jogador;
import model.repository.Banco;
import model.repository.BaseRepository;
import util.StringUtils;

public class JogadorRepository implements BaseRepository<Jogador> {

	@Override
	public Jogador salvar(Jogador novoJogador) {
		String query = "INSERT INTO jogador (nome, email, senha, perfil_acesso, data_nascimento, total_partidas, percentual_vitorias) VALUES (?, ?, ?, ?, ?, ?, ?)";
		Connection conn = Banco.getConnection();
		PreparedStatement pstmt = Banco.getPreparedStatementWithPk(conn, query);
		try {
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

	private void preencherParametrosParaInsertOuUpdate(PreparedStatement pstmt, 
			Jogador novoJogador) throws SQLException {
		pstmt.setString(1, novoJogador.getNome());
		pstmt.setString(2, novoJogador.getEmail());
		pstmt.setString(3, StringUtils.cifrar(novoJogador.getSenha()));
		pstmt.setString(4, novoJogador.getPerfil().toString());
		pstmt.setDate(5, Date.valueOf(novoJogador.getDataNascimento()));
		pstmt.setInt(6, novoJogador.getTotalPartidas());
		pstmt.setDouble(7, novoJogador.getPercentualVitorias());
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
					 + " SET   nome=?, email=?, senha=?, perfil_acesso=?,"
					 + "       data_nascimento=?, total_partidas=?, percentual_vitorias=?, id_sessao=? "
				     + " WHERE id=?";
		Connection conn = Banco.getConnection();
		PreparedStatement pstmt = Banco.getPreparedStatement(conn, query);
		try {
			this.preencherParametrosParaInsertOuUpdate(pstmt, novoJogador);
			
			pstmt.setString(8, novoJogador.getIdSessao());
			pstmt.setInt(9, novoJogador.getId());
			
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
	
	public boolean alterarIdSessao(Jogador novoJogador) {
		boolean alterou = false;
		String query = " UPDATE jogador "
					 + " SET   id_sessao=? "
				     + " WHERE id=?";
		Connection conn = Banco.getConnection();
		PreparedStatement pstmt = Banco.getPreparedStatement(conn, query);
		try {
			pstmt.setString(1, novoJogador.getIdSessao());
			pstmt.setInt(2, novoJogador.getId());
			
			alterou = pstmt.executeUpdate() > 0;
		} catch (SQLException erro) {
			System.out.println("Erro ao atualizar idSessao do jogador");
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
				jogador = converterResultSetParaJogador(resultado);
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
		String query = " SELECT * FROM jogador ";
		
		try{
			resultado = stmt.executeQuery(query);
			while(resultado.next()){
				Jogador jogador = this.converterResultSetParaJogador(resultado);
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
	
	private Jogador converterResultSetParaJogador(ResultSet resultado) throws SQLException {
		Jogador jogador = new Jogador();
		jogador.setId(Integer.parseInt(resultado.getString("ID")));
		jogador.setNome(resultado.getString("NOME"));
		jogador.setEmail(resultado.getString("EMAIL"));
		jogador.setDataNascimento(resultado.getDate("DATA_NASCIMENTO").toLocalDate()); 
		jogador.setTotalPartidas(resultado.getInt("TOTAL_PARTIDAS"));
		jogador.setPercentualVitorias(resultado.getInt("PERCENTUAL_VITORIAS"));
		jogador.setPerfil(PerfilAcesso.valueOf(resultado.getString("PERFIL_ACESSO")));
		jogador.setIdSessao(resultado.getString("ID_SESSAO"));
		return jogador;
	}
	
	/**
	 * Referência: https://www.baeldung.com/sha-256-hashing-java
	 * @param usuarioDTO dto com login e senha informados
	 * @return jogador encontrado no banco
	 */
	public Jogador consultarPorLoginSenha(UsuarioDTO usuarioDTO) {
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		
		ResultSet resultado = null;
		Jogador jogador = null;
		String query = " SELECT * FROM jogador "
				     + " WHERE email = '" + usuarioDTO.getLogin() + "'"
				     + " AND senha = '" + StringUtils.cifrar(usuarioDTO.getSenha()) + "'";
		try{
			resultado = stmt.executeQuery(query);
			if(resultado.next()){
				jogador = this.converterResultSetParaJogador(resultado);
			}
		} catch (SQLException erro){
			System.out.println("Erro ao consultar jogador com login (" + usuarioDTO.getLogin() + ")");
			System.out.println("Erro: " + erro.getMessage());
		} finally {
			Banco.closeResultSet(resultado);
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}
		return jogador;
	}

	public Jogador consultarPorLogin(String login) {
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		
		ResultSet resultado = null;
		Jogador jogador = new Jogador();
		String query = " SELECT * FROM jogador "
				     + " WHERE email = '" + login + "'";
		try{
			resultado = stmt.executeQuery(query);
			if(resultado.next()){
				jogador = this.converterResultSetParaJogador(resultado);
			}
		} catch (SQLException erro){
			System.out.println("Erro ao consultar jogador com login (" + login + ")");
			System.out.println("Erro: " + erro.getMessage());
		} finally {
			Banco.closeResultSet(resultado);
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}
		return jogador;
	}

	public Jogador consultarPorIdSessao(String idSessao) {
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		
		ResultSet resultado = null;
		Jogador jogador = new Jogador();
		String query = " SELECT * FROM jogador "
				     + " WHERE id_sessao = '" + idSessao + "'";
		try{
			resultado = stmt.executeQuery(query);
			if(resultado.next()){
				jogador = this.converterResultSetParaJogador(resultado);
			}
		} catch (SQLException erro){
			System.out.println("Erro ao consultar jogador com idSessao (" + idSessao + ")");
			System.out.println("Erro: " + erro.getMessage());
		} finally {
			Banco.closeResultSet(resultado);
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}
		return jogador;
	}
}
