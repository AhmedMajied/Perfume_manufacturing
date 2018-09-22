package Models;

public class Material {
	public int ID;
	public String name;
	public double quantity1;
	public double quantity2;
	public String unit_costs;
	public double reoreder_quantity;
	public boolean is_updated = false;
	
	public Material() {}

	public void setName(String name) {
		if(!name.equals(this.name)) {
			this.name = name;
			is_updated = true;
		}
	}
	
	public void setReoreder_quantity(String reoreder_quantity_text) {
		double reoreder_quantity = Double.parseDouble(reoreder_quantity_text);

		if(reoreder_quantity != this.reoreder_quantity) {
			this.reoreder_quantity = reoreder_quantity;
			is_updated = true;
		}
	}
	
	public String get_diff_quantities(){
		if(quantity2 == 0)
			return quantity1+"";
		
		return quantity1+","+quantity2;
	}
	
	public double get_total_quantity() {
		return quantity1+quantity2;
	}
}
