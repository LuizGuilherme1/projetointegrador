package gui;

import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

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
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import model.entites.Pacientes;
import model.entites.Usuario;
import model.exceptions.ValidationException;
import model.service.PacienteService;

public class AddController implements Initializable{
	private Pacientes entity;
	
	private Usuario user;
	
	private PacienteService service;
	
	private List<DataChangeListener> dataChangeListeners = new ArrayList<>();
	
	@FXML
	private TextField txId;
	
	@FXML
	private TextField txName;
	
	@FXML
	private TextField txIdade;
	
	@FXML
	private DatePicker dpBirthdate;
	
	@FXML
	private ComboBox<String> comboBoxSex;
	
	@FXML
	private TextField txCns;
	
	@FXML
	private TextField txCpf;
	
	@FXML
	private TextField txRg;
	
	@FXML
	private TextField txCep;
	
	@FXML
	private TextField txEndereco;
	
	@FXML
	private TextField txComplemento;
	
	@FXML
	private Button btSave;

	@FXML
	private Button btCancel;
	
	@FXML
	private Label labelErrorName;
	
	@FXML
	private Label labelErrorIdade;
	
	@FXML
	private Label labelErrorBirthdate;
	
	@FXML
	private Label labelErrorSex;
	
	@FXML
	private Label labelErrorCpf;
	
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
	
	public void setPaciente(Pacientes entity) {
		this.entity = entity;
	}
	
	public void setUser(Usuario user) {
		this.user = user;
	}
	
	public void setService(PacienteService service) {
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
	
	public void updateFormData() {
		if (entity == null) {
			throw new IllegalStateException("Entity was null");
		}
		txId.setText(String.valueOf(entity.getId()));
		txName.setText(entity.getName());
		txIdade.setText(String.valueOf(entity.getIdade()));
		txCns.setText(entity.getCns());
		txCpf.setText(entity.getCpf());
		txRg.setText(entity.getRg());
		txCep.setText(entity.getCep());
		txEndereco.setText(entity.getEndereco());
		txComplemento.setText(entity.getComplemento());
		Locale.setDefault(Locale.US);
		
		Date birthDate = entity.getBirthdate();
		if (birthDate != null) {
			java.util.Date utilDate = new java.util.Date(entity.getBirthdate().getTime());
			dpBirthdate.setValue(LocalDate.ofInstant(utilDate.toInstant(), ZoneId.systemDefault()));
		}
		if(entity.getSex() == null) {
			comboBoxSex.getSelectionModel().selectFirst();
		}else {
			comboBoxSex.setValue(entity.getSex());
		}
		comboBoxSex.setValue(entity.getSex());
	}

	private Pacientes getFormData() {
		Pacientes obj = new Pacientes();
		
		ValidationException exception = new ValidationException("Validation error");
		
		obj.setId(Utils.tryParseToInt(txId.getText()));
		
		if(txName.getText() == null || txName.getText().trim().equals("")) {
			exception.addError("name", "Field can't be empty");
		}
		obj.setName(txName.getText());
		
		if(txIdade.getText() == null || txIdade.getText().trim().equals("")) {
			exception.addError("idade", "Field can't be empty");
		}
		obj.setIdade(Utils.tryParseToInt(txIdade.getText()));
		
		if(dpBirthdate.getValue()==null){
			exception.addError("birthdate", "Field can't be empty");
		}else {
			Instant instant = Instant.from(dpBirthdate.getValue().atStartOfDay(ZoneId.systemDefault()));
			obj.setBirthdate(Date.from(instant));
		}
		
		obj.setSex(comboBoxSex.getValue());
		
		obj.setCns(txCns.getText());
		
		if(txCpf.getText() == null || txCpf.getText().trim().equals("")) {
			exception.addError("cpf", "Field can't be empty");
		}
		obj.setCpf(txCpf.getText());
		
		obj.setRg(txRg.getText());
		
		obj.setCep(txCep.getText());
		
		obj.setEndereco(txEndereco.getText());
		
		obj.setComplemento(txComplemento.getText());
		
		if (exception.getErrors().size() > 0) {
			throw exception;
		}
		
		return obj;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		initializeNodes();
	}

	private void initializeNodes() {
		Constraints.setTextFieldInteger(txId);
		Constraints.setTextFieldInteger(txIdade);
		Utils.formatDatePicker(dpBirthdate, "dd/MM/yyyy");
		
		initializeComboBoxDepartment();
		
	}
	
	public void loadAssociatedObjects() {
		comboBoxSex.setItems(FXCollections.observableArrayList(new String("Masculino"),
				new String("Feminino"), new String("Outro")));
	}

	private void initializeComboBoxDepartment() {
		comboBoxSex.setItems(FXCollections.observableArrayList(new String("Masculino"),
				new String("Feminino"), new String("Outro")));
	}
	
	private void setErrorMessages(Map<String, String> errors) {
		Set<String> fields = errors.keySet();

		labelErrorName.setText((fields.contains("paciente_name") ? errors.get("paciente_name") : ""));
		labelErrorIdade.setText((fields.contains("idade") ? errors.get("idade") : ""));
		labelErrorBirthdate.setText((fields.contains("data_nascimento") ? errors.get("birthdate") : ""));
		labelErrorSex.setText((fields.contains("sexo") ? errors.get("sexo") : ""));
		labelErrorCpf.setText((fields.contains("cpf") ? errors.get("cpf") : ""));
	}
	
}
