package scalaRose

import org.scalatest.BeforeAndAfter
import org.scalatest.FunSpec
import org.scalatest.matchers.ShouldMatchers

class GuildedRoseTest extends FunSpec with ShouldMatchers with BeforeAndAfter {

  var guildedRose: GildedRose = _
  var items: Seq[Item] = _
  val _vest = 0
  val _brie = 1
  val _elixir = 2
  val _sulfuras = 3
  val _concert = 4
  val _cake = 5

  before {
    
    import Item._
    
    items = Seq(
      Item("+5 Dexterity Vest", 10, 20),
      AgeImprovedItem("Aged Brie", 2, 0),
      Item("Elixir of the Mongoose", 5, 7),
      LegendaryItem("Sulfuras, Hand of Ragnaros", 0, 80),
      ConcertTicketItem("Backstage passes to a TAFKAL80ETC concert", 15, 20),
      ConjuredItem("Conjured Mana Cake", 3, 12))

    guildedRose = new GildedRose()
  }

  def passDays(days: Int) = {
    for (day <- 1 to days) {
      guildedRose.updateQuality(items)
    }
  }

  describe("Given normal item") {
    it("never has negative quality after expiration") {
      val vest = items(_vest)
      passDays(999)
      vest.quality should be(0)
    }
    
    it("never has negative days left to sell after expiration") {
      val vest = items(_vest)
      passDays(999)
      vest.daysLeftToSell should be(0)
    }

    it("degrades normal item quality by one for the first day") {
      passDays(1)
      val vest = items(_vest)
      vest.name should be("+5 Dexterity Vest")
      vest.daysLeftToSell should be(9)
      vest.quality should be(19)
    }

    it("degrades normal item quality by two once sell-by date is expired") {
      val vest = items(_vest)
      passDays(vest.daysLeftToSell)
      vest.daysLeftToSell should be(0)
      val qualityOnDayZero = vest.quality
      passDays(1)
      vest.quality should be(qualityOnDayZero - 2)
    }
  }

  describe("Given aged Brie item") {
    it("increases quality of item each day by 1") {
      val brie = items(_brie)
      passDays(1)
      brie.daysLeftToSell should be(1)
      brie.quality should be(1)
    }
    it("never increases quality of item beyond 50") {
      val brie = items(_brie)
      passDays(200)
      brie.quality should be(50)
    }
  }

  describe("Given Concert Tickets") {
    it("increases quality of item by day") {
      val concert = items(_concert)
      passDays(1)
      concert.daysLeftToSell should be(14)
      concert.quality should be(21)
    }

    it("increases quality of item by twice normal rate with more than 5 and 10 or less days left") {
      val concert = items(_concert)
      passDays(5)
      concert.daysLeftToSell should be(10)
      concert.quality should be(25)
      passDays(1)
      concert.daysLeftToSell should be(9)
      concert.quality should be(27)
    }

    it("increases quality of item by triple the normal rate when 5 days or less") {
      val concert = items(_concert)
      passDays(10)
      concert.daysLeftToSell should be(5)
      concert.quality should be(35)
      passDays(1)
      concert.daysLeftToSell should be(4)
      concert.quality should be(38)
    }

    it("drops quality of item to 0 when expired") { // Broken Code, Needs Fix
      val concert = items(_concert)
      passDays(concert.daysLeftToSell + 1)
      concert.daysLeftToSell should be(0)
      concert.quality should be(0)
    }
  }

  describe("Given legendary items") {
    it("never decreases in quality") {
      val sulfuras = items(_sulfuras)
      passDays(999)
      sulfuras.quality should be(80)
    }
  }

  describe("Given conjured") {
    it("degrades in quality twice as fast as normal items") {
      val conjured = items(_cake);
      passDays(3)
      conjured.daysLeftToSell should be(0)
      conjured.quality should be(6)
      passDays(1)
      conjured.daysLeftToSell should be(0)
      conjured.quality should be(2)
    }
    
    it("never has negative quality") {
      val conjured = items(_cake);
      passDays(999)
      conjured.quality should be(0)
    }
  }
  
  describe("currying for quality") {
    it("gives me a quality directly") {
      val vest = items(_vest)
      val quality = GildedRose.decreasingValueCalculator(1)(vest)
      quality should be (19)
    }
    
    it("gives me a quality through currying") {
      val vest = items(_vest)
      val qualityFunc = GildedRose.decreasingValueCalculator(1)(_)
      qualityFunc(vest) should be (19)
    }
    
  }
}