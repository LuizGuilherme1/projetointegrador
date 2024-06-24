package gui;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import application.Main;
import gui.util.Alerts;
import gui.util.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import javafx.stage.Stage;
import model.service.SymptonsService;
import model.entites.Symptons;
import model.entites.Usuario;
import model.exceptions.ValidationException;

public class SymptomsController implements Initializable, DataChangeListener{
	private Usuario user;
	
	private SymptonsService service = new SymptonsService();
	
	@FXML
	private TableView<Symptons> tvSymptoms;
	
	@FXML
	private TableColumn<Symptons, String> tcTranstorno;
	
	@FXML
	private TableColumn<Symptons, String> tcCid;
	
	@FXML
	private TableColumn<Symptons, String> tcSintomas_biologicos;
	
	@FXML
	private TableColumn<Symptons, String> tcConsequencias_sociais;
	
	@FXML
	private TableColumn<Symptons, String> tcCaracteristicas;
	
	@FXML
	private Button btPacientes;
	
	@FXML
	private Button btAjuda;
	
	@FXML
	private ComboBox<Symptons> comboCid;
	
	@FXML
	private TextField txSearch;
	
	@FXML
	private Button btSearch;
	
	@FXML
	private Label labelError;
	
	@FXML
	private Button btUsuarios;
	
	private ObservableList<Symptons> obsList;
	
	public void setSymptonsServices(SymptonsService service) {
		this.service = service;
	}
	
	@FXML
	public void btActionUsuarios(ActionEvent event) {
		try {
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
			  
			}catch (IOException e) {
				e.printStackTrace();
			}
	}
	
	@FXML
	public void onBtActionSearch() {
		
		if(txSearch.getText() == null || txSearch.getText().trim().equals("")) {
			List<Symptons> list = service.findAll();
			obsList = FXCollections.observableArrayList(list);
			tvSymptoms.setItems(obsList);
		}else {
			String s = ("%"+txSearch.getText()+"%");
			List<Symptons> list = service.search(s);
			obsList = FXCollections.observableArrayList(list);
			tvSymptoms.setItems(obsList);
		}
		
	}
	
	public void loadAssociatedObjects() {
		if (service == null) {
			throw new IllegalStateException("Service was null");
		}
		List<Symptons> list = service.findCid();
		obsList = FXCollections.observableArrayList(list);
		comboCid.setItems(obsList);
	}
	
	@FXML
	public void onBtActionAjuda() {
		Alerts.showAlert("Sobre", "uma pagina para esplicar o que os botoes fazem", 
				"Pacientes redireciona para a pagina de Pacientes, "
				+ "Ajuda ver Sobre, "
				+ "Buscar acha os Sintomas com um mesmo sintomas "
				+ " inserido e mostra na tabelha, "
				+ " caso não tenha nada escrito retorna todos os Sintomas.", AlertType.INFORMATION);
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
			controller.updateTableView(user);
		} catch (IOException e) {
			e.printStackTrace();
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
		List<Symptons> list = service.findAll();
		obsList = FXCollections.observableArrayList(list);
		tvSymptoms.setItems(obsList);
	}
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();
	}
	
	private void initializeNodes() {
		tcTranstorno.setCellValueFactory(new PropertyValueFactory<>("transtorno"));
		tcCid.setCellValueFactory(new PropertyValueFactory<>("cid"));
		tcSintomas_biologicos.setCellValueFactory(new PropertyValueFactory<>("sintomas_biologicos"));
		tcConsequencias_sociais.setCellValueFactory(new PropertyValueFactory<>("consequencias_sociais"));
		tcCaracteristicas.setCellValueFactory(new PropertyValueFactory<>("caracteristicas"));
		
		initializeComboBoxSymtons();
	}

	@Override
	public void onDataChanged() {
		updateTableView(user);
		
	}
	
	private void initializeComboBoxSymtons() {
		Callback<ListView<Symptons>, ListCell<Symptons>> factory = lv -> new ListCell<Symptons>() {
			@Override
			protected void updateItem(Symptons item, boolean empty) {
				super.updateItem(item, empty);
				setText(empty ? "" : item.getCid());
			}
		};
		comboCid.setCellFactory(factory);
		comboCid.setButtonCell(factory.call(null));
	}
}
