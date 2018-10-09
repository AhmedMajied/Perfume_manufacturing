package application;

import java.util.Vector;

import Models.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class NewBottleSecondaryScene {
	
	private static Scene scene = null;
	
	public static void display(String liquid_name,String mix_liquid_name,String bottle_size){
		Button agree_button = new Button("Agree");
		
		// retrieve liquid, Bottle and flavors
		Liquid selected_liquid = DBConnection.retrieve_liquid(liquid_name);
		Liquid selected_mix_liquid = DBConnection.retrieve_liquid(mix_liquid_name);
		Bottle selected_bottle = DBConnection.retrieve_bottle(bottle_size);
		Vector<Flavor> flavors = DBConnection.retrieve_bottle_flavors(selected_bottle.ID);
		
		// check if mix liquid name not found
		if(!mix_liquid_name.equals("notmix") && selected_mix_liquid == null) {
				AlertBox.display("Wrong Mix Liquid Name");
				return;
		}
			
		// check if liquid name not found
		if(selected_liquid == null) {
			AlertBox.display("Wrong Liquid Name");
			return;
		}
		
		// check if there enough grams of selected liquid
		if(selected_liquid.get_total_quantity() < selected_bottle.liquid_used_grams) {
			AlertBox.display("There is no enough grams of selected Liquid");
			return;
		}
		
		// check if there enough grams of selected mix liquid
		if(selected_liquid.get_total_quantity() < 3) {
			AlertBox.display("There is no enough grams of selected Mix Liquid");
			return;
		}
		
		// check if there enough grams of flavor
		for(Flavor flavor:flavors) {
			if(flavor.get_total_quantity() < flavor.used_grams) {
				AlertBox.display("There is no enough grams of "+flavor.name);
				return;
			}
		}
		
		double total_packing_cost = DBConnection.get_packing_cost();
		
		// liquid grams
		VBox liquid = new VBox(10);
		Label liquid_grams = new Label("Liquid Grams");
		
		Spinner<Double> liquid_grams_spinner = new Spinner<>();
        SpinnerValueFactory<Double> liquid_boundary = 
        			new SpinnerValueFactory.DoubleSpinnerValueFactory
        			(Math.min(1,selected_liquid.get_total_quantity()), selected_liquid.get_total_quantity(),
        					selected_bottle.liquid_used_grams,0.5); // liquid boundary
        liquid_grams_spinner.setValueFactory(liquid_boundary);
        liquid_grams_spinner.setPrefWidth(72);
        
        liquid.getChildren().addAll(liquid_grams,liquid_grams_spinner);
        liquid.setAlignment(Pos.CENTER);
        
        // mix liquid grams
 		VBox mix_liquid = new VBox(10);
 		Label mix_liquid_grams = new Label("Mix Liquid Grams");
 		Spinner<Double> mix_liquid_grams_spinner = new Spinner<>();
 		SpinnerValueFactory<Double> mix_liquid_boundary = null;
 		
		if(selected_mix_liquid !=null) {
		    mix_liquid_boundary = 
			 			new SpinnerValueFactory.DoubleSpinnerValueFactory
			 			(Math.min(1,selected_mix_liquid.get_total_quantity()), selected_mix_liquid.get_total_quantity(),
			 					3,0.5); // liquid boundary
			mix_liquid_grams_spinner.setValueFactory(mix_liquid_boundary);
			mix_liquid_grams_spinner.setPrefWidth(72);
		}
		 
		mix_liquid.getChildren().addAll(mix_liquid_grams,mix_liquid_grams_spinner);
		mix_liquid.setAlignment(Pos.CENTER);
        
        // alcohol grams
 		VBox alcohol = new VBox(10);
 		Label alcohol_grams = new Label("Alcohol Grams");
 		Label alcohol_used_grams = new Label(""+Math.min(flavors.get(0).used_grams,flavors.get(0).get_total_quantity()));
   
        alcohol.getChildren().addAll(alcohol_grams,alcohol_used_grams);
        alcohol.setAlignment(Pos.CENTER);
        
        // reinforcement grams
  		VBox reinforcement = new VBox(10);
  		Label reinforcement_grams = new Label("Reinforcement Grams");
 		Label reinforcement_used_grams = new Label(""+Math.min(flavors.get(1).used_grams,flavors.get(1).get_total_quantity()));
 		
	    reinforcement.getChildren().addAll(reinforcement_grams,reinforcement_used_grams);
	    reinforcement.setAlignment(Pos.CENTER);
	    
        // check for valid default grams
        if(flavors.get(0).get_total_quantity() < flavors.get(0).used_grams) {
        	alcohol_used_grams.setTextFill(Color.RED);
        	agree_button.setDisable(true);
        }
 			
        if(flavors.get(1).get_total_quantity() < flavors.get(1).used_grams) {
        	reinforcement_used_grams.setTextFill(Color.RED);
        	agree_button.setDisable(true);
        }
        
        // combine grams fields
        HBox gramsBox = new HBox(10);
        gramsBox.setPadding(new Insets(0,8,0,8));
        
        if(selected_mix_liquid == null)
        	gramsBox.getChildren().addAll(liquid,alcohol,reinforcement);
        else {
        	gramsBox.getChildren().addAll(liquid,mix_liquid,alcohol,reinforcement);
        	HBox.setHgrow(mix_liquid, Priority.ALWAYS);
        }
        	
        HBox.setHgrow(liquid, Priority.ALWAYS);
        HBox.setHgrow(alcohol, Priority.ALWAYS);
        HBox.setHgrow(reinforcement, Priority.ALWAYS);
        
        // reused bottle check box
      	CheckBox reused_bottle_checkbox = new CheckBox("Reused Bottle");		
      		
        // statistics part
        double bottle_total_cost;
        if(selected_mix_liquid != null) {
        	bottle_total_cost = calculate_bottle_cost(selected_bottle, reused_bottle_checkbox.isSelected()
        			,selected_liquid,liquid_grams_spinner.getValue(),selected_mix_liquid,
        			mix_liquid_grams_spinner.getValue(), 
        			total_packing_cost, flavors);
        }
        else {
        	bottle_total_cost = calculate_bottle_cost(selected_bottle, reused_bottle_checkbox.isSelected()
        			,selected_liquid,liquid_grams_spinner.getValue(),null,0, 
        			total_packing_cost, flavors);
        }
        		
        
        Label packing_cost = new Label("Packing Cost: "+total_packing_cost+" EGP");
        
        // bottle cost
        Label bottle_cost = new Label("Total Bottle Cost:");
        Spinner<Double> bottle_cost_spinner = new Spinner<>();
        SpinnerValueFactory<Double> bottle_cost_boundary = 
    			new SpinnerValueFactory.DoubleSpinnerValueFactory(0, 1000,(Math.round(bottle_total_cost * 1000.0) / 1000.0),0.10);
        bottle_cost_spinner.setValueFactory(bottle_cost_boundary);
        bottle_cost_spinner.setPrefWidth(72);
        
        // update bottle cost when reused bottle check box clicked 
        reused_bottle_checkbox.selectedProperty().addListener(e->{
  			double bottle_old_cost = bottle_cost_spinner.getValue();
 			String[] costs = selected_bottle.unit_costs.split(",");
 				
  			if(reused_bottle_checkbox.isSelected()) // don't calculate bottle material is cost
  				bottle_cost_boundary.setValue(bottle_old_cost - Double.parseDouble(costs[0]));
  			else 
  				bottle_cost_boundary.setValue(bottle_old_cost + Double.parseDouble(costs[0])); 				
  		});
        
        HBox bottle_cost_box = new HBox(3);
        bottle_cost_box.setAlignment(Pos.CENTER);
        bottle_cost_box.getChildren().addAll(bottle_cost,bottle_cost_spinner,new Label("EGP"));
           
        // bottle selling price
        Label bottle_selling_price = new Label("Bottle Selling price:");
        Spinner<Double> selling_price_spinner = new Spinner<>();
        SpinnerValueFactory<Double> selling_price_boundary = 
    			new SpinnerValueFactory.DoubleSpinnerValueFactory(0, 1000,25);
        selling_price_spinner.setValueFactory(selling_price_boundary);
        selling_price_spinner.setEditable(true);
        selling_price_spinner.setPrefWidth(72);
        
        HBox selling_price_box = new HBox(3);
        selling_price_box.setAlignment(Pos.CENTER);
        selling_price_box.getChildren().addAll(bottle_selling_price,selling_price_spinner,new Label("EGP"));
        
        // update cost according to liquid grams changing
        liquid_grams_spinner.getEditor().textProperty().addListener((obs, oldValue, newValue) -> {
            double new_bottle_total_cost = update_bottle_cost(bottle_cost_spinner.getValue(), 
            		Double.parseDouble(oldValue), Double.parseDouble(newValue),selected_liquid); 
        	bottle_cost_boundary.setValue((Math.round(new_bottle_total_cost * 1000.0) / 1000.0));
        });
        
        // update cost according to mix liquid grams changing
        mix_liquid_grams_spinner.getEditor().textProperty().addListener((obs, oldValue, newValue) -> {
            double new_bottle_total_cost = update_bottle_cost(bottle_cost_spinner.getValue(), 
            		Double.parseDouble(oldValue), Double.parseDouble(newValue),selected_mix_liquid); 
        	bottle_cost_boundary.setValue((Math.round(new_bottle_total_cost * 1000.0) / 1000.0));
        });
        
        // combine all statistics
        VBox statsBox = new VBox(8);
        statsBox.getChildren().addAll(packing_cost,bottle_cost_box,selling_price_box);
        statsBox.setAlignment(Pos.CENTER);
        
        // control buttons 
 		HBox buttons_box = new HBox(10);
 		buttons_box.setAlignment(Pos.CENTER);
 		
 		// agree button
        agree_button.setOnAction(e->{
        	double selling_price_input = Double.parseDouble(selling_price_spinner.getEditor().getText());
        	
        	if(selling_price_input < 0) {
        		AlertBox.display("Selling Price Can not be negative");
        		return;
        	}
        	
        	if(selected_mix_liquid == null) {
        		consume_main_materials(selected_bottle, reused_bottle_checkbox.isSelected(),selected_liquid,
						liquid_grams_spinner.getValue(),null,0);
        		DBConnection.save_sold_bottle(bottle_cost_spinner.getValue(),selling_price_input,selected_liquid,
						selected_bottle,liquid_grams_spinner.getValue(),flavors);
        	}
        		
        	else {
        		consume_main_materials(selected_bottle, reused_bottle_checkbox.isSelected(),selected_liquid,
						liquid_grams_spinner.getValue(),selected_mix_liquid,mix_liquid_grams_spinner.getValue());
        		DBConnection.save_sold_mix_bottle(bottle_cost_spinner.getValue(),selling_price_input,
        				selected_liquid,liquid_grams_spinner.getValue(),
        				selected_mix_liquid,mix_liquid_grams_spinner.getValue(), selected_bottle,flavors);
        	}
        	
        	NewBottleScene.display();
        });
 		
 		// back button
 		Button back_button = new Button("Back");
 		back_button.setOnAction(e-> { NewBottleScene.display(); });
 		
 		buttons_box.getChildren().addAll(back_button,agree_button);
     	
        VBox root = new VBox(25);
        root.getChildren().addAll(gramsBox,reused_bottle_checkbox,statsBox,buttons_box);
        root.setAlignment(Pos.CENTER);
        
        scene = new Scene(root,600,300);
        Main.stage.setScene(scene);
        Main.stage.centerOnScreen();
	}
	
	private static double calculate_bottle_cost(Bottle bottle,boolean reused_bottle,Liquid liquid,double liquid_grams,
													Liquid mix_liquid,double mix_liquid_grams,double packing_cost, Vector<Flavor> flavors){
		String[] costs;
		double bottle_total_cost = packing_cost;
		
		// calculate bottle cost only if it is not reused one  
		if(!reused_bottle) {
			costs = bottle.unit_costs.split(",");
			bottle_total_cost += Double.parseDouble(costs[0]);
			
			// swap if quantity1 equal zero
			if(bottle.quantity1 == 0 && bottle.quantity2 != 0) {
				bottle.quantity1 = bottle.quantity2;
				bottle.quantity2 = 0;
				bottle.unit_costs = costs[1]; 
			}
		}
		
		// calculate liquid cost
		costs = liquid.unit_costs.split(",");
		
		if(liquid.quantity1 < liquid_grams) {
			liquid_grams -= liquid.quantity1;
			bottle_total_cost += liquid.quantity1 * Double.parseDouble(costs[0]) +
									liquid_grams * Double.parseDouble(costs[1]);
		}
		else {
			bottle_total_cost += liquid_grams * Double.parseDouble(costs[0]);
		}
		
		// calculate mix liquid cost if it's mix bottle
		if(mix_liquid != null) {
			costs = mix_liquid.unit_costs.split(",");
			
			if(mix_liquid.quantity1 < mix_liquid_grams) {
				mix_liquid_grams -= mix_liquid.quantity1;
				bottle_total_cost += mix_liquid.quantity1 * Double.parseDouble(costs[0]) +
										mix_liquid_grams * Double.parseDouble(costs[1]);
			}
			else {
				bottle_total_cost += mix_liquid_grams * Double.parseDouble(costs[0]);
			}
		}
		
		// calculate flavors cost
		double used_grams;
		
		for(Flavor flavor: flavors) {
			used_grams = flavor.used_grams;
			costs = flavor.unit_costs.split(",");
			
			if(flavor.quantity1 < used_grams) {
				used_grams -= flavor.quantity1;
				bottle_total_cost += flavor.quantity1 * Double.parseDouble(costs[0]) +
										used_grams * Double.parseDouble(costs[1]);
				
				// swap
				flavor.quantity1 = flavor.quantity2 - used_grams;
				flavor.quantity2 = 0;
				flavor.unit_costs = costs[1];
			}
			else {
				bottle_total_cost += used_grams * Double.parseDouble(costs[0]);
				flavor.quantity1 -= used_grams;
			}
			
			if(flavor.quantity1 == 0 && flavor.quantity2 > 0) {
				flavor.quantity1 = flavor.quantity2;
				flavor.quantity2 = 0;
				flavor.unit_costs = costs[1];
			}
		}
		return bottle_total_cost;
	}
	
	// consume liquids grams and bottle 
	private static void consume_main_materials(Bottle bottle,boolean reused_bottle,Liquid liquid,double used_grams,Liquid mix_liquid,double mix_used_grams){
		String[] costs;
		
		// consume one bottle only if it is not reused one  
		if(!reused_bottle) {
			costs = bottle.unit_costs.split(",");
			bottle.quantity1--;
			
			// swap if quantity1 equal zero
			if(bottle.quantity1 == 0 && bottle.quantity2 != 0) {
				bottle.quantity1 = bottle.quantity2;
				bottle.quantity2 = 0;
				bottle.unit_costs = costs[1]; 
			}
		}
		
		// consume liquid grams
		costs = liquid.unit_costs.split(",");
		
		if(liquid.quantity1 < used_grams) {
			used_grams -= liquid.quantity1;
			
			// swap
			liquid.quantity1 = liquid.quantity2 - used_grams;
			liquid.quantity2 = 0;
			liquid.unit_costs = costs[1];
		}
		else {
			liquid.quantity1 -= used_grams;
		}
		
		if(liquid.quantity1 == 0 && liquid.quantity2 > 0) {
			liquid.quantity1 = liquid.quantity2;
			liquid.quantity2 = 0;
			liquid.unit_costs = costs[1];
		}
		
		// consume mix liquid grams if mix bottle
		if(mix_liquid != null) {
			
			costs = mix_liquid.unit_costs.split(",");
			
			if(mix_liquid.quantity1 < mix_used_grams) {
				mix_used_grams -= mix_liquid.quantity1;
				
				// swap
				mix_liquid.quantity1 = mix_liquid.quantity2 - mix_used_grams;
				mix_liquid.quantity2 = 0;
				mix_liquid.unit_costs = costs[1];
			}
			else {
				mix_liquid.quantity1 -= mix_used_grams;
			}
			
			if(mix_liquid.quantity1 == 0 && mix_liquid.quantity2 > 0) {
				mix_liquid.quantity1 = mix_liquid.quantity2;
				mix_liquid.quantity2 = 0;
				mix_liquid.unit_costs = costs[1];
			}
		}
		
	}
		
	private static double update_bottle_cost(double bottle_cost,double old_grams,double new_grams,Liquid liquid) {
		String[] costs = liquid.unit_costs.split(",");
		
		if(new_grams > old_grams) {// when user increases grams
			if(new_grams <= liquid.quantity1) // change is in first quantity
				bottle_cost += (new_grams - old_grams) * Double.parseDouble(costs[0]);
			else // change is in second quantity
				bottle_cost += (new_grams - old_grams) * Double.parseDouble(costs[1]);
		}
		else {// when user decreases grams
			if(old_grams <= liquid.quantity1) // change is in first quantity
				bottle_cost -= (old_grams - new_grams) * Double.parseDouble(costs[0]);
			else // change is in second quantity
				bottle_cost -= (old_grams - new_grams) * Double.parseDouble(costs[1]);
		}
		
		return bottle_cost;
	}
}
