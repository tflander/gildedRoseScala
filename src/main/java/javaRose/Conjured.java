package javaRose;

public class Conjured extends Item {
	public Conjured(String name, int sellIn, int quality) {
		super(name, sellIn, quality);
	}
	
	@Override protected int calcQuality() {
		int quality = getQuality()-2;
		if(quality < 0){
			quality = 0;
		}
		return quality;
	}
}
