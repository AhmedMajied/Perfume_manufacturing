package application;

import Models.*;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

public class DBConnection {
	
	private static Connection conn = null;
	private static PreparedStatement prepared_stmt = null;
	private static CallableStatement stmt = null;
	private static ResultSet result = null;
	
	private DBConnection() {}
	
	public static void initialize_connection(){
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
			conn = DriverManager.getConnection("jdbc:mysql://sql3.freemysqlhosting.net:3306/sql3251109?"
									+ "SslMode=Preferred&user=sql3251109&password=V6wHvf5xtd");
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
	}
	
	public static Vector<Liquid> retrieve_all_liquids(){
		Vector<Liquid> liquids = new Vector<>();
		
		try {
			prepared_stmt = conn.prepareStatement("select * from Liquid");
        	ResultSet result = prepared_stmt.executeQuery();
        	
            while(result.next()){
            	Liquid liquid = new Liquid();
                liquid.ID = result.getInt("ID");
                liquid.name = result.getString("Name");
                liquid.type = result.getString("Type");
                liquid.category = result.getString("Category");
                liquid.quality = result.getInt("Quality");
                liquid.quantity1 = result.getDouble("Quantity1");
                liquid.quantity2 = result.getDouble("Quantity2");
                liquid.unit_costs = result.getString("Unit_cost");
                liquid.reoreder_quantity = result.getDouble("Reorder_quantity");
                liquids.add(liquid);
            }  
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return liquids;
	}
	
	public static void save_new_liquid(Liquid new_liquid) {
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyy");
		
		try {
			if(new_liquid.unit_costs.equals("0")) {
				prepared_stmt = conn.prepareStatement("insert into Liquid (Name,Type,Category,Quality,"
												+ "Reorder_Quantity) values(?,?,?,?,?);");
				
				prepared_stmt.setString(1, new_liquid.name);
				prepared_stmt.setString(2, new_liquid.type);
				prepared_stmt.setString(3, new_liquid.category);
				prepared_stmt.setInt(4, new_liquid.quality);
				prepared_stmt.setDouble(5, new_liquid.reoreder_quantity);
				prepared_stmt.executeUpdate();
			}
			else {
				stmt = conn.prepareCall("{call insert_new_liquid(?,?,?,?,?,?,?,?)}");
				stmt.setString(1, new_liquid.name);
				stmt.setString(2, new_liquid.type);
				stmt.setString(3, new_liquid.category);
				stmt.setInt(4, new_liquid.quality);
				stmt.setDouble(5, new_liquid.quantity1);
				stmt.setString(6, new_liquid.unit_costs);
				stmt.setDouble(7, new_liquid.reoreder_quantity);
				stmt.setString(8, format.format(new Date()));
				stmt.execute();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static Liquid retrieve_liquid(String liquid_name) {
		Liquid liquid = null;
		
        try {
        	prepared_stmt = conn.prepareStatement("select ID,Quantity1,Quantity2,Unit_cost from Liquid where Name = ?");
        	prepared_stmt.setString(1, liquid_name);
        	ResultSet result = prepared_stmt.executeQuery();
        	
            if(result.next()){
            	liquid = new Liquid();
                liquid.ID = result.getInt(1);
                liquid.quantity1 = result.getDouble(2);
                liquid.quantity2 = result.getDouble(3);
                liquid.unit_costs = result.getString(4);
            }
		} catch (SQLException e) {
			e.printStackTrace();
		}
        
        return liquid;
	}
	
	public static void update_liquids(Vector<Liquid> new_liquids) {
		try {
			prepared_stmt = conn.prepareStatement("update Liquid set Name=?, Type=?,Category=?,"
							+ "Quality=?, Reorder_quantity=? where "
							+ "ID=?"); 
			
			for(Liquid liquid: new_liquids) {
				if(liquid.is_updated) {
					prepared_stmt.setString(1, liquid.name);
					prepared_stmt.setString(2, liquid.type);
					prepared_stmt.setString(3, liquid.category);
					prepared_stmt.setInt(4, liquid.quality);
					prepared_stmt.setDouble(5, liquid.reoreder_quantity);
					prepared_stmt.setInt(6, liquid.ID);
					
					prepared_stmt.executeUpdate();
				}
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static Vector<Bottle> retrieve_all_bottles(){
		Vector<Bottle> bottles = new Vector<>();
		
        try {
        	prepared_stmt = conn.prepareStatement("select * from Bottle");
        	ResultSet result = prepared_stmt.executeQuery();
        	
            while(result.next()){
            	Bottle bottle = new Bottle();
            	bottle.ID = result.getInt("ID");
            	bottle.name = result.getString("Name");
            	bottle.type = result.getString("Type");
                bottle.quantity1 = result.getDouble("Quantity1");
                bottle.quantity2 = result.getDouble("Quantity2");
                bottle.unit_costs = result.getString("Unit_cost");
            	bottle.reoreder_quantity = result.getDouble("Reorder_quantity");
            	bottles.add(bottle);
            }
		} catch (SQLException e) {
			e.printStackTrace();
		}
        
        return bottles;
	}
	
	public static Bottle retrieve_bottle(String bottle_size) {
		Bottle bottle = null;
		
        try {
        	prepared_stmt = conn.prepareStatement("select ID,Quantity1,Quantity2,Unit_cost,Liquid_used_grams from Bottle where Name = ?");
        	prepared_stmt.setString(1, bottle_size);
        	ResultSet result = prepared_stmt.executeQuery();
        	
            if(result.next()){
            	bottle = new Bottle();
            	bottle.ID = result.getInt(1);
                bottle.quantity1 = result.getDouble(2);
                bottle.quantity2 = result.getDouble(3);
                bottle.unit_costs = result.getString(4);
            	bottle.liquid_used_grams = result.getDouble(5);
            }
		} catch (SQLException e) {
			e.printStackTrace();
		}
        
        return bottle;
	}
	
	public static void update_bottles(Vector<Bottle> new_bottles) {
		try {
			prepared_stmt = conn.prepareStatement("update Bottle set Name=?, Type=?, "
									+ "Reorder_quantity=? where ID=?"); 
			
			for(Bottle bottle: new_bottles) {
				if(bottle.is_updated) {
					prepared_stmt.setString(1, bottle.name);
					prepared_stmt.setString(2, bottle.type);
					prepared_stmt.setDouble(3, bottle.reoreder_quantity);
					prepared_stmt.setInt(4, bottle.ID);
					
					prepared_stmt.executeUpdate();
				}
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static Vector<Flavor> retrieve_all_flavors(){
		Vector<Flavor> flavors = new Vector<>();
		
        try {
        	prepared_stmt = conn.prepareStatement("select * from Flavor");
        	ResultSet result = prepared_stmt.executeQuery();
        	
            while(result.next()){
            	Flavor flavor = new Flavor();
            	flavor.ID = result.getInt("ID");
            	flavor.name = result.getString("Name");
                flavor.quantity1 = result.getDouble("Quantity1");
                flavor.quantity2 = result.getDouble("Quantity2");
                flavor.unit_costs = result.getString("Unit_cost");
            	flavor.reoreder_quantity = result.getDouble("Reorder_quantity");
            	flavors.add(flavor);
            }
		} catch (SQLException e) {
			e.printStackTrace();
		}
        
        return flavors;
	}
	
	public static Vector<Flavor> retrieve_bottle_flavors(int bottleID){
		Vector<Flavor> flavors = new Vector<>();
		
        try {
        	prepared_stmt = conn.prepareStatement("select * from Bottles_Flavors, Flavor where BottleID = ? and FlavorID = ID");
        	prepared_stmt.setInt(1, bottleID);
        	ResultSet result = prepared_stmt.executeQuery();
        	
            while(result.next()){
            	Flavor flavor = new Flavor();
            	flavor.ID = result.getInt("ID");
            	flavor.name = result.getString("Name");
                flavor.quantity1 = result.getDouble("Quantity1");
                flavor.quantity2 = result.getDouble("Quantity2");
                flavor.unit_costs = result.getString("Unit_cost");
            	flavor.used_grams = result.getDouble("Used_grams");
            	flavors.add(flavor);
            }
		} catch (SQLException e) {
			e.printStackTrace();
		}
        
        return flavors;
	}
	
	public static void update_flavors(Vector<Flavor> new_flavors) {
		try {
			prepared_stmt = conn.prepareStatement("update Flavor set Name=?, "
										+ "Reorder_quantity=? where ID=?"); 
			
			for(Flavor flavor: new_flavors) {
				if(flavor.is_updated) {
					prepared_stmt.setString(1, flavor.name);
					prepared_stmt.setDouble(2, flavor.reoreder_quantity);
					prepared_stmt.setInt(3, flavor.ID);
					
					prepared_stmt.executeUpdate();
				}
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static boolean save_sold_bottle(double cost, double selling_price, Liquid liquid,Bottle bottle,
											double used_grams, Vector<Flavor> flavors) {
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyy");
		
		try {
			stmt = conn.prepareCall("{call manufacture_new_bottle(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
		    
			// data for Sold_bottle table
			stmt.setDouble(1, cost);
		    stmt.setDouble(2, selling_price);
		    stmt.setInt(3, liquid.ID);
		    stmt.setInt(4, bottle.ID);
		    stmt.setDouble(5, used_grams);
		    stmt.setString(6, format.format(new Date()));
		    
		    // data for new quantities after consuming
		    stmt.setDouble(7, liquid.quantity1);
		    stmt.setDouble(8, liquid.quantity2);
		    stmt.setString(9, liquid.unit_costs);
		    
		    stmt.setDouble(10, bottle.quantity1);
		    stmt.setDouble(11, bottle.quantity2);
		    stmt.setString(12, bottle.unit_costs);
		    
		    stmt.setDouble(13, flavors.get(0).quantity1);
		    stmt.setDouble(14, flavors.get(0).quantity2);
		    stmt.setString(15, flavors.get(0).unit_costs);
		    
		    stmt.setDouble(16, flavors.get(1).quantity1);
		    stmt.setDouble(17, flavors.get(1).quantity2);
		    stmt.setString(18, flavors.get(1).unit_costs);
		    
		    stmt.setDouble(19, flavors.get(2).quantity1);
		    stmt.setDouble(20, flavors.get(2).quantity2);
		    stmt.setString(21, flavors.get(2).unit_costs);
		    
		    boolean succeed = stmt.execute(); 
		    return succeed;
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	// get materials (liquid,flavors,bottles) that quantities less than reorder point
	public static Vector<Material> retrieve_reorder_materials(String material_name){
		Vector<Material> materials = new Vector<>();
		
		try {
        	prepared_stmt = conn.prepareStatement("select Name,Quantity1,Quantity2 "
        			+ "from "+ material_name +" where Quantity1 + Quantity2 <= Reorder_quantity");
        	ResultSet result = prepared_stmt.executeQuery();
        	
            while(result.next()){
            	Material material = new Material();
            	material.name = result.getString(1);
                material.quantity1 = result.getDouble(2);
                material.quantity2 = result.getDouble(3);
            	materials.add(material);
            }
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		return materials;
	}
	
	public static double get_packing_cost(){
		double packing_cost = 0;
		
        try {
        	prepared_stmt = conn.prepareStatement("select Cost, Other_costs from Packing_objects");
        	ResultSet result = prepared_stmt.executeQuery();
        	
            if(result.next()){
            	packing_cost += result.getDouble(1);
            	packing_cost += result.getDouble(2);
            }
		} catch (SQLException e) {
			e.printStackTrace();
		}
        
        return packing_cost;
	}
	
	public static int get_sold_bottles_count() {
		try {
        	prepared_stmt = conn.prepareStatement("select Count(*) from Manufactured_bottle");
        	ResultSet result = prepared_stmt.executeQuery();
        	
            if(result.next()) {
            	return result.getInt(1);
            }
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return 0;
	}
	
	public static double get_total_cost(){
		try {
			prepared_stmt = conn.prepareStatement("select Sum(Cost) from Purchase_transaction");
        	ResultSet result = prepared_stmt.executeQuery();
        	
            if(result.next()) {
            	return result.getDouble(1);
            }
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return 0;
	}
	
	public static double get_total_revenue(){
		try {
			prepared_stmt = conn.prepareStatement("select Sum(Selling_price) from Manufactured_bottle");
        	ResultSet result = prepared_stmt.executeQuery();
        	
            if(result.next()) {
            	return result.getDouble(1);
            }
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return 0;
	}
	
	public static Vector<ManufacturedBottle> retrieve_manufactured_bottles(){
		Vector<ManufacturedBottle> bottles = new Vector<>();
		
		try {
			prepared_stmt = conn.prepareStatement("select Bottle.Name,Liquid.Name,Used_grams,MB.Cost,"
					+ "Selling_price,Selling_date from Bottle,Liquid,Manufactured_bottle as MB where "
					+ "LiquidID = Liquid.ID and BottleID = Bottle.ID ");
        	ResultSet result = prepared_stmt.executeQuery();
        	
            while(result.next()) {
            	ManufacturedBottle bottle = new ManufacturedBottle();
            	bottle.name = result.getString(1);
            	bottle.liquid_name = result.getString(2);
            	bottle.used_grams = result.getDouble(3);
            	bottle.cost = result.getDouble(4);
            	bottle.selling_price = result.getDouble(5);
            	bottle.date = result.getString(6);
            	
            	bottles.add(bottle);
            }
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return bottles;
	}
	
	public static Vector<PurchasedItem> retrieve_all_purchased_items(){
		Vector<PurchasedItem> purchased_items = new Vector<>();
		
		try {
			prepared_stmt = conn.prepareStatement("select * from Purchase_transaction");
        	ResultSet result = prepared_stmt.executeQuery();
        	
            while(result.next()) {
            	PurchasedItem purchased_item = new PurchasedItem();
            	purchased_item.ID = result.getInt(1);
            	purchased_item.name = result.getString(2);
            	purchased_item.description = result.getString(3);
            	purchased_item.quantity = result.getDouble(4);
            	purchased_item.cost = result.getDouble(5);
            	purchased_item.date = result.getString(6);
            	
            	purchased_items.add(purchased_item);
            }
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return purchased_items;
	}
	
	public static void save_purchase_transaction(Material material, String materialName, PurchasedItem item) {
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyy");
		
		try {
			stmt = conn.prepareCall("{call add_new_quantity(?,?,?,?,?,?,?,?,?,?)}");
			
			stmt.setString(1, item.name);
			stmt.setString(2, item.description);
			stmt.setDouble(3, item.quantity);
			stmt.setDouble(4, item.cost);
			stmt.setString(5, format.format( new Date()));
			stmt.setString(6, materialName);
			stmt.setInt(7, material.ID);
			stmt.setDouble(8, material.quantity1);
			stmt.setDouble(9, material.quantity2);
			stmt.setString(10, material.unit_costs);
			
			stmt.execute();
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void save_purchase_transaction(PurchasedItem item) {
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyy");
		
		try {
			prepared_stmt = conn.prepareStatement("insert into Purchase_transaction (Item,Description,Quantity,Cost,Date) values(?,?,?,?,?)");
			prepared_stmt.setString(1, item.name);
			prepared_stmt.setString(2, item.description);
			prepared_stmt.setDouble(3, item.quantity);
			prepared_stmt.setDouble(4, item.cost);
			prepared_stmt.setString(5, format.format( new Date()));
			prepared_stmt.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void terminateConnection(){
		try {
			if(prepared_stmt != null)
				prepared_stmt.close();
			if(stmt != null)
				stmt.close();
			if(result != null)
				result.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}