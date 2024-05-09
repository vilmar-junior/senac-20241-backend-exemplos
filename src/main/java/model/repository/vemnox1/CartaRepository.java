package model.repository.vemnox1;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import model.entity.senhorfinancas.DespesaVO;
import model.entity.vemnox1.Carta;
import model.entity.vemnox1.Partida;
import model.repository.Banco;
import model.repository.BaseRepository;
import model.seletor.vemnox1.CartaSeletor;

public class CartaRepository implements BaseRepository<Carta> {

	public ArrayList<Carta> sortearSeisCartas(){
		ArrayList<Carta> cartasSorteadas = new ArrayList<Carta>();
		
		String sql = " select * from carta "
				   + " order by rand() "
				   + " limit 6 ";
		
		Connection conn = Banco.getConnection();
		PreparedStatement pstmt = Banco.getPreparedStatement(conn, sql);
		
		try {
			ResultSet resultado = pstmt.executeQuery();
			
			while(resultado.next()) {
				Carta c = construirDoResultSet(resultado);
				cartasSorteadas.add(c);
			}
		} catch (SQLException e) {
			System.out.println("Erro ao sortear cartas");
			System.out.println("Erro: " + e.getMessage());
		} finally {
			Banco.closeStatement(pstmt);
			Banco.closeConnection(conn);
		}
		
		return cartasSorteadas;
	}
	
	@Override
	public Carta salvar(Carta novaCarta) {
		String query = "INSERT INTO carta (nome, forca, inteligencia, velocidade, data_cadastro) VALUES (?, ?, ?, ?, ?)";
		Connection conn = Banco.getConnection();
		PreparedStatement pstmt = Banco.getPreparedStatementWithPk(conn, query);
		try {
			this.preencherParametrosInsertOuUpdate(novaCarta, pstmt);
			
			pstmt.execute();
			ResultSet resultado = pstmt.getGeneratedKeys();
			
			if(resultado.next()) {
				novaCarta.setId(resultado.getInt(1));
			}
		} catch (SQLException erro) {
			System.out.println("Erro ao salvar nova carta");
			System.out.println("Erro: " + erro.getMessage());
		} finally {
			Banco.closeStatement(pstmt);
			Banco.closeConnection(conn);
		}
		return novaCarta;
	}

	@Override
	public boolean excluir(int id) {
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		boolean excluiu = false;
		String query = "DELETE FROM carta WHERE id = " + id;
		try {
			if(stmt.executeUpdate(query) == 1) {
				excluiu = true;
			}
		} catch (SQLException erro) {
			System.out.println("Erro ao excluir carta");
			System.out.println("Erro: " + erro.getMessage());
		} finally {
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}
		return excluiu;
	}

	@Override
	public boolean alterar(Carta cartaAlterada) {
		boolean alterou = false;
		String query = " UPDATE carta SET nome = ?, forca = ?, inteligencia = ?, "
				     + "       velocidade = ?, data_cadastro = ? "
				     + " WHERE id=? ";
		Connection conn = Banco.getConnection();
		PreparedStatement pstmt = Banco.getPreparedStatementWithPk(conn, query);
		try {
			this.preencherParametrosInsertOuUpdate(cartaAlterada, pstmt);
			
			pstmt.setInt(6, cartaAlterada.getId());
			alterou = pstmt.executeUpdate() > 0;
		} catch (SQLException erro) {
			System.out.println("Erro ao atualizar carta");
			System.out.println("Erro: " + erro.getMessage());
		} finally {
			Banco.closeStatement(pstmt);
			Banco.closeConnection(conn);
		}
		return alterou;
	}

	@Override
	public Carta consultarPorId(int id) {
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		
		ResultSet resultado = null;
		Carta carta = null;
		String query = " SELECT * FROM carta WHERE id = " + id;
		
		try{
			resultado = stmt.executeQuery(query);
			if(resultado.next()){
				carta = construirDoResultSet(resultado);
			}
		} catch (SQLException erro){
			System.out.println("Erro ao consultar carta com id (" + id + ")");
			System.out.println("Erro: " + erro.getMessage());
		} finally {
			Banco.closeResultSet(resultado);
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}
		return carta;
	}

	@Override
	public ArrayList<Carta> consultarTodos() {
		ArrayList<Carta> cartas = new ArrayList<>();
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		
		ResultSet resultado = null;
		String query = " SELECT * FROM carta";
		
		try{
			resultado = stmt.executeQuery(query);
			while(resultado.next()){
				Carta carta = construirDoResultSet(resultado);
				cartas.add(carta);
			}
		} catch (SQLException erro){
			System.out.println("Erro ao consultar todas as cartas");
			System.out.println("Erro: " + erro.getMessage());
		} finally {
			Banco.closeResultSet(resultado);
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}
		return cartas;
	}
	
