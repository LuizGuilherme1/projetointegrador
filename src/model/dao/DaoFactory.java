package model.dao;

import db.DB;
import model.dao.impl.LoginDaoJDBC;
import model.dao.impl.PacientesDaoJDBC;
import model.dao.impl.SymptonsDaoJDBC;

public class DaoFactory {
	public static PacientesDao createPacientesDao() {
		return new PacientesDaoJDBC(DB.getConnection());
	}
	
	public static LoginDao createLoginDao() {
		return new LoginDaoJDBC(DB.getConnection());
	}
	
	public static SymptonsDao createSymptonsDao() {
		return new SymptonsDaoJDBC(DB.getConnection());
	}
}
