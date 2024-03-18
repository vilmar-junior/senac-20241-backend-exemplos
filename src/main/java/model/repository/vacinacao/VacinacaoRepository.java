package model.repository.vacinacao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import model.entity.vacinacao.Vacina;
import model.entity.vacinacao.Vacinacao;
import model.entity.vacinacao.Vacinacao;
import model.entity.vemnox1.Carta;
import model.repository.Banco;
import model.repository.BaseRepository;

public class VacinacaoRepository implements BaseRepository<Vacinacao> {

	@Override
	public Vacinacao salvar(Vacinacao novaVacinacao) {
		String sql = " INSERT INTO aplicacao_vacina (id_pessoa, id_vacina, data_aplicacao, avaliacao) "
				   + " VALUES(?, ?, ?, ?) ";
		Connection conexao = Banco.getConnection();
		PreparedStatement stmt = Banco.getPreparedStatementWithPk(conexao, sql);
		
		try {
			stmt.setInt(1, novaVacinacao.getIdPessoa());
			stmt.setInt(2, novaVacinacao.getVacina().getId());
			stmt.setDate(3, Date.valueOf(novaVacinacao.getDataAplicacao()));
			stmt.setInt(4, novaVacinacao.getAvaliacao());
			
			ResultSet resultado = stmt.getGeneratedKeys();
			if(resultado.next()) {
				novaVacinacao.setId(resultado.getInt(1));
			}
		} catch (SQLException e) {
			System.out.println("Erro ao salvar nova aplicação");
			System.out.println("Erro: " + e.getMessage());
		}
		
		return novaVacinacao;
	}

	@Override
	public boolean excluir(int id) {
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		boolean excluiu = false;
		String query = "DELETE FROM aplicacao_vacina WHERE id = " + id;
		try {
			if(stmt.executeUpdate(query) == 1) {
				excluiu = true;
			}
		} catch (SQLException erro) {
			System.out.println("Erro ao excluir aplicação");
			System.out.println("Erro: " + erro.getMessage());
		} finally {
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}
		return excluiu;
	}

	@Override
	public boolean alterar(Vacinacao entidade) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Vacinacao consultarPorId(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Vacinacao> consultarTodos() {
		ArrayList<Vacinacao> aplicacoes = new ArrayList<>();
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		
		ResultSet resultado = null;
		String query = " SELECT * FROM aplicacao_vacina";
		
		try{
			resultado = stmt.executeQuery(query);
			VacinaRepository vacinaRepository = new VacinaRepository();
			while(resultado.next()){
				Vacinacao vacinacao = new Vacinacao();
				vacinacao.setId(resultado.getInt("ID"));
				vacinacao.setIdPessoa(resultado.getInt("ID_PESSOA"));
				
				Vacina vacinaAplicada = vacinaRepository.consultarPorId(resultado.getInt("ID_VACINA"));
				
				vacinacao.setVacina(vacinaAplicada);
				vacinacao.setDataAplicacao(resultado.getDate("DATA_APLICACAO").toLocalDate());
				vacinacao.setAvaliacao(resultado.getInt("AVALIACAO"));
				
				aplicacoes.add(vacinacao);
			}
		} catch (SQLException erro){
			System.out.println("Erro ao executar consultar todas as cartas");
			System.out.println("Erro: " + erro.getMessage());
		} finally {
			Banco.closeResultSet(resultado);
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}
		return aplicacoes;
	}
}
