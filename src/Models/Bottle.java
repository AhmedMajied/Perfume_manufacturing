package Models;

public class Bottle extends Material{
	public double liquid_used_grams;
	public double alcahol_used_grams;
	public double reinforcement_used_grams;
	
	public void set_liquid_used_grams(String liquid_used_grams_text) {
		double liquid_used_grams = Double.parseDouble(liquid_used_grams_text);

		if(liquid_used_grams != this.liquid_used_grams) {
			this.liquid_used_grams = liquid_used_grams;
			is_updated = true;
		}
	}
	
	public void set_alcahol_used_grams(String alcahol_used_grams_text) {
		double alcahol_used_grams = Double.parseDouble(alcahol_used_grams_text);

		if(alcahol_used_grams != this.alcahol_used_grams) {
			this.alcahol_used_grams = alcahol_used_grams;
			is_updated = true;
		}
	}
	
	public void set_reinforcement_used_grams(String reinforcement_used_grams_text) {
		double reinforcement_used_grams = Double.parseDouble(reinforcement_used_grams_text);

		if(reinforcement_used_grams != this.reinforcement_used_grams) {
			this.reinforcement_used_grams = reinforcement_used_grams;
			is_updated = true;
		}
	}
}
