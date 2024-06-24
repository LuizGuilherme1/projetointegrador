package model.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import db.DB;
import db.DbException;
import model.entites.Usuario;

public interface LoginDao {
	boolean velidate(Usuario user);
	Usuario instatiate(Usuario user);
	boolean checkPermision(Usuario user);
	void insert(Usuario u, Usuario user);
	void edit(Usuario u);
	void deleteById(int id);
	List<Usuario> findAll();
	List<Usuario> findByName(String name);
}
