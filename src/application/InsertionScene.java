package application;

import Models.Liquid;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class InsertionScene {
	
	public static void display(String material) {
		
		Stage window = new Stage();
		window.initModality(Modality.APPLICATION_MODAL);
		window.getIcons().add(new Image("icon.png"));
		window.setResizable(false); // make window not resizable
		window.setTitle("Insert new "+material);
		
		HBox title_row = new HBox(1);
		HBox new_item_row = new HBox(1);
		
		if(material.equals("liquid")) {
			title_row.getChildren().addAll(
					Utility.prepare_cell("Name",true),
					Utility.prepare_cell("Type",false),
					Utility.prepare_cell("Category",false),
					Utility.prepare_cell("Quality",false),
					Utility.prepare_cell("Quantity",false),
					Utility.prepare_cell("Gram Cost",false),
					Utility.prepare_cell("Reorder Quantity",false)
			);
		}
		else if (material.equals("flavor")) {
			AlertBox.display("This function is not implemented yet");
			return;
		}
		else {
			/*title_row.getChildren().addAll(
					Utility.prepare_cell("Size"),
					Utility.prepare_cell("Type"),
					Utility.prepare_cell("Cost"),
					Utility.prepare_cell("liquid_used_grams"),
					Utility.prepare_cell("Quantity"),
					Utility.prepare_cell("Reorder Quantity")
			);*/
			AlertBox.display("This function is not implemented yet");
			return;
		}
		
		new_item_row.getChildren().add(Utility.prepare_editable_cell("",true,true));
		for(int i=1;i<title_row.getChildren().size();i++) {
			new_item_row.getChildren().add(Utility.prepare_editable_cell("",true,false));
		}
		
		// save new item button
		Button save_button = new Button("Save");
		save_button.setOnAction(e-> { 
			
			try {// handle not numeric inputs
				if(material.equals("liquid")) {
					Liquid new_liquid = new Liquid();
					new_liquid.name = ((TextField)new_item_row.getChildren().get(0)).getText();
					new_liquid.type = ((TextField)new_item_row.getChildren().get(1)).getText();
					new_liquid.category = ((TextField)new_item_row.getChildren().get(2)).getText();
					new_liquid.quality = Integer.parseInt(((TextField)new_item_row.getChildren().get(3)).getText());
					new_liquid.quantity1 = Double.parseDouble(((TextField)new_item_row.getChildren().get(4)).getText());
					new_liquid.unit_costs = ((TextField)new_item_row.getChildren().get(5)).getText();
					Double.parseDouble(new_liquid.unit_costs); // check if its a number
					new_liquid.reoreder_quantity = Double.parseDouble(((TextField)new_item_row.getChildren().get(6)).getText());
					
					new_liquid.ID = DBConnection.save_new_liquid(new_liquid);
					UpdateScene.liquids.add(new_liquid);
				}
				
				// empty the fields
				for(int i=0;i<new_item_row.getChildren().size();i++) {
					((TextField)new_item_row.getChildren().get(i)).setText("");
				}
			}catch(NumberFormatException ex) {
				AlertBox.display("Fields contain invalid inputs");
			}
		});
		
		// combine all controls
		VBox root = new VBox(10);
		root.setAlignment(Pos.BASELINE_CENTER);
		root.setPadding(new Insets(10,2,10,2));
		root.setStyle("-fx-background-color: white; -fx-border-style: solid; -fx-border-radius: 5px;");
		root.getChildren().addAll(title_row,new_item_row,save_button);
		
		window.setOnCloseRequest(e->{
			UpdateScene.display();
		});
		
		window.setScene(new Scene(root,900,110));
		window.showAndWait();
	}
}