	public ArrayList<Carta> consultarComSeletor(CartaSeletor seletor) {
		ArrayList<Carta> cartas = new ArrayList<>();
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		
		ResultSet resultado = null;
		String sql = " SELECT * FROM carta c";
		
		if(seletor.temFiltro()) {
			sql = preencherFiltros(seletor, sql);
		}
		
		if(seletor.temPaginacao()) {
			sql += " LIMIT " + seletor.getLimite() 
				 + " OFFSET " + seletor.getOffset();
		}
		
		try{
			resultado = stmt.executeQuery(sql);
			while(resultado.next()){
				Carta carta = construirDoResultSet(resultado);
				cartas.add(carta);
			}
		} catch (SQLException erro){
			System.out.println("Erro ao consultar cartas com seletor");
			System.out.println("Erro: " + erro.getMessage());
		} finally {
			Banco.closeResultSet(resultado);
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}
		return cartas;
	}
	
	private String preencherFiltros(CartaSeletor seletor, String sql) {
		
		//Tem pelo menos UM filtro
		sql += " WHERE ";
		boolean primeiro = true;
		
		if(seletor.getNome() != null && seletor.getNome().trim().length() > 0) {
			if(!primeiro) {
				sql += " AND ";
			}
			
			sql += " c.nome LIKE '%" + seletor.getNome() + "%'";
			primeiro = false;
		}
		if(seletor.getForcaMinima() > 0 && seletor.getForcaMaxima() > 0) {
			//Ambos preenchidos
			if(!primeiro) {
				sql += " AND ";
			}
			
			sql += " c.forca BETWEEN " + seletor.getForcaMinima() + " AND " + seletor.getForcaMaxima();
			primeiro = false;
		}else if(seletor.getForcaMinima() > 0) {
			//Somente a mínima --> pesquisa todas as cartas com no MÍNIMO o valor informado (operador <= )
			if(!primeiro) {
				sql += " AND ";
			}
			
			sql += " c.forca >= " + seletor.getForcaMinima();
			primeiro = false;
		}else if(seletor.getForcaMaxima() > 0) {
			//Somente a mínima --> pesquisa todas as cartas com no MÁXIMO o valor informado (operador <= )
			if(!primeiro) {
				sql += " AND ";
			}
			
			sql += " c.forca <= " + seletor.getForcaMaxima();
			primeiro = false;
		}
		
		//continua....
				
		return sql;
	}

	private Carta construirDoResultSet(ResultSet resultado) throws SQLException {
		Carta c = new Carta();
		c.setId(resultado.getInt("ID"));
		c.setForca(resultado.getInt("FORCA"));
		c.setInteligencia(resultado.getInt("INTELIGENCIA"));
		c.setVelocidade(resultado.getInt("VELOCIDADE"));
		c.setNome(resultado.getString("NOME"));
		
		if(resultado.getDate("DATA_CADASTRO") != null) {
			c.setDataCadastro(resultado.getDate("DATA_CADASTRO").toLocalDate());
		}
		
		return c;
	}
	
	private void preencherParametrosInsertOuUpdate(Carta novaCarta, PreparedStatement pstmt) throws SQLException {
		pstmt.setString(1, novaCarta.getNome());
		pstmt.setInt(2, novaCarta.getForca());
		pstmt.setInt(3, novaCarta.getInteligencia());
		pstmt.setInt(4, novaCarta.getVelocidade());
		pstmt.setDate(5, Date.valueOf(novaCarta.getDataCadastro()));
	}

	public int contarTotalRegistros(CartaSeletor seletor) {
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		
		int totalRegistros = 0;
		ResultSet resultado = null;
		String query = " select COUNT(c.ID) from carta c ";
		
		if(seletor.temFiltro()) {
			query = preencherFiltros(seletor, query);
		}
		
		try{
			resultado = stmt.executeQuery(query);
			if(resultado.next()){
				totalRegistros = resultado.getInt(1);
			}
		} catch (SQLException erro){
			System.out.println("Erro ao contar as cartas filtradas");
			System.out.println("Erro: " + erro.getMessage());
		} finally {
			Banco.closeResultSet(resultado);
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}
		return totalRegistros;
	}

	public int contarPaginas(CartaSeletor seletor) {
		int totalPaginas = 0;
		int totalRegistros = this.contarTotalRegistros(seletor);	
		
		totalPaginas =  totalRegistros / seletor.getLimite();
		int resto = totalRegistros % seletor.getLimite(); 
		
		if(resto > 0) {
			totalPaginas++;
		}
		
		return totalPaginas;
	}
}
