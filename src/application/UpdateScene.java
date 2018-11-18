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
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class UpdateScene {
	private static Scene scene = null;
	private static String current_material = "liquid";
	public static Vector<Liquid> liquids = DBConnection.retrieve_all_liquids();
	public static Vector<Flavor> flavors = DBConnection.retrieve_all_flavors();
	public static Vector<Bottle> bottles = DBConnection.retrieve_all_bottles();
	
	private UpdateScene() {}
	
	public static void display() {
		int window_width = 900;
		
		// material items choice box
		ChoiceBox<String> material_items_field = new ChoiceBox<>();
		
		// description field for new Quantity
		TextField description_field = new TextField(current_material);
		
		// table header
		HBox table_header = new HBox(1);
		table_header.setAlignment(Pos.CENTER);
		table_header.setPadding(new Insets(5,0,5,0));
		table_header.setStyle("-fx-background-color: white; -fx-border-style: solid; -fx-border-radius: 5px;");
		
		// table content
		VBox table_data = new VBox(5);
		table_data.setPrefWidth(window_width);
		
		// default is liquid
		switch_material(current_material, table_header, table_data,material_items_field,description_field);
		
		// scroll pane
		ScrollPane scrollPane = new ScrollPane();
		scrollPane.setMaxHeight(200);
		scrollPane.setHbarPolicy(ScrollBarPolicy.NEVER);
		scrollPane.setStyle("-fx-color: white; -fx-background-color: transparent;");
		scrollPane.setContent(table_data);
		
		// switch material buttons
		HBox switch_boxs = new HBox();
		switch_boxs.setAlignment(Pos.CENTER);
		
		Button switch_liquid = new Button("Liquid");
		switch_liquid.setOnAction(e-> switch_material("liquid", table_header, table_data,material_items_field,description_field));
		
		Button switch_flavor = new Button("Flavor");
		switch_flavor.setOnAction(e-> switch_material("flavor", table_header, table_data,material_items_field,description_field));
		
		Button switch_bottle = new Button("Bottle");
		switch_bottle.setOnAction(e-> switch_material("bottle", table_header, table_data,material_items_field,description_field));
		
		switch_boxs.getChildren().addAll(switch_liquid,switch_flavor,switch_bottle);
		
		// control buttons
		HBox buttons_box = new HBox(10);
		buttons_box.setAlignment(Pos.CENTER);
		
		// save button
		Button update_button = new Button("Update");
		update_button.setOnAction(e-> update(table_data));
		
		// insert new item button
		Button insert_button = new Button("Insert New Item");
		insert_button.setOnAction(e-> { InsertionScene.display(current_material); });
		
		// back button
		Button back_button = new Button("Back");
		back_button.setOnAction(e-> { AdminScene.display(); });
		
		buttons_box.getChildren().addAll(back_button,update_button,insert_button);
		
		// material table
		VBox root = new VBox(10);
		root.setAlignment(Pos.BASELINE_CENTER);
		root.setPadding(new Insets(10,0,10,0));
		
		HBox new_units_section = prepare_new_units_section(material_items_field,description_field);
		root.getChildren().addAll(switch_boxs,table_header,scrollPane,buttons_box,
								new_units_section);
		VBox.setMargin(new_units_section, new Insets(8,13,0,13));
		
		scene = new Scene(root,window_width,430);
		Main.stage.setScene(scene);
		Main.stage.centerOnScreen();
	}
	
	private static void switch_material(String material, HBox table_header, VBox table_data,
									ChoiceBox<String> material_items_field,TextField description_field) {
		
		// delete old content
		table_header.getChildren().clear();
		table_data.getChildren().clear();
		material_items_field.getItems().clear();
		material_items_field.getItems().add("Other");
		
		current_material = material;
		description_field.setText(current_material);
		
		if(material.equals("liquid")) {
			table_header.getChildren().addAll(
					Utility.prepare_cell("Name",true),
					Utility.prepare_cell("Type",false),
					Utility.prepare_cell("Category",false),
					Utility.prepare_cell("Quality",false),
					Utility.prepare_cell("Quantity",false),
					Utility.prepare_cell("Gram Cost",false),
					Utility.prepare_cell("Reorder Quantity",false)
			);
			
			for(Liquid liquid: liquids) {
				HBox row = new HBox(1);
				row.getChildren().addAll(
						Utility.prepare_editable_cell(liquid.name,true,true),
						Utility.prepare_editable_cell(liquid.type,true,false),
						Utility.prepare_editable_cell(liquid.category,true,false),
						Utility.prepare_editable_cell(liquid.quality+"",true,false),
						Utility.prepare_editable_cell(liquid.get_diff_quantities(),false,false),
						Utility.prepare_editable_cell(liquid.unit_costs,false,false),
						Utility.prepare_editable_cell(liquid.reoreder_quantity+"",true,false)
				);
				
				table_data.getChildren().add(row);
			}
			
			// fill choice box items
			for(Liquid liquid:liquids) {
				material_items_field.getItems().add(liquid.name);
			}
		}
		else if(material.equals("flavor")) {
			table_header.getChildren().addAll(
					Utility.prepare_cell("Name",true),
					Utility.prepare_cell("Quantity",true),
					Utility.prepare_cell("Gram Cost",true),
					Utility.prepare_cell("Reorder Quantity",true)
			);
			
			for(Flavor flavor: flavors) {
				HBox row = new HBox(1);
				row.getChildren().addAll(
						Utility.prepare_editable_cell(flavor.name,true,true),
						Utility.prepare_editable_cell(flavor.get_diff_quantities(),false,true),
						Utility.prepare_editable_cell(flavor.unit_costs,false,true),
						Utility.prepare_editable_cell(flavor.reoreder_quantity+"",true,true)
				);
				
				table_data.getChildren().add(row);
			}
			
			// fill choice box items
			for(Flavor flavor:flavors) {
				material_items_field.getItems().add(flavor.name);
			}
		}
		else{ // material is bottle
			table_header.getChildren().addAll(
					Utility.prepare_cell("Name",true),
					Utility.prepare_cell("Quantity",true),
					Utility.prepare_cell("Cost",true),
					Utility.prepare_cell("Reorder Quantity",true)
			);
			
			for(Bottle bottle: bottles) {
				HBox row = new HBox(1);
				row.getChildren().addAll(
						Utility.prepare_editable_cell(bottle.name,true,true),
						Utility.prepare_editable_cell(bottle.get_diff_quantities(),false,true),
						Utility.prepare_editable_cell(bottle.unit_costs,false,true),
						Utility.prepare_editable_cell(bottle.reoreder_quantity+"",true,true)
				);
				
				table_data.getChildren().add(row);
			}
			
			// fill choice box items
			for(Bottle bottle:bottles) {
				material_items_field.getItems().add(bottle.name);
			}
		}
	}
	
	private static void update(VBox table_data){
		int number_of_rows = table_data.getChildren().size();
		
		try { // handle not numeric inputs
			if(current_material.equals("liquid")) {
				
				// get updated data from text fields
				for(int i=0;i<number_of_rows;i++) {
					HBox row = (HBox) table_data.getChildren().get(i);
					
					liquids.get(i).setName(((TextField)row.getChildren().get(0)).getText());
					liquids.get(i).setType(((TextField)row.getChildren().get(1)).getText());
					liquids.get(i).setCategory(((TextField)row.getChildren().get(2)).getText());
					liquids.get(i).setQuality(((TextField)row.getChildren().get(3)).getText());
					liquids.get(i).setReoreder_quantity(((TextField)row.getChildren().get(6)).getText());
				}
				
				DBConnection.update_liquids(liquids);
			}
			else if(current_material.equals("flavor")) {
				
				// get updated data from text fields
				for(int i=0;i<number_of_rows;i++) {
					HBox row = (HBox) table_data.getChildren().get(i);
					
					flavors.get(i).setName(((TextField)row.getChildren().get(0)).getText());
					flavors.get(i).setReoreder_quantity(((TextField)row.getChildren().get(3)).getText());
				}
				
				DBConnection.update_flavors(flavors);
			}
			else { // current material is bottle
				
				// get updated data from text fields
				for(int i=0;i<number_of_rows;i++) {
					HBox row = (HBox) table_data.getChildren().get(i);
					
					bottles.get(i).setName(((TextField)row.getChildren().get(0)).getText());
					bottles.get(i).setReoreder_quantity(((TextField)row.getChildren().get(3)).getText());
				}
				
				DBConnection.update_bottles(bottles);
			}
		}catch(NumberFormatException ex) {
			AlertBox.display("Fields contain invalid inputs");
		}
		
		//TODO update success message
	}
	
	private static HBox prepare_new_units_section(ChoiceBox<String> material_items_field,TextField description_field){
		
		// items box
		Label item = new Label("Item");
		
		VBox item_box = new VBox(5);
		item_box.setAlignment(Pos.CENTER);
		item_box.getChildren().addAll(item,material_items_field);
		
		// description box
		Label description = new Label("Description");
		description_field.setAlignment(Pos.CENTER);
		description_field.setPrefWidth(20);
		description_field.setMaxWidth(450);
		
		// expand width to fit the content
		description_field.textProperty().addListener(e-> {
			description_field.setPrefWidth(description_field.getText().length()*7);
		});
		
		VBox description_box = new VBox(5);
		description_box.setAlignment(Pos.CENTER);
		description_box.getChildren().addAll(description,description_field);
		
		// units number box
		Label units_quantity = new Label("Units Quantity");
		TextField units_quantity_field = new TextField();
		units_quantity_field.setAlignment(Pos.CENTER);
		units_quantity_field.setMaxWidth(40);
		
		VBox units_number_box = new VBox(5);
		units_number_box.setAlignment(Pos.CENTER);
		units_number_box.getChildren().addAll(units_quantity,units_quantity_field);
		
		// units cost box
		Label units_Cost = new Label("Units Cost");
		TextField units_cost_field = new TextField();
		units_cost_field.setAlignment(Pos.CENTER);
		units_cost_field.setMaxWidth(40);
		
		VBox units_Cost_box = new VBox(5);
		units_Cost_box.setAlignment(Pos.CENTER);
		units_Cost_box.getChildren().addAll(units_Cost,units_cost_field);
		
		// update button
		Button update_button = new Button("Update");
		update_button.setOnAction(e-> {
			
			// get inserted data
			PurchasedItem purchased_item = new PurchasedItem() {};
			purchased_item.name = material_items_field.getValue();
			purchased_item.description = description_field.getText();
			
			if(purchased_item.name == null) {
				AlertBox.display("Item Field can not be empty");
				return;
			}
			
			try { // handle not numeric inputs
				purchased_item.quantity = Double.parseDouble(units_quantity_field.getText());
				purchased_item.cost = Double.parseDouble(units_cost_field.getText());
			}catch(NumberFormatException ex) {
				AlertBox.display("Fields contain invalid inputs");
				return;
			}
			
			// check if cost and quantity equal zero
			if(purchased_item.quantity == 0 && purchased_item.cost == 0) {
				AlertBox.display("Quantity and Cost can not be zero");
				return;
			}
			
			if(purchased_item.name.equals("Other")) {
				DBConnection.save_purchase_transaction(purchased_item);
			}
			else if(current_material.equals("liquid")) {
				for(Liquid liquid:liquids) {
					if(liquid.name.equals(material_items_field.getValue())) {
						
						String[] costs = liquid.unit_costs.split(",");
						
						// check if same gram cost was exists
						if(Double.parseDouble(costs[0]) == purchased_item.get_unit_price() 
								|| liquid.quantity1 == 0) {
							liquid.quantity1 += purchased_item.quantity;
							liquid.unit_costs = ""+(purchased_item.get_unit_price());
						}
						else if(liquid.quantity1 == 0) {
							liquid.quantity1 += purchased_item.quantity;
							liquid.unit_costs = ""+(purchased_item.get_unit_price());
						}
						else if(costs.length == 1) { // there is only one cost (new cost inserted)
							liquid.quantity2 = purchased_item.quantity;
							liquid.unit_costs = costs[0] + "," + (purchased_item.get_unit_price()); 
						}
						else { // same cost as second cost
							liquid.quantity2 += purchased_item.quantity;
						}
						
						DBConnection.save_purchase_transaction(liquid,"Liquid",purchased_item);
						break;
					}
				}
			}
			else if(current_material.equals("flavor")) {
				for(Flavor flavor:flavors) {
					if(flavor.name.equals(material_items_field.getValue())) {
						
						String[] costs = flavor.unit_costs.split(",");
						
						// check if same gram cost was exists
						if(Double.parseDouble(costs[0]) == purchased_item.get_unit_price()
								|| flavor.quantity1 == 0) {
							flavor.quantity1 += purchased_item.quantity;
							flavor.unit_costs = ""+(purchased_item.get_unit_price());
						}
						else if(flavor.quantity1 == 0) {
							flavor.quantity1 += purchased_item.quantity;
							flavor.unit_costs = ""+(purchased_item.get_unit_price());
						}
						else if(costs.length == 1) { // there is only one cost (new cost inserted)
							flavor.quantity2 = purchased_item.quantity;
							flavor.unit_costs = costs[0] + "," + (purchased_item.get_unit_price()); 
						}
						else { // same cost as second cost
							flavor.quantity2 += purchased_item.quantity;
						}
						
						DBConnection.save_purchase_transaction(flavor,"Flavor",purchased_item);
						break;
					}
				}
			}
			else{ // material is bottle
				for(Bottle bottle:bottles) {
					if(material_items_field.getValue().equals(bottle.name)) {
						
						String[] costs = bottle.unit_costs.split(",");
						
						// check if same gram cost was exists
						if(Double.parseDouble(costs[0]) == purchased_item.get_unit_price()) {
							bottle.quantity1 += purchased_item.quantity;
						}
						else if(bottle.quantity1 == 0) {
							bottle.quantity1 += purchased_item.quantity;
							bottle.unit_costs = ""+(purchased_item.get_unit_price());
						}
						else if(costs.length == 1) { // there is only one cost (new cost inserted)
							bottle.quantity2 = purchased_item.quantity;
							bottle.unit_costs = costs[0] + "," + (purchased_item.get_unit_price()); 
						}
						else { // same cost as second cost
							bottle.quantity2 += purchased_item.quantity;
						}
						
						DBConnection.save_purchase_transaction(bottle,"Bottle",purchased_item);
						break;
					}
				}
			}
			
			display();
		});
		
		// combine all controls with a title
		Label new_quantity = new Label("New Quantity:");
		new_quantity.setPadding(new Insets(0,2,5,0));
		
		HBox new_quantity_control = new HBox(7);
		new_quantity_control.setAlignment(Pos.BOTTOM_CENTER);
		new_quantity_control.setStyle("-fx-background-color: white; -fx-border-style: solid; -fx-border-radius: 5px;");
		new_quantity_control.setPadding(new Insets(8,0,8,0));
		new_quantity_control.getChildren().addAll(new_quantity,item_box,description_box,units_number_box,units_Cost_box,update_button);
		
		return new_quantity_control;
	}
	
}
