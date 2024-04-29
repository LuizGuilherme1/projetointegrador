package model.dao;

import java.util.List;

import model.entites.Symptons;

public interface SymptonsDao {
	List<Symptons> findAll();
	List<Symptons> findCid();
	List<Symptons> search(String s);
}
