package model.dao;

import model.entites.Usuario;

public interface LoginDao {
	boolean velidate(Usuario user);
	Usuario instatiateId(Usuario user);
}
