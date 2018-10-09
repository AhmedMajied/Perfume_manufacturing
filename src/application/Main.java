package application;
	
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.image.Image;

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
        
        /*float x = 19.5f;
        float y = 30.23f;
        System.out.println(Math.round(y/x * 100.0)/100.0);
        System.out.println(y/x);
        */
		stage.setOnCloseRequest(e -> DBConnection.terminateConnection());
		stage.show();	
		
		}catch(Exception e) {
			e.printStackTrace();
			AlertBox.display("Connection to databse is failed");
		}
	}
	
}

