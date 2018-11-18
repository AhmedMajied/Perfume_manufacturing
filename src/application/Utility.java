package application;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;

public class Utility {
	
	public static Pane prepare_cell(String text,boolean extendable){
		
		Pane cell = new Pane();
		
		Label label = new Label(text);
		label.setAlignment(Pos.CENTER);
		label.prefWidthProperty().bind(cell.widthProperty());
		
		cell.getChildren().add(label);
		
		if(extendable)
			HBox.setHgrow(cell, Priority.ALWAYS);
		else
			cell.setPrefWidth(120);
		
		//cell.setStyle("-fx-border-style: solid inside;"+"-fx-border-color: red;");
		//label.setStyle("-fx-border-style: solid inside;"+"-fx-border-color: blue;");
		
		return cell;
	}
	
	public static TextField prepare_editable_cell(String text,boolean editable,boolean extendable) {
		
		TextField field = new TextField(text);
		field.setAlignment(Pos.CENTER);
		
		if(!editable)
			field.setDisable(true);
		
		if(extendable)
			HBox.setHgrow(field, Priority.ALWAYS);
		else
			field.setPrefWidth(120);
		
		return field;
	}
	
}
