package model.repository.vacinacao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import model.entity.vacinacao.Pessoa;
import model.entity.vacinacao.Vacina;
import model.repository.Banco;
import model.repository.BaseRepository;
import model.seletor.VacinaSeletor;
import model.seletor.vemnox1.CartaSeletor;

public class VacinaRepository implements BaseRepository<Vacina> {

	@Override
	public Vacina salvar(Vacina novaVacina) {
		String sql = " INSERT INTO vacina(id_pesquisador, nome, id_pais, estagio_pesquisa, data_inicio_pesquisa, media) "
				   + " VALUES(?, ?, ?, ?, ?, ?) ";
		Connection conexao = Banco.getConnection();
		PreparedStatement stmt = Banco.getPreparedStatementWithPk(conexao, sql);
		
		try {
			stmt.setInt(1, novaVacina.getPesquisadorResponsavel().getId());
			stmt.setString(2, novaVacina.getNome());
			stmt.setInt(3, novaVacina.getPaisOrigem().getId());
			stmt.setInt(4, novaVacina.getEstagio());
			stmt.setDate(5, Date.valueOf(novaVacina.getDataInicioPesquisa()));
			stmt.setDouble(6, novaVacina.getMedia());
			
			stmt.execute();
			ResultSet resultado = stmt.getGeneratedKeys();
			if(resultado.next()) {
				novaVacina.setId(resultado.getInt(1));
			}
		} catch (SQLException e) {
			System.out.println("Erro ao salvar nova vacina");
			System.out.println("Erro: " + e.getMessage());
		}
		
		return novaVacina;
	}

	@Override
	public boolean excluir(int id) {
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		boolean excluiu = false;
		String query = "DELETE FROM vacina WHERE id = " + id;
		try {
			if(stmt.executeUpdate(query) == 1) {
				excluiu = true;
			}
		} catch (SQLException erro) {
			System.out.println("Erro ao excluir vacina");
			System.out.println("Erro: " + erro.getMessage());
		} finally {
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}
		return excluiu;
	}

	@Override
	public boolean alterar(Vacina vacinaEditada) {
		boolean alterou = false;
		String query = " UPDATE vacina "
				     + " SET id_pesquisador=?, nome=?, id_pais=?, estagio_pesquisa=?, data_inicio_pesquisa=?, media=? "
				     + " WHERE id=? ";
		Connection conn = Banco.getConnection();
		PreparedStatement stmt = Banco.getPreparedStatementWithPk(conn, query);
		try {
			stmt.setInt(1, vacinaEditada.getPesquisadorResponsavel().getId());
			stmt.setString(2, vacinaEditada.getNome());
			stmt.setInt(3, vacinaEditada.getPaisOrigem().getId());
			stmt.setInt(4, vacinaEditada.getEstagio());
			stmt.setDate(5, Date.valueOf(vacinaEditada.getDataInicioPesquisa()));
			stmt.setDouble(6, vacinaEditada.getMedia());
			
			stmt.setInt(7, vacinaEditada.getId());
			alterou = stmt.executeUpdate() > 0;
		} catch (SQLException erro) {
			System.out.println("Erro ao atualizar vacina");
			System.out.println("Erro: " + erro.getMessage());
		} finally {
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}
		return alterou;
	}

	@Override
	public Vacina consultarPorId(int id) {
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		
		Vacina vacina = null;
		ResultSet resultado = null;
		String query = " SELECT * FROM vacina WHERE id = " + id;
		
		try{
			resultado = stmt.executeQuery(query);
			PessoaRepository pessoaRepository = new PessoaRepository();
			if(resultado.next()){
				vacina = new Vacina();
				vacina.setId(Integer.parseInt(resultado.getString("ID")));
				vacina.setNome(resultado.getString("NOME"));
				
				PaisRepository paisRepository = new PaisRepository();
				vacina.setPaisOrigem(paisRepository.consultarPorId(resultado.getInt("ID_PAIS")));
				
				vacina.setEstagio(resultado.getInt("ESTAGIO_PESQUISA"));
				vacina.setDataInicioPesquisa(resultado.getDate("DATA_INICIO_PESQUISA").toLocalDate()); 
				Pessoa pesquisador = pessoaRepository.consultarPorId(resultado.getInt("ID_PESQUISADOR"));
				vacina.setPesquisadorResponsavel(pesquisador);
				vacina.setMedia(resultado.getDouble("MEDIA"));
			}
		} catch (SQLException erro){
			System.out.println("Erro ao consultar vacina com o id: " + id);
			System.out.println("Erro: " + erro.getMessage());
		} finally {
			Banco.closeResultSet(resultado);
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}
		return vacina;
	}

