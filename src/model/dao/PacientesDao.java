package model.dao;

import java.util.List;

import model.entites.Pacientes;

public interface PacientesDao {
	
	void insert(Pacientes p);
	void edit(Pacientes p);
	void deleteById(int id);
	Pacientes findById(int id);
	List<Pacientes> findAll();
	
}
