package gui;

import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import db.DbException;
import gui.util.Alerts;
import gui.util.Constraints;
import gui.util.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import model.entites.Pacientes;
import model.exceptions.ValidationException;
import model.service.PacienteService;

public class AddController implements Initializable{
	private Pacientes entity;
	
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
			service.saveOrUpdate(entity);
			notifyDataChangeListeners();
			Utils.currentStage(event).close();
			
		} catch (DbException e) {
			Alerts.showAlert("Error Saving object", null, e.getMessage(), AlertType.ERROR);
		}
	}
	
	public void setPaciente(Pacientes entity) {
		this.entity = entity;
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
		if (entity.getBirthdate() != null) {
			dpBirthdate.setValue(LocalDate.ofInstant(entity.getBirthdate().toInstant(), ZoneId.systemDefault()));
		}
		if(entity.getGender() == null) {
			comboBoxSex.getSelectionModel().selectFirst();
		}else {
			comboBoxSex.setValue(entity.getGender());
		}
		comboBoxSex.setValue(entity.getGender());
	}

	private Pacientes getFormData() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		initializeNodes();
	}

	private void initializeNodes() {
		Constraints.setTextFieldInteger(txIdade);
		Utils.formatDatePicker(dpBirthdate, "dd/MM/yyyy");
		initializeComboBoxDepartment();
		
	}

	private void initializeComboBoxDepartment() {
		//TODO
		
	}
	
}
