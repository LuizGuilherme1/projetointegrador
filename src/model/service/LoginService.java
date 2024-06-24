package model.service;

import model.dao.LoginDao;

import java.util.List;

import model.dao.DaoFactory;
import model.entites.Usuario;

public class LoginService {
	private LoginDao dao = DaoFactory.createLoginDao();
	
	public boolean validate(Usuario login) {
		return dao.velidate(login);
	}
	
	public List<Usuario> findAll(){
		return dao.findAll();
	}
	
	public List<Usuario> findByName(String name){
		return dao.findByName(name);
	}
	
	public Usuario instantiate(Usuario user) {
		return dao.instatiate(user);
	}
	
	public void remove(Usuario obj) {
		dao.deleteById(obj.getId());
	}

	public void saveOrUpdate(Usuario entity, Usuario user) {
		if(entity.getId() == null) {
			dao.insert(entity, user);
		}else {
			dao.edit(entity);
		}
	}

}
