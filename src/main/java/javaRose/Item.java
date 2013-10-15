package javaRose;

public class Item {
	public String name;
	public int sellIn;
	public int quality;

	public Item(String name, int sellIn, int quality) {
		this.setName(name);
		this.setSellIn(sellIn);
		this.setQuality(quality);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getSellIn() {
		return sellIn;
	}

	public void setSellIn(int sellIn) {
		this.sellIn = sellIn;
	}

	public int getQuality() {
		return quality;
	}

	public void setQuality(int quality) {
		this.quality = quality;
	}

	public void age() {
		adjustSellIn();
		setQuality(calcQuality());
	}

	protected void adjustSellIn() {
		if (getSellIn() > 0) { 
			setSellIn(getSellIn() - 1); 
		};
	}

	protected int calcQuality() {
		int candidateQuality = getQuality() - 1;
		if (getSellIn() <= 0) {
			candidateQuality = candidateQuality - 1;
		}
		if (candidateQuality < 0)
			candidateQuality = 0;
		return candidateQuality;
	}

}
