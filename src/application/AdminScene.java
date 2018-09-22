package application;

import java.util.Vector;

import Models.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class AdminScene {
	
	private static Scene scene = null;
	private AdminScene() {}
	
	public static void display(){
	
		// control buttons
		HBox control_buttons = new HBox(20); 
		
		Button update_button = new Button("Update Materials");
		update_button.setOnAction(e-> { UpdateScene.display(); });
		
		// new bottle button
		Button new_bottle_button = new Button("New Bottle");
		new_bottle_button.setOnAction(e-> { NewBottleScene.display(); });
		
		Button sold_bottles_button = new Button("Sold Bottles");
		sold_bottles_button.setOnAction(e-> { SoldBottlesScene.display(); });
		
		Button purchase_details_button = new Button("Purchase details");
		purchase_details_button.setOnAction(e-> { PurchaseDetailsScene.display(); });
		
		control_buttons.getChildren().addAll(update_button,new_bottle_button,sold_bottles_button,purchase_details_button);
		control_buttons.setAlignment(Pos.CENTER);
		
		// needed materials section
		HBox needed_materials = new HBox(10);
		needed_materials.setAlignment(Pos.CENTER);
		
		needed_materials.getChildren().addAll(
				prepare_reorder_table(DBConnection.retrieve_reorder_materials("Liquid"),"Liquids","Name"),
				prepare_reorder_table(DBConnection.retrieve_reorder_materials("Flavor"),"Flavors","Name"),
				prepare_reorder_table(DBConnection.retrieve_reorder_materials("Bottle"),"Bottles","Size")
		);
		
		// statistics section
		VBox stats_box = new VBox(7);
		stats_box.setPadding(new Insets(5,0,5,0));
		stats_box.setMaxWidth(320);
		stats_box.setAlignment(Pos.CENTER);
		stats_box.setStyle("-fx-background-color: white; -fx-border-style: solid; -fx-border-radius: 5px;");
		
		double total_cost = DBConnection.get_total_cost();
		double total_revenue = DBConnection.get_total_revenue();
		
		Label sold_bottles_count = new Label("Sold Bottles no: "+DBConnection.get_sold_bottles_count());
		Label cost = new Label("Cost: "+(Math.round(total_cost))+" EP"); 
		Label revenue = new Label("Revenue: "+(Math.round(total_revenue)+" EP"));
		Label profit = new Label("Profit: "+(Math.round(Math.max(0,total_revenue-total_cost))+" EP"));
		
		HBox hbox = new HBox(18);
		hbox.setAlignment(Pos.CENTER);
		hbox.getChildren().addAll(cost,revenue,profit);
		stats_box.getChildren().addAll(hbox,sold_bottles_count);
		
		VBox root = new VBox(25);
		root.getChildren().addAll(control_buttons,needed_materials,stats_box);
		root.setAlignment(Pos.CENTER);
        
        scene = new Scene(root,790,350);		
		Main.stage.setScene(scene);
		Main.stage.centerOnScreen();
	}
	
	private static VBox prepare_reorder_table(Vector<Material> data,String title_text,String col_name) {
		
		Label title = new Label("Needed "+title_text+" ("+data.size()+")"); // title
		
		// table header
		HBox table_header = new HBox(1);
		Pane name_col = Utility.prepare_cell(col_name);
		Pane quantity_col = Utility.prepare_cell("Quantity");
		
		table_header.getChildren().addAll(name_col,quantity_col);
		
		// table data
		VBox table_data = new VBox(5);
		table_data.setPrefWidth(250);
		
		for(Material data_row: data) {
			HBox table_row = new HBox();
			table_row.getChildren().add(Utility.prepare_cell(data_row.name));
			
			if(!title_text.equals("Bottles"))
				table_row.getChildren().add(Utility.prepare_cell(data_row.get_total_quantity() + ""));
			else
				table_row.getChildren().add(Utility.prepare_cell((int)data_row.get_total_quantity() + ""));
			
			table_data.getChildren().add(table_row);
		}
		
		// scroll pane
		ScrollPane scrollPane = new ScrollPane();
		scrollPane.setHbarPolicy(ScrollBarPolicy.NEVER);
		scrollPane.setMaxHeight(90);
		scrollPane.setContent(table_data);
		
		// table style
		VBox reorder_box = new VBox(10);
		reorder_box.setPrefHeight(155);
		reorder_box.setStyle("-fx-background-color: white; -fx-border-style: solid; -fx-border-radius: 5px;");
		reorder_box.setAlignment(Pos.BASELINE_CENTER);
		
		reorder_box.getChildren().addAll(title,table_header,scrollPane);
		
		return reorder_box;
	}
}
