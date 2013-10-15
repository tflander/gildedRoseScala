package javaRose;

public class Brie extends Item {
	public Brie(String name, int sellIn, int quality) {
		super(name, sellIn, quality);
	}

	@Override protected int calcQuality() {
		int quality = getQuality() + 1;
		if(quality > 50){
			quality = 50;
		}
		return quality;
	}
}
