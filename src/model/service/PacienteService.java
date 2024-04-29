package model.service;

import java.util.List;

import model.dao.DaoFactory;
import model.dao.PacientesDao;
import model.entites.Pacientes;
import model.entites.Usuario;

public class PacienteService {
	private PacientesDao dao = DaoFactory.createPacientesDao();
	
	public List<Pacientes> findAll(Usuario user){
		return dao.findAll(user);
	}
	
	public void saveOrUpdate(Pacientes obj, Usuario user) {
		if(obj.getId() == null) {
			dao.insert(obj, user);
		}else {
			dao.edit(obj);
		}
	}
	
	public void remove(Pacientes obj) {
		dao.deleteById(obj.getId());
	}
}
