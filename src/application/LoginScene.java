package application;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class LoginScene {
	
	public Scene display() {
		
		ImageView logo = new ImageView(new Image("icon.png"));

		logo.setFitWidth(100);
		logo.setPreserveRatio(true);
		logo.setSmooth(true);
		
		Label password = new Label("password:");
		password.setFont(Font.font("Verdana",FontWeight.BOLD,15));
		
		PasswordField password_field = new PasswordField();
		
		HBox hbox = new HBox(15);
		hbox.setAlignment(Pos.CENTER);
		hbox.getChildren().addAll(password,password_field);
		
		Button login_button = new Button("Login");
		login_button.setOnAction(e-> {
			if(password_field.getText().equals("Thebest$"))
				AdminScene.display();
			else
				AlertBox.display("Invalid password!");
		});
		
		// fire login button when enter is pressed
		password_field.setOnKeyPressed(key -> {
			KeyCode keyCode = key.getCode();
	        if (keyCode.equals(KeyCode.ENTER)) {
	            login_button.fire();
	        }
		});
		
		VBox root = new VBox(15);
		root.getChildren().addAll(logo, hbox, login_button);
		root.setAlignment(Pos.CENTER);

		Scene scene = new Scene(root,320,220);
		
		return scene;
	}
}
