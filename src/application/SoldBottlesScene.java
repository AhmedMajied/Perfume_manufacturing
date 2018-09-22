package application;

import java.util.Vector;

import Models.ManufacturedBottle;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class SoldBottlesScene {
	
	private static Scene scene = null;
	private SoldBottlesScene() {}
	
	public static void display() {
		
		// table header
		HBox table_header = new HBox();
		table_header.setPadding(new Insets(10,0,10,0));
		table_header.setStyle("-fx-background-color: white; -fx-border-style: solid; -fx-border-radius: 5px;");
		
		table_header.getChildren().addAll(
				Utility.prepare_cell("Bottle Size"),
				Utility.prepare_cell("Liquid Name"),
				Utility.prepare_cell("Used Grams"),
				Utility.prepare_cell("Cost"),
				Utility.prepare_cell("Selling Price"),
				Utility.prepare_cell("Date")
		);
		
		// table data
		Vector<ManufacturedBottle> bottles = DBConnection.retrieve_manufactured_bottles();
		
		VBox table_data = new VBox(5);
		table_data.setPrefWidth(800);
		for(ManufacturedBottle bottle: bottles) {
			HBox row = new HBox();
			row.getChildren().addAll(
					Utility.prepare_cell(bottle.name),
					Utility.prepare_cell(bottle.liquid_name),
					Utility.prepare_cell(bottle.used_grams+""),
					Utility.prepare_cell(bottle.cost+""),
					Utility.prepare_cell(bottle.selling_price+""),
					Utility.prepare_cell(bottle.date)
			);
			
			// row style
			row.setStyle("-fx-background-color: white;");
			row.setPadding(new Insets(5,0,5,0));
			
			table_data.getChildren().add(row);
		}
		
		// scroll pane
		ScrollPane scrollPane = new ScrollPane();
		scrollPane.setHbarPolicy(ScrollBarPolicy.NEVER);
		scrollPane.setMaxHeight(200);
		scrollPane.setStyle("-fx-background-color: transparent;");
		scrollPane.setContent(table_data);
		
		// control buttons 
 		HBox buttons_box = new HBox(10);
 		buttons_box.setAlignment(Pos.CENTER);
 		
 		// generate report button
        Button generate_report = new Button("Generate Report");
        generate_report.setOnAction(e->{ Utility.generate_excel_file();});
 		
 		// back button
 		Button back_button = new Button("Back");
 		back_button.setOnAction(e-> { AdminScene.display(); });
 		
 		buttons_box.getChildren().addAll(back_button,generate_report);
		 		
		VBox root = new VBox(10);
		root.getChildren().addAll(table_header,scrollPane,buttons_box);
		
		scene = new Scene(root,800,300);
		Main.stage.setScene(scene);
		Main.stage.centerOnScreen();
	}
}
