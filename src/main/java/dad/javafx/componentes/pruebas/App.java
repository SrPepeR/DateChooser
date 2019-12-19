package dad.javafx.componentes.pruebas;

import dad.javafx.componentes.DateChooser;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		Scene scene = new Scene(new DateChooser());
		primaryStage.setScene(scene);
		primaryStage.show();
		
	}

	public static void main(String[] args) {
		launch(args);
	}

}
