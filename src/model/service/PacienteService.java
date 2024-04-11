package model.service;

import java.util.List;

import model.dao.DaoFactory;
import model.dao.PacientesDao;
import model.entites.Pacientes;

public class PacienteService {
	private PacientesDao dao = DaoFactory.createPacientesDao();
	
	public List<Pacientes> findAll(){
		return dao.findAll();
	}
	
	public void saveOrUpdate(Pacientes obj) {
		if(obj.getId() == null) {
			dao.insert(obj);
		}else {
			dao.edit(obj);
		}
	}
	
	public void remove(Pacientes obj) {
		dao.deleteById(obj.getId());
	}
}
