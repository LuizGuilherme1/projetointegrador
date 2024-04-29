package model.dao;

import java.util.List;

import model.entites.Pacientes;
import model.entites.Usuario;

public interface PacientesDao {
	
	void insert(Pacientes p, Usuario user);
	void edit(Pacientes p);
	void deleteById(int id);
	Pacientes findById(int id);
	List<Pacientes> findAll(Usuario user);
	
}
