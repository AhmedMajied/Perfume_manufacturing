package Models;

public class PurchasedItem {
	public int ID;
	public String name;
	public String description;
	public double quantity;
	public double cost;
	public String date;
	
	public double get_unit_price(){
		return (Math.round(cost/quantity * 100.0)/100.0);
	}
}