	@Override
	public ArrayList<Vacina> consultarTodos() {
		ArrayList<Vacina> vacinas = new ArrayList<>();
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		
		ResultSet resultado = null;
		String query = " SELECT * FROM vacina";
		
		try{
			resultado = stmt.executeQuery(query);
			PessoaRepository pessoaRepository = new PessoaRepository();
			while(resultado.next()){
				Vacina vacina = new Vacina();
				vacina.setId(Integer.parseInt(resultado.getString("ID")));
				vacina.setNome(resultado.getString("NOME"));

				PaisRepository paisRepository = new PaisRepository();
				vacina.setPaisOrigem(paisRepository.consultarPorId(resultado.getInt("ID_PAIS")));

				vacina.setEstagio(resultado.getInt("ESTAGIO_PESQUISA"));
				vacina.setDataInicioPesquisa(resultado.getDate("DATA_INICIO_PESQUISA").toLocalDate()); 
				Pessoa pesquisador = pessoaRepository.consultarPorId(resultado.getInt("ID_PESQUISADOR"));
				vacina.setPesquisadorResponsavel(pesquisador);
				vacina.setMedia(resultado.getDouble("MEDIA"));

				vacinas.add(vacina);
			}
		} catch (SQLException erro){
			System.out.println("Erro ao consultar todas as vacinas");
			System.out.println("Erro: " + erro.getMessage());
		} finally {
			Banco.closeResultSet(resultado);
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}
		return vacinas;
	}
	
	public ArrayList<Vacina> consultarComFiltros(VacinaSeletor seletor){
		ArrayList<Vacina> vacinas = new ArrayList<>();
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		
		ResultSet resultado = null;
		String query = " select v.* from vacina v "
					 + " inner join pais p on v.id_pais = p.id "
					 + " inner join pessoa pe on v.id_pesquisador = pe.id  ";
		
		query = incluirFiltros(seletor, query);
		
		if(seletor.temPaginacao()) {
			query += " LIMIT " + seletor.getLimite();
			query += " OFFSET " + seletor.getOffset();
		}
		
		try{
			resultado = stmt.executeQuery(query);
			PessoaRepository pessoaRepository = new PessoaRepository();
			while(resultado.next()){
				Vacina vacina = new Vacina();
				vacina.setId(Integer.parseInt(resultado.getString("ID")));
				vacina.setNome(resultado.getString("NOME"));

				PaisRepository paisRepository = new PaisRepository();
				vacina.setPaisOrigem(paisRepository.consultarPorId(resultado.getInt("ID_PAIS")));

				vacina.setEstagio(resultado.getInt("ESTAGIO_PESQUISA"));
				vacina.setDataInicioPesquisa(resultado.getDate("DATA_INICIO_PESQUISA").toLocalDate()); 
				Pessoa pesquisador = pessoaRepository.consultarPorId(resultado.getInt("ID_PESQUISADOR"));
				vacina.setPesquisadorResponsavel(pesquisador);
				vacinas.add(vacina);
			}
		} catch (SQLException erro){
			System.out.println("Erro ao consultar todas as vacinas");
			System.out.println("Erro: " + erro.getMessage());
		} finally {
			Banco.closeResultSet(resultado);
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}
		return vacinas;
	}

	private String incluirFiltros(VacinaSeletor seletor, String query) {
		boolean primeiro = true;
		if(seletor.getNomeVacina() != null) {
			if(primeiro) {
				query += " WHERE ";
			}else {
				query += " AND ";
			}
			query += "upper(v.nome) LIKE UPPER('%" + seletor.getNomeVacina() + "%')";
			primeiro = false;
		}
		
		if(seletor.getNomePais() != null) {
			if(primeiro) {
				query += " WHERE ";
			}else {
				query += " AND ";
			}
			query += " upper(p.nome) LIKE UPPER('%" + seletor.getNomePais() + "%')";
		}
		
		if(seletor.getNomePesquisador() != null) {
			if(primeiro) {
				query += " WHERE ";
			}else {
				query += " AND ";
			}
			query += " upper(pe.nome) LIKE UPPER('%" + seletor.getNomePesquisador() + "%')";
		}
		
		return query;
	}

	public int contarTotalRegistros(VacinaSeletor seletor) {
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		
		int totalRegistros = 0;
		ResultSet resultado = null;
		String query = " select COUNT(v.ID) from vacina v "
					 + " inner join pais p on v.id_pais = p.id "
					 + " inner join pessoa pe on v.id_pesquisador = pe.id  ";
		
		query = incluirFiltros(seletor, query);
		
		try{
			resultado = stmt.executeQuery(query);
			if(resultado.next()){
				totalRegistros = resultado.getInt(1);
			}
		} catch (SQLException erro){
			System.out.println("Erro ao contar as vacinas filtradas");
			System.out.println("Erro: " + erro.getMessage());
		} finally {
			Banco.closeResultSet(resultado);
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}
		return totalRegistros;
	}

	public int contarPaginas(VacinaSeletor seletor) {
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
