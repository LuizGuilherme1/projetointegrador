package gui;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
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
import model.entites.Symptons;
import model.entites.Usuario;
import model.service.PacienteService;

public class PacientesController implements Initializable, DataChangeListener{

	private PacienteService service = new PacienteService();
	
	private Usuario user;
	
	@FXML
	private TableView<Pacientes> tvPacientes;
	
	@FXML
	private TableColumn<Pacientes, String> tcName;
	
	@FXML
	private TableColumn<Pacientes, Integer> tcIdade;
	
	@FXML
	private TableColumn<Pacientes, Date> tcBirthdate;
	
	@FXML
	private TableColumn<Pacientes, String> tcSexo;
	
	@FXML
	private TableColumn<Pacientes, String> tcCns;
	
	@FXML
	private TableColumn<Pacientes, String> tcCpf;
	
	@FXML
	private TableColumn<Pacientes, String> tcRg;
	
	@FXML
	private TableColumn<Pacientes, String> tcCep;
	
	@FXML
	private TableColumn<Pacientes, String> tcEndereço;
	
	@FXML
	private TableColumn<Pacientes, String> tcComplemento;
	
	@FXML
	private TableColumn<Pacientes, Pacientes> tcEdit;
	
	@FXML
	private TableColumn<Pacientes, Pacientes> tcDelete;
	
	@FXML
	private TableColumn<Pacientes, Pacientes> tcEvolucao;
	
	@FXML
	private TextField txName;
	
	@FXML
	private Button btSintomas;
	
	@FXML
	private Button btCadastro;
	
	@FXML
	private Button btPesquisa;
	
	@FXML
	private Button btAjuda;
	
	@FXML
	private Button btUsuarios;
	
	private ObservableList<Pacientes> obsList;
	
	@FXML
	public void btActionUsuarios(ActionEvent event) {
		try {
			  if(user.getKey() == "True") {
				  btUsuarios.setVisible(true);
				  FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/Usuarios.fxml"));
				  ScrollPane scrollpane = loader.load();
				
				  scrollpane.setFitToHeight(true);
				  scrollpane.setFitToWidth(true);
				
				  Stage parentStage = Utils.currentStage(event);
				  Scene scene = Main.getMainScene();
				  scene = new Scene(scrollpane);
				  parentStage.setScene(scene);
				  parentStage.setTitle("Usuarios");
				  parentStage.show();
				
				  UsuariosController controller = loader.getController();
				  controller.updateTableView(user);
			  }else {
				  btUsuarios.setVisible(false);
			  }
			  
			}catch (IOException e) {
				e.printStackTrace();
			}
	}
	
