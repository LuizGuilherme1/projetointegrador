package model.service;

import java.util.List;

import model.dao.DaoFactory;
import model.dao.SymptonsDao;
import model.entites.Symptons;

public class SymptonsService {
	private SymptonsDao dao = DaoFactory.createSymptonsDao();
	
	public List<Symptons> findAll(){
		return dao.findAll();
	}
	
	public List<Symptons> findCid(){
		return dao.findCid();
	}
	
	public List<Symptons> search(String s){
		return dao.search(s.toString());
	}
}
