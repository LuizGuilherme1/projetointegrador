package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import db.DB;
import db.DbException;
import model.dao.LoginDao;
import model.entites.Pacientes;
import model.entites.Usuario;

public class LoginDaoJDBC implements LoginDao{
	private Connection conn;
	
	public LoginDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	private Usuario instantiateUser(ResultSet rs, Usuario obj) throws SQLException {
		obj.setId(rs.getInt("id"));
		obj.setKey(rs.getString("acess"));
		return obj;
	}
	
	@Override
	public boolean velidate(Usuario obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("SELECT * FROM usuario "
					+ "WHERE email = ? and senha = ?");
			st.setString(1, obj.getEmail());
			st.setString(2, obj.getSenha());
			
			ResultSet rs = st.executeQuery();
			if (rs.next()) {
				return true;
			}
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		}finally {
			DB.closeStatement(st);
		}
		return false;
		
	}

	@Override
	public Usuario instatiate(Usuario obj) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"SELECT * FROM usuario "
					+"WHERE email = ? and senha = ?");
			st.setString(1, obj.getEmail());
			st.setString(2, obj.getSenha());
			rs = st.executeQuery();
			if(rs.next()) {
			   instantiateUser(rs, obj);
			   return obj;
			}
			return null;
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

	@Override
	public boolean checkPermision(Usuario user) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("SELECT * FROM usuario "
					+ "WHERE id = ?");
			st.setInt(1, user.getId());
			
			ResultSet rs = st.executeQuery();
			if (rs.next() && user.getKey()=="True") {
				return true;
			}
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		}finally {
			DB.closeStatement(st);
		}
		return false;
	}
	
	@Override
	public void deleteById(int id) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("DELETE FROM usuario WHERE id = ?");
			
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
	
	private Usuario instantiateNewUser(ResultSet rs) throws SQLException {
		Usuario obj = new Usuario();
		obj.setId(rs.getInt("id"));
		obj.setName(rs.getString("nome"));
		obj.setEmail(rs.getString("email"));
		obj.setSenha(rs.getString("senha"));
		obj.setKey(rs.getString("acess"));
		return obj;
	}

	@Override
	public void insert(Usuario u, Usuario user) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"INSERT INTO usuario "
					+ "(nome, email, senha, acess) "
					+ "VALUES "
					+ "(?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
			
			st.setString(1, u.getName());
			st.setString(2, u.getEmail());
			st.setString(3, u.getSenha());
			st.setString(4, u.getKey());
			
            int rowsAffected = st.executeUpdate();
			
			if (rowsAffected > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if (rs.next()) {
					int id = rs.getInt(1);
					u.setId(id);
				}
				DB.closeResultSet(rs);
			}else {
				throw new DbException("Unexpected error! No rows affected!");
			}
			
		}catch (SQLException e) {
			throw new DbException(e.getMessage());
		}finally {
			DB.closeStatement(st);
		}
		
	}

	@Override
	public void edit(Usuario u) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"update usuario "
					+ "set nome = ?, email = ?, senha = ?, acess = ? "
					+ "where id = ?" );
			st.setString(1, u.getName());
			st.setString(2, u.getEmail());
			st.setString(3, u.getSenha());
			st.setString(4, u.getKey());
			st.setInt(5, u.getId());
			
			st.executeUpdate();
			
		}catch (SQLException e) {
			throw new DbException(e.getMessage());
		}finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public List<Usuario> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"SELECT * FROM usuario "
					+ "ORDER BY nome");
			
            rs = st.executeQuery();
			
			List<Usuario> list = new ArrayList<>();
			
			while (rs.next()) {
				Usuario obj = instantiateNewUser(rs);
				list.add(obj);
			}
			return list;
			
		}catch (SQLException e) {
			throw new DbException(e.getMessage());
		}finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

	@Override
	public List<Usuario> findByName(String name) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"SELECT * "
					+ "FROM usuario "
					+ "WHERE nome LIKE ?");
			
			st.setString(1, name);
			rs = st.executeQuery();
			
            List<Usuario> list = new ArrayList<>();
			
			while (rs.next()) {
				Usuario obj = instantiateNewUser(rs);
				list.add(obj);
			}
			return list;
			
		}catch (SQLException e) {
			throw new DbException(e.getMessage());
		}finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}
	
	
	
	
}
