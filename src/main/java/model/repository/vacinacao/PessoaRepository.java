package model.repository.vacinacao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import model.entity.vacinacao.Pessoa;
import model.entity.vemnox1.Carta;
import model.repository.Banco;
import model.repository.BaseRepository;

public class PessoaRepository implements BaseRepository<Pessoa> {

	@Override
	public Pessoa salvar(Pessoa novaPessoa) {
		String sql = " INSERT INTO pessoa (nome, cpf, sexo, data_nascimento, tipo) "
				   + " VALUES(?, ?, ?, ?, ?) ";
		Connection conexao = Banco.getConnection();
		PreparedStatement stmt = Banco.getPreparedStatementWithPk(conexao, sql);
		
		try {
			stmt.setString(1, novaPessoa.getNome());
			stmt.setString(2, novaPessoa.getCpf());
			stmt.setString(3, novaPessoa.getSexo() + "");
			stmt.setDate(4, Date.valueOf(novaPessoa.getDataNascimento()));
			stmt.setInt(5, novaPessoa.getTipo());
			
			ResultSet resultado = stmt.getGeneratedKeys();
			if(resultado.next()) {
				novaPessoa.setId(resultado.getInt(1));
			}
		} catch (SQLException e) {
			System.out.println("Erro ao salvar nova pessoa");
			System.out.println("Erro: " + e.getMessage());
		}
		
		return novaPessoa;
	}

	@Override
	public boolean excluir(int id) {
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		boolean excluiu = false;
		String query = "DELETE FROM pessoa WHERE id = " + id;
		try {
			if(stmt.executeUpdate(query) == 1) {
				excluiu = true;
			}
		} catch (SQLException erro) {
			System.out.println("Erro ao excluir pessoa");
			System.out.println("Erro: " + erro.getMessage());
		} finally {
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}
		return excluiu;
	}

	@Override
	public boolean alterar(Pessoa entidade) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Pessoa consultarPorId(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Pessoa> consultarTodos() {
		ArrayList<Pessoa> pessoas = new ArrayList<>();
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		
		ResultSet resultado = null;
		String query = " SELECT * FROM pessoa";
		
		try{
			resultado = stmt.executeQuery(query);
			while(resultado.next()){
				Pessoa pessoa = new Pessoa();
				pessoa.setId(Integer.parseInt(resultado.getString("ID")));
				pessoa.setNome(resultado.getString("NOME"));
				pessoa.setCpf(resultado.getString("CPF"));
				pessoa.setSexo(resultado.getString("SEXO").charAt(0));
				pessoa.setDataNascimento(resultado.getDate("DATA_NASCIMENTO").toLocalDate()); 
				pessoa.setTipo(Integer.parseInt(resultado.getString("TIPO")));
				pessoas.add(pessoa);
			}
		} catch (SQLException erro){
			System.out.println("Erro ao executar consultar todas as cartas");
			System.out.println("Erro: " + erro.getMessage());
		} finally {
			Banco.closeResultSet(resultado);
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}
		return pessoas;
	}

	public boolean cpfJaCadastrado(String cpf) {
		boolean cpfJaUtilizado = false;	
		
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		String query = " SELECT count(id) FROM pessoa WHERE cpf = " + cpf;
		
		try {
			ResultSet resultado = stmt.executeQuery(query);
			cpfJaUtilizado = (resultado.getInt(1) > 0);
		} catch (SQLException e) {
			System.out.println("Erro ao consultar CPF. Causa: " + e.getMessage());
		}
		
		return cpfJaUtilizado;
	}
}
