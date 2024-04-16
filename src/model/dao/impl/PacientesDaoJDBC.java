package model.dao.impl;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import java.sql.Statement;

import db.DB;
import db.DbException;
import model.dao.PacientesDao;
import model.entites.Pacientes;

public class PacientesDaoJDBC implements PacientesDao{
	
private Connection conn;
	
	public PacientesDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Pacientes p) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"INSERT INTO pacientes "
					+ "(paciente_name, idade, data_nascimento, sexo, cns, cpf, rg, cep, endereço, complemento) "
					+ "VALUES "
					+ "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);
			st.setString(1, p.getName());
			st.setInt(2, p.getIdade());
			st.setDate(3, new java.sql.Date(p.getBirthdate().getTime()));
			st.setString(4, p.getSex());
			st.setString(5, p.getCns());
			st.setString(6, p.getCpf());
			st.setString(7, p.getRg());
			st.setString(8, p.getCep());
			st.setString(9, p.getEndereco());
			st.setString(10, p.getComplemento());
			
            int rowsAffected = st.executeUpdate();
			
			if (rowsAffected > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if (rs.next()) {
					int id = rs.getInt(1);
					p.setId(id);
				}
				DB.closeResultSet(rs);
			}else {
				throw new DbException("Unexpected error! No rows affected!");
			}
			
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		}finally {
			DB.closeStatement(st);
		 }
	}	

	@Override
	public void edit(Pacientes p) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"update pacientes "
					+ "set paciente_name = ?, idade = ?, data_nascimento = ?, "
					+ "sexo = ?, cns = ?, cpf = ?, rg = ?, cep = ?, endereço = ?, complemento = ? "
					+ "where id = ?");
			st.setString(1, p.getName());
			st.setInt(2, p.getIdade());
			st.setDate(3, new java.sql.Date(p.getBirthdate().getTime()));
			st.setString(4, p.getSex());
			st.setString(5, p.getCns());
			st.setString(6, p.getCpf());
			st.setString(7, p.getRg());
			st.setString(8, p.getCep());
			st.setString(9, p.getEndereco());
			st.setString(10, p.getComplemento());
			st.setInt(11, p.getId());
			
			st.executeUpdate();
			
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		}finally {
			DB.closeStatement(st);
		}
		
	}

	@Override
	public void deleteById(int id) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("DELETE FROM pacientes WHERE paciente_id = ?");
			
			st.setInt(1, id);
			
			st.executeUpdate();
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public Pacientes findById(int id) {
		//TODO change id to cpf
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"SELECT * "
					+ "FROM pacientes "
					+ "WHERE paciente_id = ?");
			
			st.setInt(1, id);
			rs = st.executeQuery();
			if (rs.next()) {
				Pacientes obj = instantiatePacientes(rs);
				return obj;
			}
			return null;
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}
	
	private Pacientes instantiatePacientes(ResultSet rs) throws SQLException {
		Pacientes obj = new Pacientes();
		obj.setId(rs.getInt("paciente_id"));
		obj.setName(rs.getString("paciente_name"));
		obj.setIdade(rs.getInt("idade"));
		obj.setBirthdate(rs.getDate("data_nascimento"));
		obj.setSex(rs.getString("sexo"));
		obj.setCns(rs.getString("cns"));
		obj.setCpf(rs.getString("cpf"));
		obj.setRg(rs.getString("rg"));
		obj.setCep(rs.getString("cep"));
		obj.setEndereco(rs.getString("endereço"));
		obj.setComplemento(rs.getString("complemento"));
		return obj;
	}
	
	@Override
	public List<Pacientes> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"SELECT * FROM projetointegrador.pacientes "
					+ "ORDER BY paciente_name");
			
			rs = st.executeQuery();
			
			List<Pacientes> list = new ArrayList<>();
			
			while (rs.next()) {
				Pacientes obj = instantiatePacientes(rs);
				list.add(obj);
			}
			return list;
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}
	
}
