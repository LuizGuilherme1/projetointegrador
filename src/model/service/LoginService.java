package model.service;

import model.dao.LoginDao;
import model.dao.DaoFactory;
import model.entites.Usuario;

public class LoginService {
	private LoginDao dao = DaoFactory.createLoginDao();
	
	public boolean validate(Usuario cadastro) {
		return dao.velidate(cadastro);
	}

}
