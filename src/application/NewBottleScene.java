package application;

import java.util.Vector;
import Models.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class NewBottleScene{
	
	private static Scene scene = null;
	private NewBottleScene() {}
	
	public static void display(){
		
		// get all bottles
		Vector<Bottle> bottles = DBConnection.retrieve_all_bottles();
		
		// liquid name
		VBox vbox1 = new VBox(10);
		Label liquid_name = new Label("Liquid name");
		TextField liquid_name_field = new TextField();
		vbox1.getChildren().addAll(liquid_name,liquid_name_field);
		vbox1.setAlignment(Pos.CENTER);
		
		// bottle size
		VBox vbox2 = new VBox(10);
		Label bottle_size = new Label("Bottle Size");
		ChoiceBox<String> bottle_size_field = new ChoiceBox<>();
		
		for(Bottle bottle:bottles) {
			if(bottle.quantity1 > 0)
				bottle_size_field.getItems().add(bottle.name);
		}
		
		Label ml = new Label("ml");
		
		// size in ml
		HBox size_in_ml = new HBox(5);
		size_in_ml.getChildren().addAll(bottle_size_field,ml);
		size_in_ml.setAlignment(Pos.BASELINE_CENTER);
		
		vbox2.getChildren().addAll(bottle_size,size_in_ml);
		vbox2.setAlignment(Pos.CENTER);
		
		// combine all fields
		HBox hbox = new HBox(20);
		hbox.setPadding(new Insets(0,8,0,8));
		hbox.setAlignment(Pos.CENTER);
		hbox.getChildren().addAll(vbox1,vbox2);
		
		// control buttons 
		HBox buttons_box = new HBox(10);
		buttons_box.setAlignment(Pos.CENTER);
		
		// create new bottle button
		Button create_button = new Button("Create");
		create_button.setDisable(true);
		create_button.setOnAction(e-> {
			NewBottleSecondaryScene.display(liquid_name_field.getText(),bottle_size_field.getValue());
		});
		
		// back button
		Button back_button = new Button("Back");
		back_button.setOnAction(e-> { AdminScene.display(); });
		
		buttons_box.getChildren().addAll(back_button,create_button);
		
		// enable create button when user select a bottle size 
		bottle_size_field.valueProperty().addListener(e->{ create_button.setDisable(false); });
		
		// control box
		VBox control_box = new VBox(8);
		control_box.setStyle("-fx-background-color: white; -fx-border-style: solid; -fx-border-radius: 5px;");
		control_box.setPadding(new Insets(8,0,8,0));
		control_box.setMaxWidth(300);
		control_box.getChildren().addAll(hbox,buttons_box);
		
		VBox root = new VBox(30);
		root.setAlignment(Pos.BASELINE_CENTER);
		root.setPadding(new Insets(10,0,10,0));
		root.getChildren().addAll(control_box,prepare_liquids_pane(liquid_name_field));
		
		scene = new Scene(root,700,420);		
		Main.stage.setScene(scene);
		Main.stage.centerOnScreen();
	}
	
	private static VBox prepare_liquids_pane(TextField liquid_name_field){
		
		Vector<Liquid> all_liquids = DBConnection.retrieve_all_liquids();
		
		// table header
		HBox header = new HBox(1);
		header.getChildren().addAll(
				Utility.prepare_cell("Name"),
				Utility.prepare_cell("Quality"),
				Utility.prepare_cell("Type"),
				Utility.prepare_cell("Category"),
				Utility.prepare_cell("Quantity"),
				Utility.prepare_cell("Gram Cost")
		);
		
		// header style
		header.setPadding(new Insets(10,0,10,0));
		header.setStyle("-fx-background-color: white; -fx-border-style: solid; -fx-border-radius: 5px;");
		
		// table data
		VBox table_data = new VBox(5);
		table_data.setPrefWidth(690);
		
		// liquids data 
		for(int liquid_index=0;liquid_index<all_liquids.size();liquid_index++) {
			HBox hbox = new HBox(1);
			hbox.getChildren().addAll(
					Utility.prepare_cell(all_liquids.get(liquid_index).name),
					Utility.prepare_cell(""+all_liquids.get(liquid_index).quality),
					Utility.prepare_cell(all_liquids.get(liquid_index).type),
					Utility.prepare_cell(all_liquids.get(liquid_index).category),
					Utility.prepare_cell(all_liquids.get(liquid_index).get_diff_quantities()),
					Utility.prepare_cell(all_liquids.get(liquid_index).unit_costs)
			);
			
			// data row style
			hbox.setPadding(new Insets(5,0,5,0));
			hbox.setStyle("-fx-background-color: white;");
			
			// row click action
			final String selected_liquid_name = all_liquids.get(liquid_index).name;
			hbox.setOnMouseClicked(e->{
				liquid_name_field.setText(selected_liquid_name);
			});
			
			table_data.getChildren().add(hbox);
		}
	
		// scroll pane
		ScrollPane scrollPane = new ScrollPane();
		scrollPane.setMaxHeight(200);
		scrollPane.setStyle("-fx-background-color: transparent;");
		scrollPane.setHbarPolicy(ScrollBarPolicy.NEVER);
		scrollPane.setContent(table_data);
		
		// liquids table
		VBox liquids_pane = new VBox(5);
		liquids_pane.setPadding(new Insets(0,5,0,5));
		liquids_pane.getChildren().addAll(header,scrollPane);
	
		return liquids_pane;
	}
	
}
