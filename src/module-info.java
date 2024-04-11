module ProjetoIntegradorAtual {
	
	opens application to javafx.graphics, javafx.fxml;
	requires javafx.controls;
	requires javafx.fxml;
	requires java.sql;
	exports gui;
	opens gui;
	opens model.entites to javafx.base;
	exports model.entites;
}
