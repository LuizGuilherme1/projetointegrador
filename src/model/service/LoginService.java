package model.service;

import model.dao.LoginDao;
import model.dao.DaoFactory;
import model.entites.Usuario;

public class LoginService {
	private LoginDao dao = DaoFactory.createLoginDao();
	
	public boolean validate(Usuario login) {
		return dao.velidate(login);
	}
	
	public Usuario instantiateId(Usuario user) {
		return dao.instatiateId(user);
	}

}
