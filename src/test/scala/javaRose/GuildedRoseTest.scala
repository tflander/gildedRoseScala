package javaRose

import java.util.ArrayList

import scala.collection.JavaConversions.asScalaBuffer

import org.scalatest.BeforeAndAfter
import org.scalatest.FunSpec
import org.scalatest.matchers.ShouldMatchers

class GuildedRoseTest extends FunSpec with ShouldMatchers with BeforeAndAfter {

  var guildedRose: GildedRose = _
  var items: ArrayList[Item] = _
  val _vest = 0
  val _brie = 1
  val _elixir = 2
  val _sulfuras = 3
  val _concert = 4
  val _cake = 5

  before {
    items = new ArrayList[Item]();
    items.add(new Item("+5 Dexterity Vest", 10, 20));
    items.add(new Brie("Aged Brie", 2, 0));
    items.add(new Item("Elixir of the Mongoose", 5, 7));
    items.add(new Sulfuras("Sulfuras, Hand of Ragnaros", 0, 80));
    items.add(new Concert("Backstage passes to a TAFKAL80ETC concert", 15, 20));
    items.add(new Conjured("Conjured Mana Cake", 3, 6));

    guildedRose = new GildedRose()
  }

	def passDays(days: Int) = {
		for (day <- 1 to days) {
			guildedRose.updateQuality(items)
		}
	}

  describe("Given normal item") {
    it("never has quality below zero after sellIn") { // Broken code, Must to fix
      val vest = items.get(_vest)
      passDays(999)
      vest.getSellIn() should be(0)
      vest.getQuality() should be(0)
    }
    
    it("displays updates for next day") {
      guildedRose.updateQuality(items)

      for (item <- items) {
        println("-----")
        println(item.getName())
        println(item.getSellIn())
        println(item.getQuality())
      }
    }

    ignore("displays changes") {
      val vest = items.get(_vest)
      for (i <- 0 to 30) {
        println("-----  + i")
        println(vest.getName())
        println(vest.getSellIn())
        println(vest.getQuality())
        guildedRose.updateQuality(items)
      }
    }

    it("degrades vest quality by one for the first day") {
      passDays(1)
      val vest = items.get(_vest)
      vest.getName() should be("+5 Dexterity Vest")
      vest.getSellIn() should be(9)
      vest.getQuality() should be(19)
    }
    
    it("degrades vest quality by two once sell-by date is expired") {
      val vest = items.get(_vest)
      passDays(vest.getSellIn())
      vest.getSellIn() should be(0)
      val qualityOnDayZero = vest.getQuality()
      passDays(1)
      vest.getQuality() should be(qualityOnDayZero - 2)
    }
  }
  
  describe("Given aged Brie item") {
    it("quality of item increases by day") {
        val brie = items.get(_brie)
      passDays(1)
      brie.getSellIn() should be(1)
      brie.getQuality() should be(1)
    }
    it("quality of item never increases beyond 50") {
    	val brie = items.get(_brie)
    	passDays(200)
    	brie.getQuality() should be(50)
    }
  }
  
  describe("Given Concert Tickets") {
    it("quality of item increases by day") {
      val concert = items.get(_concert)
      passDays(1)
      concert.getSellIn() should be(14)
      concert.getQuality() should be(21)
    }
    
    it("quality of item increases double between 5 and 10 days") {
      val concert = items.get(_concert)
      passDays(5)
      concert.getSellIn() should be(10)
      concert.getQuality() should be(25)
      passDays(1)
      concert.getSellIn() should be(9)
      concert.getQuality() should be(27)    
    }    
    
    it("quality of item increases tripples when less than 5 days") {
      val concert = items.get(_concert)
      passDays(10)
      concert.getSellIn() should be(5)
      concert.getQuality() should be(35)
      passDays(1)
      concert.getSellIn() should be(4)
      concert.getQuality() should be(38)    
    }
    
    it("quality of item drops to 0 when no days left"){  // Broken Code, Needs Fix
      val concert = items.get(_concert)
      passDays(concert.getSellIn()+1)
      concert.getSellIn() should be(0)
      concert.getQuality() should be(0)
    }
  }
  
  describe("given sulferous") {
    it("never decreases in quality") {
    val sulfuras = items.get(_sulfuras)
    passDays(999)
    sulfuras.getQuality() should be (80)
    }
  }
  
  describe("given Cobnjured") {
    it("degrades in quality twice as fast as normal items") {
    	val conjured = items.get(_cake);
    	passDays(1);
    	conjured.getSellIn() should be (2);
    	conjured.getQuality() should be (4)
    }
  }
}