	@FXML
	public void btActionCadastro(ActionEvent event) {
		Stage parentStage = Utils.currentStage(event);
		Pacientes obj = new Pacientes();
		createDialogForm(obj, "/gui/Add.fxml", parentStage);
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
	public void btActionPesquisa() {
		if (service == null) {
			throw new IllegalStateException("Service was null");
		}
		if(txName.getText()==null||txName.getText().trim().equals("")){
		    List<Pacientes> list = service.findAll(user);
		    obsList = FXCollections.observableArrayList(list);
		    tvPacientes.setItems(obsList);
		}else {
			String s = ("%"+txName.getText()+"%");
			List<Pacientes> list = service.findByName(s,user);
			obsList = FXCollections.observableArrayList(list);
			tvPacientes.setItems(obsList);
		}
	}
	
	@FXML
	public void btActionAjuda() {
		Alerts.showAlert("Sobre", "uma pagina para esplicar o que os botoes fazem", 
				"Sintomas redireciona para a pagina de sintomas, Cadastro adiciona um novo paciente, "
				+ "pesquisa acha um paciente por id, Ajuda ver Sobre, Edit permit editar os dados de um paciente "
				+ "e Delete deleta um paciente, "
				+ "Buscar acha os Pacientes com o mesmo nome, caso "
				+ " estiver vasio ele retorna todos os pacientes ", AlertType.INFORMATION);
	}
	
	public void setPacienteServices(PacienteService service) {
		this.service = service;
	}
	
	private void createDialogForm(Pacientes obj, String absoluteName, Stage parentStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			Pane pane = loader.load();

			AddController controller = loader.getController();
			controller.setPaciente(obj);
			controller.setUser(user);
			controller.setService(new PacienteService());
			controller.loadAssociatedObjects();
			controller.subscribeDataChangeListener(this);
			controller.updateFormData();

			Stage dialogStage = new Stage();
			dialogStage.setTitle("enter Paciente data");
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
	
	public void passUser(Usuario user) {
		this.user = user;
	}
	
	public void updateTableView(Usuario user) {
		passUser(user);
		if (service == null) {
			throw new IllegalStateException("Service was null");
		}
		List<Pacientes> list = service.findAll(user);
		obsList = FXCollections.observableArrayList(list);
		tvPacientes.setItems(obsList);
		initEditButtons();
		initDeleteButtons();
		initEvolucaoButtons();
	}
	
	private void initEvolucaoButtons() {
		//TODO
		tcEvolucao.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tcEvolucao.setCellFactory(param -> new TableCell<Pacientes, Pacientes>(){
			private final Button button = new Button("Evolução");
			@Override
			protected void updateItem(Pacientes obj, boolean empty) {
				super.updateItem(obj, empty);
				if (obj == null) {
					setGraphic(null);
					return;
				}
				setGraphic(button);
				button.setOnAction(
						event -> createEvoluçao(obj, "/gui/Evolucao.fxml", Utils.currentStage(event)));
			}
			
		});
	}
	
	private void createEvoluçao(Pacientes obj, String absoluteName, Stage parentStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			Pane pane = loader.load();

			EvolucaoController controller = loader.getController();
			controller.setPaciente(obj);
			controller.setUser(user);
			controller.setService(new PacienteService());
			controller.subscribeDataChangeListener(this);
			controller.updateFormData();

			Stage dialogStage = new Stage();
			dialogStage.setTitle("enter Paciente data");
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
	
	
	private void initEditButtons() {
		tcEdit.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tcEdit.setCellFactory(param -> new TableCell<Pacientes, Pacientes>() {
			private final Button button = new Button("Edit");

			@Override
			protected void updateItem(Pacientes obj, boolean empty) {
				super.updateItem(obj, empty);
				if (obj == null) {
					setGraphic(null);
					return;
				}
				setGraphic(button);
				button.setOnAction(
						event -> createDialogForm(obj, "/gui/Add.fxml", Utils.currentStage(event)));
			}
		});
	}
	
	private void initDeleteButtons() {
		tcDelete.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tcDelete.setCellFactory(param -> new TableCell<Pacientes, Pacientes>() {
			private final Button button = new Button("Remove");

			@Override
			protected void updateItem(Pacientes obj, boolean empty) {
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

	private void removeEntity(Pacientes obj) {
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
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();
	}
	
	private void initializeNodes() {
		tcName.setCellValueFactory(new PropertyValueFactory<>("name"));
		tcIdade.setCellValueFactory(new PropertyValueFactory<>("idade"));
		tcBirthdate.setCellValueFactory(new PropertyValueFactory<>("birthdate"));
		Utils.formatTableColumnDate(tcBirthdate, "dd/MM/yyyy");
		tcSexo.setCellValueFactory(new PropertyValueFactory<>("sex"));
		tcCns.setCellValueFactory(new PropertyValueFactory<>("cns"));
		tcCpf.setCellValueFactory(new PropertyValueFactory<>("cpf"));
		tcRg.setCellValueFactory(new PropertyValueFactory<>("rg"));
		tcCep.setCellValueFactory(new PropertyValueFactory<>("cep"));
		tcEndereço.setCellValueFactory(new PropertyValueFactory<>("endereco"));
		tcComplemento.setCellValueFactory(new PropertyValueFactory<>("complemento"));
		
	}

	@Override
	public void onDataChanged() {
		updateTableView(user);
		
	}

}
