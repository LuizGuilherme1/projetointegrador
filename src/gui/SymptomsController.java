package gui;

import gui.util.Alerts;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import model.entites.Symptons;

public class SymptomsController {
	
	private SymptomsService service;
	
	@FXML
	private TableView<Symptons> tvSymptoms;
	
	@FXML
	private TableColumn<Symptons, String> tcId;
	
	@FXML
	private TableColumn<Symptons, String> tcName;
	
	@FXML
	private TableColumn<Symptons, String> tcDescS;
	
	@FXML
	private Button btPacientes;
	
	@FXML
	private Button btAjuda;
	
	@FXML
	public void onBtActionAjuda() {
		Alerts.showAlert("Sobre", "uma pagina para esplicar o que os botoes fazem", 
				"Pacientes redireciona para a pagina de Pacientes, "
				+ "Ajuda ver Sobre", AlertType.INFORMATION);
	}
}
