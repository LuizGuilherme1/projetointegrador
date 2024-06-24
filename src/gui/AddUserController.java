package gui;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import db.DB;
import db.DbException;
import gui.util.Alerts;
import gui.util.Constraints;
import gui.util.Utils;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import model.entites.Usuario;
import model.exceptions.ValidationException;
import model.service.LoginService;
import model.service.PacienteService;

public class AddUserController implements Initializable{
	
	private Usuario user;
	
	private Usuario entity;
	
	private LoginService service;
	
	private List<DataChangeListener> dataChangeListeners = new ArrayList<>();
	
	@FXML
	private TextField txId;
	
	@FXML
	private TextField txName;
	
	@FXML
	private TextField txEmail;
	
	@FXML
	private TextField txPassword;
	
	@FXML
	private ComboBox<String> comboKey;
	
	@FXML
	private Label labelErrorName;
	
	@FXML
	private Label labelErrorEmail;
	
	@FXML
	private Label labelErrorPassword;
	
	@FXML
	private Label labelErrorKey;
	
	@FXML
	private Button btSave;

	@FXML
	private Button btCancel;
	
	@FXML
	public void onBtCancelAction(ActionEvent event) {
		Utils.currentStage(event).close();
	}
	
	@FXML
	public void onActionSave(ActionEvent event) {
		if (entity == null) {
			throw new IllegalStateException("Entity was null");
		}
		if (service == null) {
			throw new IllegalStateException("Service was null");
		}
		try {
			entity = getFormData();
			service.saveOrUpdate(entity,user);
			notifyDataChangeListeners();
			Utils.currentStage(event).close();
			
		}catch (ValidationException e) {
			setErrorMessages(e.getErrors());
		} catch (DbException e) {
			Alerts.showAlert("Error Saving object", null, e.getMessage(), AlertType.ERROR);
		}
	}
	
	private void setErrorMessages(Map<String, String> errors) {
		Set<String> fields = errors.keySet();

		labelErrorName.setText((fields.contains("nome") ? errors.get("name") : ""));
		labelErrorEmail.setText((fields.contains("email") ? errors.get("email") : ""));
		labelErrorPassword.setText((fields.contains("senha") ? errors.get("senha") : ""));
		
	}

	public void updateFormData() {
		if (entity == null) {
			throw new IllegalStateException("Entity was null");
		}
		txId.setText(String.valueOf(entity.getId()));
		txName.setText(entity.getName());
		txEmail.setText(entity.getEmail());
		txPassword.setText(entity.getSenha());
		
		if(entity.getKey() == null) {
			comboKey.getSelectionModel().selectFirst();
		}else {
			comboKey.setValue(entity.getKey());
		}
		comboKey.setValue(entity.getKey());
	}
	
	private Usuario getFormData() {
		Usuario obj = new Usuario();
		
        ValidationException exception = new ValidationException("Validation error");
		
		obj.setId(Utils.tryParseToInt(txId.getText()));
		
		if(txName.getText() == null || txName.getText().trim().equals("")) {
			exception.addError("name", "Field can't be empty");
		}
		obj.setName(txName.getText());
		
		if(txEmail.getText() == null || txEmail.getText().trim().equals("")) {
			exception.addError("email", "Field can't be empty");
		}
		obj.setEmail(txEmail.getText());
		
		if(txPassword.getText() == null || txPassword.getText().trim().equals("")) {
			exception.addError("senha", "Field can't be empty");
		}
		obj.setSenha(txPassword.getText());
		
		obj.setKey(comboKey.getValue());
		
		return obj;
	}

	public void setEntity(Usuario entity) {
		this.entity = entity;
	}
	
	public void setUser(Usuario user) {
		this.user = user;
	}
	
	public void setService(LoginService service) {
		this.service = service;
	}
	
	public void subscribeDataChangeListener(DataChangeListener listener) {
		dataChangeListeners.add(listener);
	}
	
	private void notifyDataChangeListeners() {
		for (DataChangeListener listener : dataChangeListeners) {
			listener.onDataChanged();
		}
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		initializeNodes();
	}

	private void initializeNodes() {
		Constraints.setTextFieldInteger(txId);
		
		initializeComboBoxDepartment();
		
	}
	
	public void loadAssociatedObjects() {
		comboKey.setItems(FXCollections.observableArrayList(new String("False"),
				new String("True")));
	}

	private void initializeComboBoxDepartment() {
		comboKey.setItems(FXCollections.observableArrayList(new String("False"),
				new String("True")));
	}
		
}
