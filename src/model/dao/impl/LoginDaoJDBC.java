package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import db.DB;
import db.DbException;
import model.dao.LoginDao;
import model.entites.Usuario;

public class LoginDaoJDBC implements LoginDao{
	private Connection conn;
	
	public LoginDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	private Usuario instantiateUser(ResultSet rs, Usuario obj) throws SQLException {
		obj.setId(rs.getInt("id"));
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
	public Usuario instatiateId(Usuario obj) {
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
	
	
	
	
}
