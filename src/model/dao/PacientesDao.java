package model.dao;

import java.util.List;

import model.entites.Pacientes;
import model.entites.Usuario;

public interface PacientesDao {
	
	void insert(Pacientes p, Usuario user);
	void edit(Pacientes p);
	void deleteById(int id);
	List<Pacientes> findByName(String name, Usuario user);
	List<Pacientes> findAll(Usuario user);
	void evolucaoEdit(Pacientes p);
	
}
