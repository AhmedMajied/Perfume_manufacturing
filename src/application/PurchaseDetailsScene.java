package application;

import java.util.Vector;
import Models.PurchasedItem;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class PurchaseDetailsScene {
	
	private static Scene scene = null;
	private PurchaseDetailsScene() {}
	
	public static void display() {
		int window_width = 650;
		
		// table header
		HBox table_header = new HBox();
		table_header.setPadding(new Insets(10,0,10,0));
		table_header.setStyle("-fx-background-color: white; -fx-border-style: solid; -fx-border-radius: 5px;");
		
		table_header.getChildren().addAll(
				Utility.prepare_cell("Item",true),
				Utility.prepare_cell("Description",false),
				Utility.prepare_cell("Quantity",false),
				Utility.prepare_cell("Cost",false),
				Utility.prepare_cell("Date",false)
		);
		
		// table data
		Vector<PurchasedItem> purchased_items = DBConnection.retrieve_all_purchased_items();
		
		VBox table_data = new VBox(5);
		table_data.setPrefWidth(window_width);
		for(PurchasedItem item:purchased_items) {
			HBox row = new HBox();
			row.getChildren().addAll(
					Utility.prepare_cell(item.name,true),
					Utility.prepare_cell(item.description,false),
					Utility.prepare_cell(item.quantity+"",false),
					Utility.prepare_cell(item.cost+"",false),
					Utility.prepare_cell(item.date,false)
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
		
 		// back button
 		Button back_button = new Button("Back");
 		back_button.setOnAction(e-> { AdminScene.display(); });
 		
		VBox root = new VBox(10);
		root.setAlignment(Pos.BASELINE_CENTER);
		root.getChildren().addAll(table_header,scrollPane,back_button);
		
		scene = new Scene(root,window_width,300);
		Main.stage.setScene(scene);
		Main.stage.centerOnScreen();
	}
}
