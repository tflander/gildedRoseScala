package javaRose;

import java.util.List;

public class GildedRose {

	public void updateQuality(List<Item> items) {
		for (Item item : items) {
			item.age();
		}
	}

}