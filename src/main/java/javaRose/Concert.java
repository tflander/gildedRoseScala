package javaRose;

public class Concert extends Item {
	public Concert(String name, int sellIn, int quality) {
		super(name, sellIn, quality);
	}


	@Override protected int calcQuality() {
		int quality = getQuality();
		if(sellIn == 0){
			return 0;
		}
		quality = quality + 1;
		if(sellIn < 10){
			quality++;
		}
		
		if(sellIn < 5){
			quality = quality + 1;
		}
		
		return quality;
	}
	
	
}
