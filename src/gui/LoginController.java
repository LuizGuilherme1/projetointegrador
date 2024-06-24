package gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import model.entites.Usuario;
import model.service.LoginService;
import model.service.PacienteService;

import java.io.IOException;

import application.Main;
import gui.util.*;

public class LoginController {
	private LoginService service = new LoginService();
	
	@FXML
	private Button btCheck;
	
	@FXML
    private TextField txEmail;
	
	@FXML
    private PasswordField txPassword;
	
	public void onBtAction(ActionEvent event) {
			try {
				//
				Usuario user = new Usuario();
				user.setEmail(txEmail.getText());
				user.setSenha(txPassword.getText().toString());
				if (user.getEmail().trim() == ""||user.getSenha().trim() == ""||
						(user.getEmail() == null && user.getSenha() == null)) {
		            Alerts.showAlert("Error", null, "Não pode ser nulo", AlertType.ERROR);
		            return;
		        }
				if (service.validate(user)) {
					service.instantiate(user);
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
					controller.updateTableView(user);
					
				}else {
				    Alerts.showAlert("Error", null, "Este email não existe ou senha incorreta", AlertType.ERROR);
					
				}

			} catch (IOException e) {
				e.printStackTrace();
		    }
	}
}
