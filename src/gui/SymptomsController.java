package gui;

import java.io.IOException;

import application.Main;
import gui.util.Alerts;
import gui.util.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import model.service.SymptonsService;
import model.entites.Symptons;

public class SymptomsController {
	
	private SymptonsService service;
	
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
	
	@FXML
	public void onBtActionPacientes(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/Pacientes.fxml"));
			ScrollPane scrollpane = loader.load();
			
			scrollpane.setFitToHeight(true);
			scrollpane.setFitToWidth(true);
			
			Stage parentStage = Utils.currentStage(event);
			Scene scene = Main.getMainScene();
			scene = new Scene(scrollpane);
			parentStage.setScene(scene);
			parentStage.setTitle("pacientes");
			parentStage.show();
			
			PacientesController controller = loader.getController();
			controller.updateTableView();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
