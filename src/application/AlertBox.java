package application;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AlertBox {
	
	public static void display(String message) {
		
		Stage window = new Stage();
		window.initModality(Modality.APPLICATION_MODAL);
		window.getIcons().add(new Image("icon.png"));
		window.setResizable(false); // make window not resizable
		window.setTitle("Error");
		
		Label alert_message = new Label(message);
		Button close_button  = new Button("Close");
		close_button.setOnAction(e-> window.close());
		
		VBox root = new VBox(10);
		root.getChildren().addAll(alert_message, close_button);
		root.setAlignment(Pos.CENTER);
		
		window.setScene(new Scene(root,240,110));
		window.showAndWait();
	}
}
