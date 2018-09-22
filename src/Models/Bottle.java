package Models;

public class Bottle extends Material{
	public String type;
	public double liquid_used_grams;
	
	public void setType(String type) {
		if(type != this.type) {
			this.type = type;
			is_updated = true;
		}
	}
}
