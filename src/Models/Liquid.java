package Models;

public class Liquid extends Material{
	public String type;
	public String category;
	public int quality; 
	
	public Liquid() {
		super();
	}

	public void setType(String type) {
		if(!type.equals(this.type)) {
			this.type = type;
			is_updated = true;
		}
	}
	
	public void setCategory(String category) {
		if(!category.equals(this.category)) {
			this.category = category;
			is_updated = true;
		}
	}

	public void setQuality(String quality_text) {
		int quality = Integer.parseInt(quality_text);
		
		if(quality != this.quality) {
			this.quality = quality;
			is_updated = true;
		}		
	}
}
