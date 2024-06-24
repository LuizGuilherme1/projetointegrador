package gui;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import application.Main;
import db.DbIntegrityException;
import gui.util.Alerts;
import gui.util.Utils;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.entites.Pacientes;
import model.entites.Usuario;
import model.service.LoginService;
import model.service.PacienteService;

public class UsuariosController implements Initializable, DataChangeListener{
	private LoginService service = new LoginService();
	private Usuario user;
	
	@FXML
	private TableView<Usuario> tvUser;
	
	@FXML
	private TableColumn<Usuario, String> tcName;
	
	@FXML
	private TableColumn<Usuario, String> tcEmail;
	
	@FXML
	private TableColumn<Usuario, String> tcKey;
	
	@FXML
	private TableColumn<Usuario, Usuario> tcEdit;
	
	@FXML
	private TableColumn<Usuario, Usuario> tcDelet;
	
	@FXML
	private TextField txName;
	
	@FXML
	private Button btPesquisa;
	
	@FXML
	private Button btAjuda;
	
	@FXML
	private Button btCadastro;
	
	@FXML
	private Button btSintomas;
	
	@FXML
	private Button btPacientes;
	
	private ObservableList<Usuario> obsList;
	
	@FXML
	public void btActionCadastro(ActionEvent event) {
		Stage parentStage = Utils.currentStage(event);
		Usuario obj = new Usuario();
		createDialogForm(obj, "/gui/AddUser.fxml", parentStage);
	}
	
	@FXML
	public void btActionSintomas(ActionEvent event) {
		try {
		  FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/Symptoms.fxml"));
		  ScrollPane scrollpane = loader.load();
		
		  scrollpane.setFitToHeight(true);
		  scrollpane.setFitToWidth(true);
		
		  Stage parentStage = Utils.currentStage(event);
		  Scene scene = Main.getMainScene();
		  scene = new Scene(scrollpane);
		  parentStage.setScene(scene);
		  parentStage.setTitle("Sintomas");
		  parentStage.show();
		
		  SymptomsController controller = loader.getController();
		  controller.updateTableView(user);
		  controller.loadAssociatedObjects();
		  
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	public void btActionPaciente(ActionEvent event) {
		try {
		  FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/Pacientes.fxml"));
		  ScrollPane scrollpane = loader.load();
		
		  scrollpane.setFitToHeight(true);
		  scrollpane.setFitToWidth(true);
		
		  Stage parentStage = Utils.currentStage(event);
		  Scene scene = Main.getMainScene();
		  scene = new Scene(scrollpane);
		  parentStage.setScene(scene);
		  parentStage.setTitle("Paciente");
		  parentStage.show();
		
		  PacientesController controller = loader.getController();
		  controller.updateTableView(user);
		  
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	public void btActionPesquisa() {
		if (service == null) {
			throw new IllegalStateException("Service was null");
		}
		if(txName.getText()==null||txName.getText().trim().equals("")){
		    List<Usuario> list = service.findAll();
		    obsList = FXCollections.observableArrayList(list);
		    tvUser.setItems(obsList);
		}else {
			String s = ("%"+txName.getText()+"%");
			List<Usuario> list = service.findByName(s);
			obsList = FXCollections.observableArrayList(list);
			tvUser.setItems(obsList);
		}
	}
	
	@FXML
	public void btActionAjuda() {
		//TODO
		Alerts.showAlert("Sobre", "uma pagina para esplicar o que os botoes fazem", 
				"Sintomas redireciona para a pagina de sintomas, Cadastro adiciona um novo paciente, "
				+ "pesquisa acha um paciente por id, Ajuda ver Sobre, Edit permit editar os dados de um paciente "
				+ "e Delete deleta um paciente.", AlertType.INFORMATION);
	}
	
	public void setLoginServices(LoginService service) {
		this.service = service;
	}
	
	public void passUser(Usuario user) {
		this.user = user;
	}
	
	private void createDialogForm(Usuario obj, String absoluteName, Stage parentStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			Pane pane = loader.load();

			AddUserController controller = loader.getController();
			controller.setEntity(obj);
			controller.setUser(user);
			controller.setService(new LoginService());
			controller.loadAssociatedObjects();
			controller.subscribeDataChangeListener(this);
			controller.updateFormData();

			Stage dialogStage = new Stage();
			dialogStage.setTitle("enter Usuario data");
			dialogStage.setScene(new Scene(pane));
			dialogStage.setResizable(false);
			dialogStage.initOwner(parentStage);
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.showAndWait();

		} catch (IOException e) {
			e.printStackTrace();
			Alerts.showAlert("IOException", "error loading view", e.getMessage(), AlertType.ERROR);
		}
	}
	
	public void updateTableView(Usuario user) {
		passUser(user);
		if (service == null) {
			throw new IllegalStateException("Service was null");
		}
		List<Usuario> list = service.findAll();
		obsList = FXCollections.observableArrayList(list);
		tvUser.setItems(obsList);
		initEditButtons();
		initDeleteButtons();
	}
	
	private void initEditButtons() {
		tcEdit.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tcEdit.setCellFactory(param -> new TableCell<Usuario, Usuario>() {
			private final Button button = new Button("edit");

			@Override
			protected void updateItem(Usuario obj, boolean empty) {
				super.updateItem(obj, empty);
				if (obj == null) {
					setGraphic(null);
					return;
				}
				setGraphic(button);
				button.setOnAction(
						event -> createDialogForm(obj, "/gui/AddUser.fxml", Utils.currentStage(event)));
			}
		});
	}
	
	private void initDeleteButtons() {
		tcDelet.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tcDelet.setCellFactory(param -> new TableCell<Usuario, Usuario>() {
			private final Button button = new Button("remove");

			@Override
			protected void updateItem(Usuario obj, boolean empty) {
				super.updateItem(obj, empty);
				if (obj == null) {
					setGraphic(null);
					return;
				}
				setGraphic(button);
				button.setOnAction(event -> removeEntity(obj));
			}
		});
	}

	private void removeEntity(Usuario obj) {
		Optional<ButtonType> result = Alerts.showConfirmation("Confirme", "Voce quer deletar esse Paciente ?");
		if (result.get() == ButtonType.OK) {
			if (service == null) {
				throw new IllegalStateException("Service was null");
			}
			try {
				service.remove(obj);
				updateTableView(user);
			} catch (DbIntegrityException e) {
				Alerts.showAlert("Error Removing Object", null, e.getMessage(), AlertType.ERROR);
			}
		}
	}
	
	
	@Override
	public void onDataChanged() {
		updateTableView(user);
		
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		initializeNodes();
		
	}
	
	private void initializeNodes() {
		tcName.setCellValueFactory(new PropertyValueFactory<>("name"));
		tcEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
		tcKey.setCellValueFactory(new PropertyValueFactory<>("key"));
		
	}
	
}
