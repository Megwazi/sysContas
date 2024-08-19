package ifsc.ctds.syscontas.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import ifsc.ctds.syscontas.entity.Contas;

public class ContasDAO implements DAO<Contas>{

	@Override
	public Object get(Long idcontas) {
		Contas contas = null;
		String sql = "SELECT * FROM contas WHERE idcontas = ?";
		//Conexão com o banco de dados
		Connection conexao = null;
		//Preparação de um comando sql
		PreparedStatement stm = null;
		//Guarda o retorno da operação sql
		ResultSet rset = null;
		try {
			//obtem conexão com o banco
			conexao = Conexao.getConnection();
			//preparar o comando e seus parâmetros
			stm = (PreparedStatement) conexao.prepareStatement(sql);
			stm.setInt(1, idcontas.intValue());
			//executa a consulta e obtem as respostas
			rset = stm.executeQuery();
			while(rset.next()) {
				contas = new Contas();
				//atribui os valores lidos ao objetos
				contas.setIdConta(rset.getInt("idcontas"));
				contas.setValor(rset.getDouble("valor"));
				contas.setVencimento(rset.getString("vencimento"));
				contas.setSituacao(rset.getString("situacao"));

			}
		} catch(SQLException e) {
			System.out.println("Erro ao conectar ao banco de dados. Error: " + e);
		} finally {
			//Fecha todas as conexões após o seu uso
			try {
				if (stm != null)
					stm.close();
				if (conexao != null)
					conexao.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}	
		return contas;
	}

	@Override
	public List<Contas> getAll() {
		List<Contas> contas = new ArrayList<Contas>();
		String sql = "SELECT * FROM contas";
		//Conexão com o banco de dados
		Connection conexao = null;
		//Preparação de um comando sql
		PreparedStatement stm = null;
		//Guarda o retorno da operação sql
		ResultSet rset = null;
		try {
			//obtem conexão com o banco
			conexao = Conexao.getConnection();
			//preparar o comando e seus parâmetros
			stm = (PreparedStatement) conexao.prepareStatement(sql);			
			//executa a consulta e obtem as respostas
			rset = stm.executeQuery();
			while(rset.next()) {
				Contas conta = new Contas();
				//atribui os valores lidos ao objetos
				conta.setIdConta(rset.getInt("idcontas"));
				conta.setValor(rset.getDouble("valor"));
				conta.setVencimento(rset.getString("vencimento"));
				conta.setSituacao(rset.getString("situacao"));
				//Adiciona a lista de caixas o novo objeto
				contas.add(conta);
			}
		} catch(SQLException e) {
			System.out.println("Erro ao conectar ao banco de dados. Error: " + e);
		} finally {
			//Fecha todas as conexões após o seu uso
			try {
				if (stm != null)
					stm.close();
				if (conexao != null)
					conexao.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}	
		return contas;
	}

	@Override
	public int create(Contas contas) {
		String sql = "INSERT INTO contas (valor, vencimento, situacao) VALUES (?,?,?)";
		//Conexão com o banco de dados
		Connection conexao = null;
		//Preparação de um comando sql
		PreparedStatement stm = null;
		try {
			//obtem conexão com o banco
			conexao = Conexao.getConnection();
			//preparar o comando e seus parâmetros
			stm = (PreparedStatement) conexao.prepareStatement(sql);
			stm.setDouble(1, contas.getValor());
			stm.setString(2, contas.getVencimento());
			stm.setString(3, contas.getSituacao());
			//manda simpleste executar a operação
			stm.execute();		
		} catch(SQLException e) {
			System.out.println("Erro ao conectar ao banco de dados. Error: " + e);
		} finally {
			//Fecha todas as conexões após o seu uso
			try {
				if (stm != null)
					stm.close();
				if (conexao != null)
					conexao.close();
				return 1;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}	
		return 0;
	}

	@Override
	public boolean update(Contas contas, String[] params) {
		String sql = "UPDATE contas SET valor = ?, vencimento = ?, situacao = ? WHERE idcontas = ?";
		//Conexão com o banco de dados
		Connection conexao = null;
		//Preparação de um comando sql
		PreparedStatement stm = null;
		try {
			//obtem conexão com o banco
			conexao = Conexao.getConnection();
			//preparar o comando e seus parâmetros
			stm = (PreparedStatement) conexao.prepareStatement(sql);
			stm.setDouble(1, contas.getValor());
			stm.setString(2, contas.getVencimento());
			stm.setString(3, contas.getSituacao());
			stm.setLong(4, contas.getIdConta());
			//manda simpleste executar a operação
			stm.execute();		
		} catch(SQLException e) {
			System.out.println("Erro ao conectar ao banco de dados. Error: " + e);
		} finally {
			//Fecha todas as conexões após o seu uso
			try {
				if (stm != null)
					stm.close();
				if (conexao != null)
					conexao.close();
				return true;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}	
		return false;
	}

	@Override
	public boolean delete(Contas contas) {
		String sql = "DELETE FROM contas WHERE idcontas = ?";
		//Conexão com o banco de dados
		Connection conexao = null;
		//Preparação de um comando sql
		PreparedStatement stm = null;
		try {
			//obtem conexão com o banco
			conexao = Conexao.getConnection();
			//preparar o comando e seus parâmetros
			stm = (PreparedStatement) conexao.prepareStatement(sql);
			stm.setLong(1, contas.getIdConta());
			//manda simpleste executar a operação
			stm.execute();		
		} catch(SQLException e) {
			System.out.println("Erro ao conectar ao banco de dados. Error: " + e);
		} finally {
			//Fecha todas as conexões após o seu uso
			try {
				if (stm != null)
					stm.close();
				if (conexao != null)
					conexao.close();
				return true;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}	
		return false;
	}
}
