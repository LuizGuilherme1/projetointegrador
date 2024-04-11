package application;
	
import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;


public class Main extends Application {
	private static Scene mainScene;
	@Override
	public void start(Stage primaryStage) {
		try {
			Parent loader = FXMLLoader.load(getClass().getResource("/gui/Login.fxml"));
			Scene mainScene = new Scene(loader);
			primaryStage.setScene(mainScene);
			primaryStage.setTitle("Login");
			primaryStage.show();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public static Scene getMainScene() {
		return mainScene;
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
