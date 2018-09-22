package application;
	
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {
	
	public static Stage stage;
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) {
		
		try {
        DBConnection.initialize_connection(); // initialize connection
		
        stage = primaryStage;
        stage.setResizable(false);
        stage.setTitle("Friends Perfume");
        stage.getIcons().add(new Image("icon.png"));
        
        stage.setScene(new LoginScene().display());
        
		stage.setOnCloseRequest(e -> DBConnection.terminateConnection());
		stage.show();	
		
		}catch(Exception e) {
			e.printStackTrace();
			AlertBox.display("Connection to databse is failed");
		}
	}
	
}
